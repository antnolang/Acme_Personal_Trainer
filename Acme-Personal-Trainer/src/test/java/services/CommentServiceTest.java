
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// The SUT ---------------------------
	@Autowired
	private ArticleService	articleService;

	@Autowired
	private CommentService	commentService;


	//Test ------------------------------------------------

	@Test
	public void driverCreateCommentCustomer() {
		final Object testingData[][] = {
			/*
			 * A: Req.10.1 Create comment
			 * B: Test positivo
			 * C: 100%. 28/28 Recorre 28 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer1", "article1", "Text", null
			},
			/*
			 * A: Req.10.1 Create comment
			 * B: El articulo no está en modo final
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer1", "article2", "Text", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Create comment
			 * B: El customer no es premium
			 * C: 67%. 19/28 Recorre 19 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer2", "article1", "Text", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateCustomer((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void templateCreateCustomer(final String username, final int articleId, final String text, final Class<?> expected) {
		Class<?> caught;
		Comment comment, commentSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			comment = this.commentService.create(articleId);
			comment.setText(text);
			commentSaved = this.commentService.save(comment);
			this.commentService.flush();

			Assert.notNull(commentSaved);
			Assert.isTrue(commentSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateCommentNutritionist() {
		final Object testingData[][] = {
			/*
			 * A: Req.10.1 Create comment
			 * B: Test positivo
			 * C: 100%. 28/28 Recorre 28 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"nutritionist1", "article1", "Text", null
			},
			/*
			 * A: Req.10.1 Create comment
			 * B: El nutricionista no puede comentar el articulo porque no es el que lo ha creado
			 * C: 71%. 20/28 Recorre 20 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"nutritionist1", "article3", "Text", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Create comment
			 * B: El nutricionista no puede comentar el articulo porque está en modo no final
			 * C: 67%. 19/28 Recorre 19 de las 28 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"nutritionist1", "article2", "Text", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateNutritionist((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void templateCreateNutritionist(final String username, final int articleId, final String text, final Class<?> expected) {
		Class<?> caught;
		Comment comment, commentSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			comment = this.commentService.create(articleId);
			comment.setText(text);
			commentSaved = this.commentService.save(comment);
			this.commentService.flush();

			Assert.notNull(commentSaved);
			Assert.isTrue(commentSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testListCommentNutritionistPositive() {
		Collection<Comment> comments;
		int commentId, numberComments;
		Comment comment1, comment2, comment3;

		super.authenticate("nutritionist1");

		commentId = super.getEntityId("comment1");
		comment1 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment2");
		comment2 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment6");
		comment3 = this.commentService.findOne(commentId);
		numberComments = 3;

		comments = this.commentService.findCommentsByArticle(comment1.getArticle().getId());

		super.unauthenticate();

		Assert.isTrue(comments.contains(comment1) && comments.contains(comment2) && comments.contains(comment3));
		Assert.isTrue(comments.size() == numberComments);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Debe listar también el comment7
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListCommentNutritionistNegative1() {
		Collection<Comment> comments;
		int commentId, numberComments;
		Comment comment3;

		super.authenticate("nutritionist1");

		commentId = super.getEntityId("comment3");
		comment3 = this.commentService.findOne(commentId);
		numberComments = 1;

		comments = this.commentService.findCommentsByArticle(comment3.getArticle().getId());

		super.unauthenticate();

		Assert.isTrue(comments.contains(comment3));
		Assert.isTrue(comments.size() == numberComments);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: El nutritionist 2 no puede acceder a los comentarios de los articulos de otro nutritionist
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListCommentNutritionistNegative2() {
		Collection<Comment> comments;
		int commentId, numberComments;
		Comment comment1, comment2;

		super.authenticate("nutritionist2");

		commentId = super.getEntityId("comment1");
		comment1 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment2");
		comment2 = this.commentService.findOne(commentId);
		numberComments = 2;

		comments = this.commentService.findCommentsByArticle(comment2.getArticle().getId());

		super.unauthenticate();

		Assert.isTrue(comments.contains(comment1) && comments.contains(comment2));
		Assert.isTrue(comments.size() == numberComments);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testListCommentCustomerPositive() {
		Collection<Comment> comments;
		int commentId, numberComments;
		Comment comment1, comment2, comment3;

		super.authenticate("customer1");

		commentId = super.getEntityId("comment1");
		comment1 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment2");
		comment2 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment6");
		comment3 = this.commentService.findOne(commentId);
		numberComments = 3;

		comments = this.commentService.findCommentsByArticle(comment1.getArticle().getId());

		super.unauthenticate();

		Assert.isTrue(comments.contains(comment1) && comments.contains(comment2) && comments.contains(comment3));
		Assert.isTrue(comments.size() == numberComments);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Debe listar también el comment7
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListCommentCustomerNegative1() {
		Collection<Comment> comments;
		int commentId, numberComments;
		Comment comment1, comment2, comment3;

		super.authenticate("customer2");

		commentId = super.getEntityId("comment1");
		comment1 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment2");
		comment2 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment6");
		comment3 = this.commentService.findOne(commentId);
		numberComments = 3;

		comments = this.commentService.findCommentsByArticle(comment1.getArticle().getId());

		super.unauthenticate();

		Assert.isTrue(comments.contains(comment1) && comments.contains(comment2) && comments.contains(comment3));
		Assert.isTrue(comments.size() == numberComments);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: El nutritionist 2 no puede acceder a los comentarios de los articulos de otro nutritionist
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testListCommentCustomerNegative2() {
		Collection<Comment> comments;
		int commentId, numberComments;
		Comment comment1, comment2;

		super.authenticate("customer1");

		commentId = super.getEntityId("comment1");
		comment1 = this.commentService.findOne(commentId);
		commentId = super.getEntityId("comment2");
		comment2 = this.commentService.findOne(commentId);
		numberComments = 2;

		comments = this.commentService.findCommentsByArticle(comment1.getArticle().getId());

		super.unauthenticate();

		Assert.isTrue(comments.contains(comment1) && comments.contains(comment2));
		Assert.isTrue(comments.size() == numberComments);
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 14 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testPositiveDisplayComment() {
		int commentId;
		Comment comment, stored;

		super.authenticate("nutritionist1");

		commentId = super.getEntityId("comment1");
		stored = this.commentService.findOne(commentId);
		comment = this.commentService.findOneToDisplay(commentId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(comment));
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: The comment to display must belong to the commentor principal.
	 * 
	 * C: 92.8% of sentence coverage, since it has been covered
	 * 13 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDisplayComment1() {
		int commentId;
		Comment comment, stored;

		super.authenticate("nutritionist2");

		commentId = super.getEntityId("comment1");
		stored = this.commentService.findOne(commentId);
		comment = this.commentService.findOneToDisplay(commentId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(comment));
	}

	/*
	 * A: An actor who is authenticated as an commentor must be able to
	 * manage his or her comments, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: The comment to display must belong to the commentor principal.
	 * 
	 * C: 92.8% of sentence coverage, since it has been covered
	 * 13 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDisplayComment2() {
		int commentId;
		Comment comment, stored;

		super.authenticate("customer2");

		commentId = super.getEntityId("comment1");
		stored = this.commentService.findOne(commentId);
		comment = this.commentService.findOneToDisplay(commentId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(comment));
	}

}
