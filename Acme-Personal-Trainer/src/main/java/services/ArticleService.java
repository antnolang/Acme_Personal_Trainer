
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import security.LoginService;
import domain.Article;
import domain.Customer;
import domain.Nutritionist;

@Service
@Transactional
public class ArticleService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ArticleRepository	articleRepository;

	// Supporting services -------------------------------------------
	@Autowired
	private NutritionistService	nutritionistService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private Validator			validator;


	//Constructor ----------------------------------------------------
	public ArticleService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Article create() {
		Article result;
		Nutritionist nutritionist;

		nutritionist = this.nutritionistService.findByPrincipal();
		result = new Article();

		result.setNutritionist(nutritionist);

		return result;
	}

	public Article save(final Article article) {
		Assert.notNull(article);
		this.checkByPrincipal(article);
		Article result;

		result = this.articleRepository.save(article);

		return result;
	}

	public Article findOne(final int articleId) {
		Article result;

		result = this.articleRepository.findOne(articleId);

		Assert.notNull(result);

		return result;
	}

	public Article findOneToDisplayCustomer(final int articleId) {
		Article result;

		result = this.articleRepository.findOne(articleId);

		Assert.notNull(result);
		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]")) {
			final Customer customer;
			customer = this.customerService.findByPrincipal();
			Assert.isTrue(customer.getIsPremium());
		}
		Assert.isTrue(result.getIsFinalMode());

		return result;
	}

	public Article findOneToDisplayNutritionist(final int articleId) {
		Article result;

		result = this.articleRepository.findOne(articleId);

		Assert.notNull(result);
		if (!(result.getIsFinalMode()))
			this.checkByPrincipal(result);

		return result;
	}

	public Article findOneToNutritionistEdit(final int articleId) {
		Article result;

		result = this.findOne(articleId);

		this.checkByPrincipal(result);
		Assert.isTrue(!(result.getIsFinalMode()));

		return result;
	}

	public Collection<Article> findAll() {
		Collection<Article> results;

		results = this.articleRepository.findAll();

		return results;
	}

	public void delete(final Article article) {
		Assert.notNull(article);
		Assert.isTrue(this.articleRepository.exists(article.getId()));
		this.checkByPrincipal(article);

		this.articleRepository.delete(article);
	}

	// This method id used when an actor want to delete all his or her data.
	public void deleteArticlesByNutritionist(final Nutritionist nutritionist) {
		Collection<Article> articles;

		this.commentService.deleteCommentByArticlesByNutritionist(nutritionist.getId());
		articles = this.articleRepository.findArticlesByNutritionist(nutritionist.getId());
		this.articleRepository.delete(articles);
	}

	// Other business methods ---------------------

	public void makeFinal(final Article article) {
		this.checkByPrincipal(article);

		article.setIsFinalMode(true);
		article.setPublishedMoment(this.utilityService.current_moment());
	}

	public Article reconstruct(final Article article, final BindingResult binding) {
		Article result, stored_article;

		if (article.getId() == 0)
			result = this.create();
		else {
			stored_article = this.findOne(article.getId());

			result = new Article();
			result.setId(stored_article.getId());
			result.setVersion(stored_article.getVersion());
			result.setNutritionist(stored_article.getNutritionist());
		}

		result.setTitle(article.getTitle().trim());
		result.setDescription(article.getDescription().trim());
		result.setTags(article.getTags().trim());

		this.validator.validate(result, binding);

		return result;
	}

	private void checkByPrincipal(final Article article) {
		Nutritionist nutritionist;

		nutritionist = this.nutritionistService.findByPrincipal();

		Assert.isTrue(nutritionist.equals(article.getNutritionist()));
	}

	public Collection<Article> findArticlesByNutritionist(final int nutritionistId) {
		Collection<Article> results;

		results = this.articleRepository.findArticlesByNutritionist(nutritionistId);

		return results;
	}

	protected void flush() {
		this.articleRepository.flush();
	}

	public Collection<Article> findArticlesByPrincipal() {
		Collection<Article> results;
		Nutritionist nutritionist;

		nutritionist = this.nutritionistService.findByPrincipal();
		results = this.articleRepository.findArticlesByNutritionist(nutritionist.getId());

		return results;

	}

	public Collection<Article> findFinalArticle() {
		Collection<Article> articles;

		articles = this.articleRepository.findFinalArticle();

		return articles;
	}
}
