
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

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;

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


	// Tests ------------------------------------------------------------------

	//	/* TODO: Test funcional query 11.4.8
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The listing of customers who have got accepted at least 10% more 
	//	 * applications than the average, ordered by name.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testFindUsualCustomers() {
	//		Collection<Customer> customers;
	//		Customer c1;
	//		int c1Id, numberCustomers;
	//
	//		customers = this.curriculumService.findUsualCustomers();
	//		c1Id = super.getEntityId("customer1");
	//		c1 = this.customerRepository.findOne(c1Id);
	//		numberCustomers = 1;
	//
	//		Assert.isTrue(customers.contains(c1));
	//		Assert.isTrue(customers.size() == numberCustomers);
	//	}

	//	/* TODO: Test funcional query 37.5.3
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The top-three customers that write more endorsement.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testCustomerWriteMostEndorsement() {
	//		Collection<Customer> customers;
	//		Customer c1;
	//		int c1Id;
	//
	//		customers = this.curriculumService.findCustomerWriteMostEndorsement();
	//		c1Id = super.getEntityId("customer1");
	//		c1 = this.customerRepository.findOne(c1Id);
	//
	//		Assert.isTrue(customers.contains(c1));
	//		Assert.isTrue(customers.size() == 1);
	//	}

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
	public void save_negative_test() {
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

	// Ancillary methods ------------------------------------------------------
}
