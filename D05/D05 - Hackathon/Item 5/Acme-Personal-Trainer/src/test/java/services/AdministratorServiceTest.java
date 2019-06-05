
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Trainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private TrainerService			trainerService;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Create user accounts for new administrators
	 */
	@Test
	public void registerAdministratorDriver() {
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
				"admin1", "AdministratorTest", "AdministratorTest", "AdministratorTest", "http://www.google.com", "AdministratorTest@us.es", "695478452", "Address Test", null
			},
			/*
			 * B: Administrator::name is blank
			 * 
			 * C: Approximately 61% of sentence coverage, since it has been
			 * covered 11 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "", "AdministratorTest", "AdministratorTest", "http://www.google.com", "AdministratorTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::surname is blank
			 * 
			 * C: Approximately 61% of sentence coverage, since it has been
			 * covered 11 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTest", "AdministratorTest", "", "http://www.google.com", "AdministratorTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Administrator::email is blank
			 * 
			 * C: Approximately 33% of sentence coverage, since it has been
			 * covered 6 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AdministratorTest", "AdministratorTest", "AdministratorTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},
			/*
			 * B: Actor unauthenticated tries to register an administrator account
			 * 
			 * C: Approximately 11% of sentence coverage, since it has been
			 * covered 2 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				null, "AdministratorTest", "AdministratorTest", "AdministratorTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},
			/*
			 * B: A company tries to register an administrator
			 * 
			 * C: Approximately 17% of sentence coverage, since it has been
			 * covered 3 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"company1", "AdministratorTest", "AdministratorTest", "AdministratorTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerAdministratorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void registerAdministratorTemplate(final String username, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Administrator administrator, saved;
		UserAccount userAccount;
		Authority auth;

		super.startTransaction();

		caught = null;

		try {
			super.authenticate(username);

			auth = new Authority();
			auth.setAuthority("ADMIN");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			administrator = this.administratorService.create();
			administrator.setName(name);
			administrator.setMiddleName(middleName);
			administrator.setSurname(surname);
			administrator.setAddress(address);
			administrator.setEmail(email);
			administrator.setPhoneNumber(phoneNumber);
			administrator.setPhoto(photo);

			saved = this.administratorService.save(administrator);
			this.administratorService.flush();

			administrator = this.administratorService.findOne(saved.getId());
			Assert.isTrue(saved.equals(administrator));

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
		Administrator administrator, saved;
		String oldName;

		super.authenticate("admin1");

		this.startTransaction();

		administrator = this.administratorService.findOneToDisplayEdit(super.getEntityId("administrator1"));

		oldName = administrator.getName();

		administrator.setName("TEST");

		saved = this.administratorService.save(administrator);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated must be able to:
	 * Edit his/her personal data
	 * 
	 * B: administrator::name is blank
	 * 
	 * C: Approximately 61% of sentence coverage, since it has been
	 * covered 11 lines of code of 18 possible.
	 * 
	 * D: Approximately 60% of data coverage, because actors have a lot
	 * of attributes with several restrictions.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void save_negative_test() {
		Administrator administrator, saved;
		String oldName;

		super.authenticate("admin1");

		this.startTransaction();

		administrator = this.administratorService.findOneToDisplayEdit(super.getEntityId("administrator1"));

		oldName = administrator.getName();

		administrator.setName("");

		saved = this.administratorService.save(administrator);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Display a listing suspicious actors.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of data coverage
	 * 
	 * D: Intentionally blank
	 */
	@Test
	public void list_suspicious_actors() {
		List<Actor> actors;
		Actor suspicious;

		super.authenticate("admin1");

		actors = new ArrayList<>(this.actorService.findAll());
		suspicious = actors.get(0);
		suspicious.setIsSuspicious(true);

		Assert.notNull(suspicious);

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor who is considered suspicious
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 7 lines of code of 7 possible.
	 * 
	 * D: Intentionally blank
	 */
	@Test
	public void banActor_positive_test() {
		Customer customer;

		super.authenticate("admin1");

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		customer.setIsSuspicious(true);

		this.actorService.ban(customer);

		Assert.isTrue(customer.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor who is considered suspicious
	 * 
	 * B: Ban an actor who is not suspicious
	 * 
	 * C: Approximately 29% of sentence coverage, since it has been
	 * covered 2 lines of code of 7 possible.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void banActor_negative_test() {
		Customer customer;

		super.authenticate("admin1");

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		this.actorService.ban(customer);

		Assert.isTrue(customer.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor who is considered suspicious
	 * 
	 * B: Customer tries to ban another customer
	 * 
	 * C: Approximately 43% of sentence coverage, since it has been
	 * covered 3 lines of code of 7 possible.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void banActor_negative_test2() {
		Customer customer;

		super.authenticate("customer2");

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		customer.setIsSuspicious(true);

		this.actorService.ban(customer);

		Assert.isTrue(customer.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Unban an actor
	 * 
	 * B: Positive test
	 * 
	 * C: Approximately 100% of sentence coverage, since it has been
	 * covered 7 lines of code of 7 possible.
	 * 
	 * D: Intentionally blank
	 */
	@Test
	public void unbanActor_positive_test() {
		Customer customer;

		super.authenticate("admin1");

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		customer.setIsSuspicious(true);

		this.actorService.ban(customer);

		this.actorService.unBan(customer);

		Assert.isTrue(!customer.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor who is considered suspicious
	 * 
	 * B: Ban an actor who is not banned previously
	 * 
	 * C: Approximately 29% of sentence coverage, since it has been
	 * covered 2 lines of code of 7 possible.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void unbanActor_negative_test() {
		Customer customer;

		super.authenticate("admin1");

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		this.actorService.unBan(customer);

		Assert.isTrue(!customer.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Ban an actor who is considered suspicious
	 * 
	 * B: Customer tries to unban another customer
	 * 
	 * C: Approximately 43% of sentence coverage, since it has been
	 * covered 3 lines of code of 7 possible.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void unbanActor_negative_test2() {
		Customer customer;

		super.authenticate("admin1");

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		customer.setIsSuspicious(true);

		this.actorService.ban(customer);

		super.unauthenticate();

		super.authenticate("customer2");

		this.actorService.unBan(customer);

		Assert.isTrue(!customer.getUserAccount().getIsBanned());

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Launch a process that computes an internal score for every trainer. The process must flag the actors as suspicious when their polarity score is too negative.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: Intentionally blank
	 */
	@Test
	public void launch_score_process() {

		super.authenticate("admin1");

		this.trainerService.scoreProcess();

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Launch a process that computes an internal score for every trainer. The process must flag the actors as suspicious when their polarity score is too negative.
	 * 
	 * B: Customer tries to launch score process
	 * 
	 * C: 4% of sentence coverage -> It has covered 1 line of 25.
	 * 
	 * D: Intentionally blank
	 */
	@Test(expected = IllegalArgumentException.class)
	public void launch_score_process_negative_test() {

		super.authenticate("customer1");

		this.trainerService.scoreProcess();

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Display the internal score of the trainers
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage
	 * 
	 * D: Intentionally blank
	 */
	@Test
	public void display_score() {
		Trainer trainer;

		super.authenticate("admin1");

		trainer = this.trainerService.findOne(super.getEntityId("trainer1"));
		Assert.notNull(trainer);

		super.unauthenticate();
	}

}
