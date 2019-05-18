
package services;

import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under testing ---------------------
	@Autowired
	private CategoryService	categoryService;


	// Other supporting tests --------------------

	// Suite test --------------------------------

	/*
	 * A: Requirement 4 and 11.2 (An administrator lists categories).
	 * C: Analysis of sentence coverage: 23/23 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void list_positiveTest() {
		super.authenticate("admin1");

		Map<Integer, String> mapa;

		mapa = this.categoryService.categoriesByLanguage("en");

		Assert.notNull(mapa);
		Assert.notEmpty(mapa);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 4 and 11.2 (An administrator creates a category).
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void create_positiveTest() {
		super.authenticate("admin1");

		Category category;

		category = this.categoryService.create();

		Assert.notNull(category);
		Assert.isNull(category.getName());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 4 and 11.2 (An administrator deletes a category).
	 * B: The category is reference by at least one working out.
	 * C: Analysis of sentence coverage: 8/9 -> 88.89% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void delete_negativeTest_uno() {
		super.authenticate("admin1");

		int categoryId;
		Category category, found;

		categoryId = this.getEntityId("category1");

		category = this.categoryService.findOne(categoryId);

		this.categoryService.delete(category);

		found = this.categoryService.findOne(category.getId());

		Assert.notNull(found);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 4 and 11.2 (An administrator deletes a category).
	 * B: The category doesn't exist in database.
	 * C: Analysis of sentence coverage: 2/9 -> 22.22% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void delete_negativeTest_dos() {
		super.authenticate("admin1");

		Category category, found;

		category = this.categoryService.create();
		category.setName("hello,hola");

		found = this.categoryService.findOne(category.getId());
		Assert.isNull(found);

		this.categoryService.delete(category);

		found = this.categoryService.findOne(category.getId());
		Assert.isNull(found);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 4 and 11.2 (An administrator deletes a category).
	 * C: Analysis of sentence coverage: 9/9 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void delete_positiveTest() {
		super.authenticate("admin1");

		int categoryId;
		Category category, found;

		categoryId = this.getEntityId("category5");

		category = this.categoryService.findOne(categoryId);

		this.categoryService.delete(category);

		found = this.categoryService.findOne(category.getId());

		Assert.isNull(found);

		super.unauthenticate();
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 4 and 11.2 (An administrator can create/edit categories)
			 * B: Invalid data in category::name.
			 * C: Analysis of sentence coverage: 11/12 -> 91.67% executed code lines.
			 * D: Analysis of data coverage: Category::name is null => 1/7 -> 14.28%.
			 */
			{
				null, ConstraintViolationException.class
			},
			/*
			 * A: Requirement 4 and 11.2 (An administrator can create/edit categories)
			 * B: Invalid data in category::name.
			 * C: Analysis of sentence coverage: 11/12 -> 91.67% executed code lines.
			 * D: Analysis of data coverage: Category::name is empty string => 1/7 -> 14.28%.
			 */
			{
				"", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 4 and 11.2 (An administrator can create/edit categories)
			 * B: Invalid data in category::name.
			 * C: Analysis of sentence coverage: 11/12 -> 91.67% executed code lines.
			 * D: Analysis of data coverage: Category::name is a malicious script => 1/7 -> 14.28%.
			 */
			{
				"<script> Alert('hacked!!'); </script>", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 4 and 11.2 (An administrator can create/edit categories)
			 * B: Invalid data in category::name.
			 * C: Analysis of sentence coverage: 11/12 -> 91.67% executed code lines.
			 * D: Analysis of data coverage: Category::name is equal than other category's name => 1/7 -> 14.28%.
			 */
			{
				"Resistance,Resistencia", DataIntegrityViolationException.class
			},
			/*
			 * A: Requirement 4 and 11.2 (An administrator can create/edit categories)
			 * B: Invalid data in category::name.
			 * C: Analysis of sentence coverage: 7/12 -> 58.33% executed code lines.
			 * D: Analysis of data coverage: Category::name has a invalid format => 1/7 -> 14.28%.
			 */
			{
				"Resistance,Resistencia,Résistance", IllegalArgumentException.class
			},
			/*
			 * A: Requirement 4 and 11.2 (An administrator can create/edit categories)
			 * B: Invalid data in category::name.
			 * C: Analysis of sentence coverage: 10/12 -> 83.33% executed code lines.
			 * D: Analysis of data coverage: Category::name has a invalid format => 1/7 -> 14.28%.
			 */
			{
				",estiramientos", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void templateSave(final String name, final Class<?> expected) {
		Class<?> caught;
		Category category, saved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("admin1");

			category = this.categoryService.create();
			category.setName(name);

			saved = this.categoryService.save(category);
			this.categoryService.flush();

			Assert.notNull(saved);
			Assert.isTrue(saved.getId() != 0);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

}
