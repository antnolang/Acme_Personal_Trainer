
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
		Date moment;

		moment = this.utilityService.current_moment();

		result = new Comment();
		result.setArticle(article);
		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
			result.setCustomer(this.customerService.findByPrincipal());

		result.setPublicationMoment(moment);
		return result;
	}

	public Comment save(final Comment comment) {
		Assert.isTrue(this.principalCanWrite(comment.getArticle()));
		Comment result;

		result = this.commentRepository.save(comment);

		return result;
	}

	public Comment findOne(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);

		Assert.notNull(result);

		return result;
	}

	public Comment findOneToEdit(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);

		Assert.notNull(result);
		Assert.isNull(this.commentRepository.findOne(commentId));
		Assert.isTrue(this.principalCanWrite(result.getArticle()));

		return result;
	}

	public Comment findOneToDisplay(final int commentId) {
		Comment result;

		result = this.findOne(commentId);

		Assert.notNull(result);
		Assert.isTrue(this.principalCanWrite(result.getArticle()));

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

	public Comment reconstruct(final Comment comment, final BindingResult binding, final int articleId) {
		Comment result;

		result = this.create(articleId);
		result.setText(comment.getText().trim());

		this.validator.validate(result, binding);

		return result;
	}

	public void deleteCommentByArticlesByNutritionist(final int nutritionistId) {
		Collection<Comment> comments;

		comments = this.commentRepository.findCommentByArticlesByNutritionist(nutritionistId);
		this.commentRepository.delete(comments);
	}

	public Collection<Comment> findCommentsByArticle(final int articleId) {
		Collection<Comment> comments;
		Article article;

		comments = this.commentRepository.findCommentsByArticle(articleId);
		article = this.articleService.findOne(articleId);

		Assert.isTrue(article.getIsFinalMode());
		Assert.isTrue(this.principalCanWrite(article));

		return comments;
	}
}
