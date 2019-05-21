
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
import domain.Trainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TrainerServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private TrainerService	trainerService;


	// Other services and repositories ----------------------------------------

	// Test

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Register to the system as a trainer
	 */
	@Test
	public void registerTrainerDriver() {
		final Object testingData[][] = {
			/*
			 * B: Positive test
			 * 
			 * C: Approximately 100% of sentence coverage, since it has been
			 * covered 16 lines of code of 16 possible.
			 * 
			 * D: Approximately 64% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"TrainerTest", "TrainerTest", "TrainerTest", "http://www.google.com", "TrainerTest@us.es", "695478452", "Address Test", null
			},
			/*
			 * B: Trainer::name is blank
			 * 
			 * C: Approximately 56% of sentence coverage, since it has been
			 * covered 9 lines of code of 16 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"", "TrainerTest", "TrainerTest", "http://www.google.com", "TrainerTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Trainer::surname is blank
			 * 
			 * C: Approximately 56% of sentence coverage, since it has been
			 * covered 9 lines of code of 16 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"TrainerTest", "TrainerTest", "", "http://www.google.com", "TrainerTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Trainer::email is blank
			 * 
			 * C: Approximately 25% of sentence coverage, since it has been
			 * covered 4 lines of code of 16 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"TrainerTest", "TrainerTest", "TrainerTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerTrainerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	protected void registerTrainerTemplate(final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Trainer trainer, saved;
		UserAccount userAccount;
		Authority auth;

		super.startTransaction();

		caught = null;

		try {

			auth = new Authority();
			auth.setAuthority("TRAINER");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			trainer = this.trainerService.create();
			trainer.setName(name);
			trainer.setMiddleName(middleName);
			trainer.setSurname(surname);
			trainer.setAddress(address);
			trainer.setEmail(email);
			trainer.setPhoneNumber(phoneNumber);
			trainer.setPhoto(photo);

			saved = this.trainerService.save(trainer);
			this.trainerService.flush();

			trainer = this.trainerService.findOne(saved.getId());
			Assert.isTrue(saved.equals(trainer));

			Assert.isNull(saved.getMark());
			Assert.isNull(saved.getScore());
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
	 * covered 16 lines of code of 16 possible.
	 * 
	 * D: Approximately 8% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test
	public void save_positive_test() {
		Trainer trainer, saved;
		String oldName;

		super.authenticate("trainer1");

		this.startTransaction();

		trainer = this.trainerService.findOneToDisplayEdit(super.getEntityId("trainer1"));

		oldName = trainer.getName();

		trainer.setName("TEST");

		saved = this.trainerService.save(trainer);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Trainer::name is blank
	 * 
	 * C: Approximately 56% of sentence coverage, since it has been
	 * covered 9 lines of code of 16 possible.
	 * 
	 * D: Approximately 60% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test() {
		Trainer trainer, saved;
		String oldName;

		super.authenticate("trainer1");

		this.startTransaction();

		trainer = this.trainerService.findOneToDisplayEdit(super.getEntityId("trainer1"));

		oldName = trainer.getName();

		trainer.setName("");

		saved = this.trainerService.save(trainer);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

}
