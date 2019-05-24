
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import security.LoginService;
import domain.Article;
import domain.Comment;
import domain.Customer;
import domain.Nutritionist;

@Service
@Transactional
public class CommentService {

	// Managed repository ---------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private NutritionistService	nutritionistService;

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private Validator			validator;

	@Autowired
	private UtilityService		utilityService;


	//Constructor ----------------------------------------------------
	public CommentService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Comment create(final int articleId) {
		Article article;
		article = this.articleService.findOne(articleId);
		Assert.isTrue(this.principalCanWrite(article));
		Comment result;

		result = new Comment();
		result.setArticle(article);
		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
			result.setCustomer(this.customerService.findByPrincipal());

		return result;
	}

	public final Comment save(final Comment comment, final Article article) {
		Assert.isTrue(this.principalCanWrite(article));
		Comment result;
		Date moment;

		moment = this.utilityService.current_moment();

		comment.setPublicationMoment(moment);

		result = this.commentRepository.save(comment);

		return result;
	}

	public final Comment findOne(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);

		Assert.notNull(result);

		return result;
	}

	public final Comment findOneToDisplay(final int commentId) {
		Comment result;

		result = this.findOne(commentId);

		Assert.notNull(result);
		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]")) {
			final Customer customer;
			customer = this.customerService.findByPrincipal();
			Assert.isTrue(customer.getIsPremium());
		}

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> results;

		results = this.commentRepository.findAll();

		return results;
	}

	public void deleteCommentByCustomer(final Customer customer) {
		Collection<Comment> comments;

		comments = this.commentRepository.findCommentByCustomer(customer.getId());
		this.commentRepository.delete(comments);
	}

	// Other business methods ---------------------

	public boolean principalCanWrite(final Article article) {
		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]")) {
			Customer customer;
			customer = this.customerService.findByPrincipal();
			return (customer.getIsPremium() && article.getIsFinalMode());
		} else {
			Nutritionist nutritionist;
			nutritionist = this.nutritionistService.findByPrincipal();
			return (article.getNutritionist().getId() == nutritionist.getId() && article.getIsFinalMode());
		}
	}

	public final Comment reconstruct(final Comment comment, final BindingResult binding) {
		Comment result;

		result = this.create(comment.getArticle().getId());
		result.setText(comment.getText());

		this.validator.validate(result, binding);

		return result;
	}

	public void deleteCommentByArticlesByNutritionist(final int nutritionistId) {
		Collection<Comment> comments;

		comments = this.commentRepository.findCommentByArticlesByNutritionist(nutritionistId);
		this.commentRepository.delete(comments);
	}
}
