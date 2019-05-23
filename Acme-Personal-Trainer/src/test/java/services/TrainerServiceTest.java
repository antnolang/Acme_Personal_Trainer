
package services;

import java.util.Arrays;
import java.util.Collection;

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
import domain.Customer;
import domain.Endorsement;
import domain.Trainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TrainerServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private TrainerService		trainerService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;


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
	public void save_negative_test1() {
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

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Trainer::name is blank
	 * 
	 * C: Approximately 86% of sentence coverage, since it has been
	 * covered 6 lines of code of 7 possible.
	 * 
	 * D: Approximately 60% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void save_negative_test2() {
		Trainer trainer, saved;
		String oldName;

		super.authenticate("trainer1");

		this.startTransaction();

		trainer = this.trainerService.findOneToDisplayEdit(super.getEntityId("trainer2"));

		oldName = trainer.getName();

		trainer.setName("");

		saved = this.trainerService.save(trainer);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * List his/her endorsement
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void list_received_sent_endorsements_test() {
		Trainer trainer;
		Collection<Endorsement> receiveds, sents;

		super.authenticate("trainer1");

		trainer = this.trainerService.findOneToDisplayEdit(super.getEntityId("trainer1"));

		receiveds = this.endorsementService.findReceivedEndorsementsByTrainer(trainer.getId());
		sents = this.endorsementService.findSendEndorsementsByTrainer(trainer.getId());

		Assert.notNull(receiveds);
		Assert.notNull(sents);

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Display one of his/her endorsements
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void display_endorsement_positive_test() {
		Endorsement endorsement;

		super.authenticate("trainer1");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));

		Assert.notNull(endorsement);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Display one of his/her endorsements
	 * 
	 * B: A trainer tries to display an endorsemenet that does not belongs to him/her
	 * 
	 * C: 92% of sentence coverage. -> It has covered 12 lines of 13.
	 * 
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_endorsement_negative_test() {
		Endorsement endorsement;

		super.authenticate("trainer2");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));

		Assert.notNull(endorsement);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Create an endorsement
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: 100% of data coverage because every fields are obligatory.
	 */
	@Test
	public void create_endorsement_positive_test() {
		Endorsement endorsement, saved;
		Customer customer;

		super.authenticate("trainer1");

		endorsement = this.endorsementService.create();
		customer = this.customerService.findOne(super.getEntityId("customer1"));

		endorsement.setComments("TEST");
		endorsement.setMark(7);
		endorsement.setCustomer(customer);

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Create an endorsement
	 * 
	 * B: A trainer tries to create an endorsement for a customer that not attends his/her
	 * working outs.
	 * 
	 * C: 49% of sentence coverage -> It has covered 3 lines of 7.
	 * 
	 * D: 100% of data coverage because every fields are obligatory.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void create_endorsement_negative_test1() {
		Endorsement endorsement, saved;
		Customer customer;

		super.authenticate("trainer1");

		endorsement = this.endorsementService.create();
		customer = this.customerService.findOne(super.getEntityId("customer2"));

		endorsement.setComments("TEST");
		endorsement.setMark(7);
		endorsement.setCustomer(customer);

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Create an endorsement
	 * 
	 * B: Endorsement:coment is blank.
	 * 
	 * C: 71% of sentence coverage -> It has covered 5 lines of 7.
	 * 
	 * D: 100% of data coverage because every fields are obligatory.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void create_endorsement_negative_test2() {
		Endorsement endorsement, saved;
		Customer customer;

		super.authenticate("trainer1");

		endorsement = this.endorsementService.create();
		customer = this.customerService.findOne(super.getEntityId("customer2"));

		endorsement.setComments("");
		endorsement.setMark(7);
		endorsement.setCustomer(customer);

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Update an endorsement
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: 100% of data coverage because every fields are obligatory.
	 */
	@Test
	public void save_endorsement_positive_test() {
		Endorsement endorsement, saved;

		super.authenticate("trainer1");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));

		endorsement.setComments("TEST");

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to:
	 * Update an endorsement
	 * 
	 * B: A trainer tries to updates an endorsement that it has been writed by a customer
	 * 
	 * C: 29% of sentence coverage -> Its covered 2 lines of 7.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void save_endorsement_negative_test() {
		Endorsement endorsement, saved;

		super.authenticate("trainer1");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement2"));

		endorsement.setComments("TEST");

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();

	}
}
