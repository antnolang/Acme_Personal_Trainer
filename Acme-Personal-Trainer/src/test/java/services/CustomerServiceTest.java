
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.Endorsement;
import domain.Trainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private ApplicationService	applicationService;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The listing of customers who have got accepted at least 10% more
	 * applications than the average, ordered by name.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testFindUsualCustomers() {
		Collection<Customer> customers;
		Customer c1, c3;
		int c1Id, c3Id, numberCustomers;

		customers = this.customerService.findUsualCustomers();
		c1Id = super.getEntityId("customer1");
		c1 = this.customerRepository.findOne(c1Id);
		c3Id = super.getEntityId("customer3");
		c3 = this.customerRepository.findOne(c3Id);
		numberCustomers = 2;

		Assert.isTrue(customers.contains(c1));
		Assert.isTrue(customers.contains(c3));
		Assert.isTrue(customers.size() == numberCustomers);
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The top-three customers that write more endorsement.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testCustomerWriteMostEndorsement() {
		Collection<Customer> customers;
		Customer c1;
		int c1Id;

		customers = this.customerService.findCustomerWriteMostEndorsement();
		c1Id = super.getEntityId("customer1");
		c1 = this.customerRepository.findOne(c1Id);

		Assert.isTrue(customers.contains(c1));
		Assert.isTrue(customers.size() == 1);
	}

	// Test

	/*
	 * A: An actor who is not authenticated must be able to:
	 * Register to the system as a customer
	 */
	@Test
	public void registerCustomerDriver() {
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
				"CustomerTest", "CustomerTest", "CustomerTest", "http://www.google.com", "customerTest@us.es", "695478452", "Address Test", null
			},
			/*
			 * B: Customer::name is blank
			 * 
			 * C: Approximately 56% of sentence coverage, since it has been
			 * covered 9 lines of code of 16 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"", "CustomerTest", "CustomerTest", "http://www.google.com", "customerTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Customer::surname is blank
			 * 
			 * C: Approximately 56% of sentence coverage, since it has been
			 * covered 9 lines of code of 16 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CustomerTest", "CustomerTest", "", "http://www.google.com", "customerTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Customer::email is blank
			 * 
			 * C: Approximately 25% of sentence coverage, since it has been
			 * covered 4 lines of code of 16 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"CustomerTest", "CustomerTest", "CustomerTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerCustomerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

	protected void registerCustomerTemplate(final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Customer customer, saved;
		UserAccount userAccount;
		Authority auth;

		super.startTransaction();

		caught = null;

		try {

			auth = new Authority();
			auth.setAuthority("CUSTOMER");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			customer = this.customerService.create();
			customer.setName(name);
			customer.setMiddleName(middleName);
			customer.setSurname(surname);
			customer.setAddress(address);
			customer.setEmail(email);
			customer.setPhoneNumber(phoneNumber);
			customer.setPhoto(photo);

			saved = this.customerService.save(customer);
			this.customerService.flush();

			customer = this.customerService.findOne(saved.getId());
			Assert.isTrue(saved.equals(customer));

			Assert.isTrue(!saved.getIsPremium());
			Assert.isTrue(!saved.getIsSuspicious());

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
		Customer customer, saved;
		String oldName;

		super.authenticate("customer1");

		this.startTransaction();

		customer = this.customerService.findOneToDisplayEdit(super.getEntityId("customer1"));

		oldName = customer.getName();

		customer.setName("TEST");

		saved = this.customerService.save(customer);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: Customer::name is blank
	 * 
	 * C: Approximately 56% of sentence coverage, since it has been
	 * covered 9 lines of code of 16 possible.
	 * 
	 * D: Approximately 60% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test1() {
		Customer customer, saved;
		String oldName;

		super.authenticate("customer1");

		this.startTransaction();

		customer = this.customerService.findOneToDisplayEdit(super.getEntityId("customer1"));

		oldName = customer.getName();

		customer.setName("");

		saved = this.customerService.save(customer);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: A customer tries to edit the personal data of other customer
	 * 
	 * C: Approximately 86% of sentence coverage, since it has been
	 * covered 6 lines of code of 7 possible.
	 * 
	 * D: Approximately 60% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void save_negative_test2() {
		Customer customer, saved;
		String oldName;

		super.authenticate("customer1");

		this.startTransaction();

		customer = this.customerService.findOneToDisplayEdit(super.getEntityId("customer2"));

		oldName = customer.getName();

		customer.setName("");

		saved = this.customerService.save(customer);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as a customer must be able to:
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
		Customer customer;
		Collection<Endorsement> receiveds, sents;

		super.authenticate("customer1");

		customer = this.customerService.findOneToDisplayEdit(super.getEntityId("customer1"));

		receiveds = this.endorsementService.findReceivedEndorsementsByCustomer(customer.getId());
		sents = this.endorsementService.findSendEndorsementsByCustomer(customer.getId());

		Assert.notNull(receiveds);
		Assert.notNull(sents);

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as a customer must be able to:
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

		super.authenticate("customer1");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement2"));

		Assert.notNull(endorsement);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to:
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
		Trainer trainer;
		Application application;

		super.authenticate("customer1");

		application = this.applicationService.findOne(super.getEntityId("application4"));

		application.setCustomer(this.customerService.findOne(super.getEntityId("customer1")));

		super.unauthenticate();

		super.authenticate("trainer3");

		this.applicationService.acceptedApplication(application);
		Assert.isTrue(application.getStatus().equals("ACCEPTED"));

		super.unauthenticate();

		super.authenticate("customer1");

		endorsement = this.endorsementService.create();
		trainer = this.trainerService.findOne(super.getEntityId("trainer3"));

		endorsement.setComments("TEST");
		endorsement.setMark(7);
		endorsement.setTrainer(trainer);

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();
	}
	/*
	 * A: An actor who is authenticated as a customer must be able to:
	 * Create an endorsement
	 * 
	 * B: A customer tries to create an endorsement for a trainer that not attends his/her
	 * working outs.
	 * 
	 * C: 38% of sentence coverage -> It has covered 3 lines of 8.
	 * 
	 * D: 100% of data coverage because every fields are obligatory.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void create_endorsement_negative_test1() {
		Endorsement endorsement, saved;
		Trainer trainer;

		super.authenticate("customer1");

		endorsement = this.endorsementService.create();
		trainer = this.trainerService.findOne(super.getEntityId("trainer2"));

		endorsement.setComments("TEST");
		endorsement.setMark(7);
		endorsement.setTrainer(trainer);

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to:
	 * Create an endorsement
	 * 
	 * B: Endorsement::comments is blank
	 * 
	 * C: 63% of sentence coverage -> It has covered 5 lines of 8.
	 * 
	 * D: 100% of data coverage because every fields are obligatory.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void create_endorsement_negative_test2() {
		Endorsement endorsement, saved;
		Trainer trainer;

		super.authenticate("customer1");

		endorsement = this.endorsementService.create();
		trainer = this.trainerService.findOne(super.getEntityId("trainer1"));

		endorsement.setComments("");
		endorsement.setMark(7);
		endorsement.setTrainer(trainer);

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to:
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

		super.authenticate("customer1");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement2"));

		endorsement.setComments("TEST");

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to:
	 * Update an endorsement
	 * 
	 * B: A customer tries to updates an endorsement that it has been written by a trainer
	 * 
	 * C: 25% of sentence coverage -> Its covered 2 lines of 8.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void save_endorsement_negative_test() {
		Endorsement endorsement, saved;

		super.authenticate("customer1");

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));

		endorsement.setComments("TEST");

		saved = this.endorsementService.save(endorsement);

		Assert.notNull(saved);

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * List and display the endorsements than others customers has written about the trainer he/she has attended
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentece coverage
	 * 
	 * D: Intentionally blank
	 */
	@Test
	public void list_display_endorsements_attended_trainers_positive_test() {
		List<Endorsement> endorsements = new ArrayList<>();
		Endorsement endorsement;

		super.authenticate("customer1");

		endorsements = (List<Endorsement>) this.endorsementService.findReceivedEndorsementsByTrainer(this.trainerService.findOne(super.getEntityId("trainer1")).getId());
		endorsement = endorsements.get(0);

		Assert.notNull(endorsements);
		Assert.notNull(endorsement);

		super.unauthenticate();
	}

	// Ancillary methods ------------------------------------------------------
}
