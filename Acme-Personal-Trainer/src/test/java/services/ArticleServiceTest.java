
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ArticleServiceTest extends AbstractTest {

	// Service under testing ---------------------------------------------

	@Autowired
	private ArticleService	articleService;


	// Other supporting services and repositories ------------------------

	// Tests -------------------------------------------------------------

	@Test
	public void driverCreateArticleNutritionist() {
		final Object testingData[][] = {
			/*
			 * A: Req.43. Every nutritionist can write a article.
			 * B: Test positivo
			 * C: 100%. 28/28 Recorre 28 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"nutritionist1", "articlePrueba1", "Description1", null
			},
			/*
			 * A: Req.43. Every nutritionist can write a article.
			 * B: El titulo del articulo no puede ser nulo.
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"nutritionist1", null, "Text", ConstraintViolationException.class
			},
			/*
			 * A: Req.43. Every nutritionist can write a article.
			 * B: La descripcion del articulo no puede ser vacía.
			 * C: 67%. 19/28 Recorre 19 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"nutritionist1", "articlePrueba2", "", ConstraintViolationException.class
			},
			/*
			 * A: Req.43. Every nutritionist can write a article.
			 * B: Solo pueden crear articulos los nutricionistas.
			 * C: 67%. 19/28 Recorre 19 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer1", "articlePrueba3", "Description1", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateNutritionist((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void templateCreateNutritionist(final String username, final String title, final String description, final Class<?> expected) {
		Class<?> caught;
		Article article, articleSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			article = this.articleService.create();
			article.setTitle(title);
			article.setDescription(description);
			articleSaved = this.articleService.save(article);
			this.articleService.flush();

			Assert.notNull(articleSaved);
			Assert.isTrue(articleSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testEditArticlePositive() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article2");
		article1 = this.articleService.findOne(articleId);

		article1.setDescription("nueva descripcion");
		this.articleService.save(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: El nutricionista no puede editar un articulo en modo final
	 * 
	 * C: 84.6% of sentence coverage, since it has been covered
	 * 11 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditArticleNegative1() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);

		article1.setDescription("nueva descripcion");
		this.articleService.save(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: El nutricionista no puede editar un árticulo que no ha escrito él
	 * 
	 * C: 80% of sentence coverage, since it has been covered
	 * 10 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEditArticleNegative2() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist2");

		articleId = super.getEntityId("article2");
		article1 = this.articleService.findOne(articleId);

		article1.setDescription("nueva descripcion");
		this.articleService.save(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testListArticleNutritionistPositive() {
		Collection<Article> articles;
		int articleId, numberArticles;
		Article article1, article2;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article2");
		article2 = this.articleService.findOne(articleId);
		numberArticles = 2;

		articles = this.articleService.findArticlesByPrincipal();

		super.unauthenticate();

		Assert.isTrue(articles.contains(article1) && articles.contains(article2));
		Assert.isTrue(articles.size() == numberArticles);
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Al nutricionista debe aparecerle tanto los articulos finales como los no finales
	 * 
	 * C: 80% of sentence coverage, since it has been covered
	 * 10 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListArticleNutritionistNegative1() {
		Collection<Article> articles;
		int articleId, numberArticles;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);
		numberArticles = 1;

		articles = this.articleService.findArticlesByPrincipal();

		super.unauthenticate();

		Assert.isTrue(articles.contains(article1));
		Assert.isTrue(articles.size() == numberArticles);
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Un nutricionista no puede listar los articulos de otro
	 * 
	 * C: 84.6% of sentence coverage, since it has been covered
	 * 11 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListArticleNutritionistNegative2() {
		Collection<Article> articles;
		int articleId, numberArticles;
		Article article1, article2;

		super.authenticate("nutritionist2");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article2");
		article2 = this.articleService.findOne(articleId);
		numberArticles = 2;

		articles = this.articleService.findArticlesByPrincipal();

		super.unauthenticate();

		Assert.isTrue(articles.contains(article1) && articles.contains(article2));
		Assert.isTrue(articles.size() == numberArticles);
	}

	/*
	 * A: Req 47.1 An actor who is authenticated as a customer must be able to only premium customer can list
	 * and displaying the articles that are published by the nutritionist.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testListArticleCustomerPositive() {
		Collection<Article> articles;
		int articleId, numberArticles;
		Article article1, article3, article4, article5, article6;

		super.authenticate("customer1");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article3");
		article3 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article4");
		article4 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article5");
		article5 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article6");
		article6 = this.articleService.findOne(articleId);
		numberArticles = 5;

		articles = this.articleService.findFinalArticle();

		super.unauthenticate();

		Assert.isTrue(articles.contains(article1) && articles.contains(article3) && articles.contains(article4) && articles.contains(article5) && articles.contains(article6));
		Assert.isTrue(articles.size() == numberArticles);
	}

	/*
	 * A: Req 47.1 An actor who is authenticated as a customer must be able to only premium customer can list
	 * and displaying the articles that are published by the nutritionist.
	 * 
	 * B: Al cliente no debe aparecerle en el listado los articulos en modo no final
	 * 
	 * C: 84.6% of sentence coverage, since it has been covered
	 * 11 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListArticleCustomerNegative1() {
		Collection<Article> articles;
		int articleId, numberArticles;
		Article article1, article2, article3, article4, article5, article6;

		super.authenticate("customer1");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article2");
		article2 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article3");
		article3 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article4");
		article4 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article5");
		article5 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article6");
		article6 = this.articleService.findOne(articleId);
		numberArticles = 6;

		articles = this.articleService.findFinalArticle();

		super.unauthenticate();

		Assert.isTrue(articles.contains(article1) && articles.contains(article2) && articles.contains(article3) && articles.contains(article4) && articles.contains(article5) && articles.contains(article6));
		Assert.isTrue(articles.size() == numberArticles);
	}

	/*
	 * A: Req 47.1 An actor who is authenticated as a customer must be able to only premium customer can list
	 * and displaying the articles that are published by the nutritionist.
	 * 
	 * B: Al cliente deben aparecerle todos los articulos en modo final existentes. Falta el article6.
	 * 
	 * C: 80% of sentence coverage, since it has been covered
	 * 10 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListArticleCustomerNegative2() {
		Collection<Article> articles;
		int articleId, numberArticles;
		Article article1, article3, article4, article5;

		super.authenticate("customer2");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article3");
		article3 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article4");
		article4 = this.articleService.findOne(articleId);
		articleId = super.getEntityId("article5");
		article5 = this.articleService.findOne(articleId);
		numberArticles = 4;

		articles = this.articleService.findFinalArticle();

		super.unauthenticate();

		Assert.isTrue(articles.contains(article1) && articles.contains(article3) && articles.contains(article4) && articles.contains(article5));
		Assert.isTrue(articles.size() == numberArticles);
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testMakeFinalArticlePositive() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article2");
		article1 = this.articleService.findOne(articleId);

		this.articleService.makeFinal(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: El articulo 3 ya está en Modo Final
	 * 
	 * C: 80% of sentence coverage, since it has been covered
	 * 10 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMakeFinalArticleNegative1() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article3");
		article1 = this.articleService.findOne(articleId);

		this.articleService.makeFinal(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: El nutricionista2 no puede marcar en modo final un articulo de otro nutricionista
	 * 
	 * C: 84.6% of sentence coverage, since it has been covered
	 * 11 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMakeFinalArticleNegative2() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist2");

		articleId = super.getEntityId("article2");
		article1 = this.articleService.findOne(articleId);

		this.articleService.makeFinal(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testDeleteArticlePositive() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article2");
		article1 = this.articleService.findOne(articleId);

		this.articleService.delete(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: No se puede eliminar un articulo en modo final
	 * 
	 * C: 84.6% of sentence coverage, since it has been covered
	 * 11 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteArticleNegative1() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article1");
		article1 = this.articleService.findOne(articleId);

		this.articleService.delete(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: No se puede eliminar un articulo creado por otro nutricionista
	 * 
	 * C: 80% of sentence coverage, since it has been covered
	 * 10 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteArticleNegative2() {
		int articleId;
		Article article1;

		super.authenticate("nutritionist2");

		articleId = super.getEntityId("article2");
		article1 = this.articleService.findOne(articleId);

		this.articleService.delete(article1);

		this.articleService.flush();

		super.unauthenticate();
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 14 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testPositiveDisplayArticle() {
		int articleId;
		Article article, stored;

		super.authenticate("nutritionist1");

		articleId = super.getEntityId("article1");
		stored = this.articleService.findOne(articleId);
		article = this.articleService.findOneToDisplayNutritionist(articleId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(article));
	}

	/*
	 * A: Req. 46.1 An actor who is authenticated as a nutritionist must be able to
	 * manage his or her articles, which includes listing them, showing them, creating,
	 * updating and deleting them. An article can be saved in draft mode; once they are
	 * saved in final mode, they cant be edited.
	 * 
	 * B: Un nutricionista no puede mostrar un articulo creado por nutricionista
	 * 
	 * C: 92.8% of sentence coverage, since it has been covered
	 * 13 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDisplayArticle1() {
		int articleId;
		Article article, stored;

		super.authenticate("nutritionist2");

		articleId = super.getEntityId("article1");
		stored = this.articleService.findOne(articleId);
		article = this.articleService.findOneToDisplayNutritionist(articleId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(article));
	}

	/*
	 * A: Req 47.1 An actor who is authenticated as a customer must be able to only premium customer can list
	 * and displaying the articles that are published by the nutritionist.
	 * 
	 * B: un customer no premium no puede visualizar ningun articulo
	 * 
	 * C: 92.8% of sentence coverage, since it has been covered
	 * 13 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDisplayArticle2() {
		int articleId;
		Article article, stored;

		super.authenticate("customer2");

		articleId = super.getEntityId("article1");
		stored = this.articleService.findOne(articleId);
		article = this.articleService.findOneToDisplayCustomer(articleId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(article));
	}

}
