
package services;

import java.util.Arrays;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Nutritionist;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NutritionistServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private NutritionistService	nutritionistService;


	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Create an account for a new nutritionist
	 */
	@Test
	public void registerNutritionistDriver() {
		final Object testingData[][] = {
			/*
			 * B: Positive test
			 * 
			 * C: Approximately 100% of sentence coverage, since it has been
			 * covered 18 lines of code of 18 possible.
			 * 
			 * D: Approximately 64% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "NutritionistTest", "NutritionistTest", "NutritionistTest", "http://www.google.com", "NutritionistTest@us.es", "695478452", "Address Test", null
			},
			/*
			 * B: Nutritionist::name is blank
			 * 
			 * C: Approximately 61% of sentence coverage, since it has been
			 * covered 11 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "", "NutritionistTest", "NutritionistTest", "http://www.google.com", "NutritionistTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Nutritionist::surname is blank
			 * 
			 * C: Approximately 61% of sentence coverage, since it has been
			 * covered 11 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "NutritionistTest", "NutritionistTest", "", "http://www.google.com", "NutritionistTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Nutritionist::email is blank
			 * 
			 * C: Approximately 33% of sentence coverage, since it has been
			 * covered 6 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "NutritionistTest", "NutritionistTest", "NutritionistTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},
			/*
			 * B: Actor unauthenticated tries to register an Nutritionist account
			 * 
			 * C: Approximately 11% of sentence coverage, since it has been
			 * covered 2 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				null, "NutritionistTest", "NutritionistTest", "NutritionistTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},
			/*
			 * B: A company tries to register an Nutritionist
			 * 
			 * C: Approximately 17% of sentence coverage, since it has been
			 * covered 3 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"company1", "NutritionistTest", "NutritionistTest", "NutritionistTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerNutritionistTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void registerNutritionistTemplate(final String username, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Nutritionist nutritionist, saved;
		UserAccount userAccount;
		Authority auth;

		super.startTransaction();

		caught = null;

		try {
			super.authenticate(username);

			auth = new Authority();
			auth.setAuthority("NUTRITIONIST");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			nutritionist = this.nutritionistService.create();
			nutritionist.setName(name);
			nutritionist.setMiddleName(middleName);
			nutritionist.setSurname(surname);
			nutritionist.setAddress(address);
			nutritionist.setEmail(email);
			nutritionist.setPhoneNumber(phoneNumber);
			nutritionist.setPhoto(photo);

			saved = this.nutritionistService.save(nutritionist);
			this.nutritionistService.flush();

			nutritionist = this.nutritionistService.findOne(saved.getId());
			Assert.isTrue(saved.equals(nutritionist));

			Assert.isTrue(!saved.getIsSuspicious());

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.rollbackTransaction();
		super.checkExceptions(expected, caught);

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 18 lines of code of 18 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test
	public void save_positive_test() {
		Nutritionist nutritionist, saved;
		String oldName;

		super.authenticate("nutritionist1");

		this.startTransaction();

		nutritionist = this.nutritionistService.findOneToDisplayEdit(super.getEntityId("nutritionist1"));

		oldName = nutritionist.getName();

		nutritionist.setName("TEST");

		saved = this.nutritionistService.save(nutritionist);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Nutritionist::name is blank
	 * 
	 * C: Approximately 61% of sentence coverage, since it has been
	 * covered 11 lines of code of 18 possible.
	 * 
	 * D: Approximately 60% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test() {
		Nutritionist nutritionist, saved;
		String oldName;

		super.authenticate("nutritionist1");

		this.startTransaction();

		nutritionist = this.nutritionistService.findOneToDisplayEdit(super.getEntityId("nutritionist1"));

		oldName = nutritionist.getName();

		nutritionist.setName("");

		saved = this.nutritionistService.save(nutritionist);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

}
