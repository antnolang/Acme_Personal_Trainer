
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
import domain.Administrator;

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

}
