
package services;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private AuditorService	auditorService;


	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to:
	 * Create user accounts for a new audit
	 */
	@Test
	public void registerAuditorDriver() {
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
				"admin1", "AuditorTest", "AuditorTest", "AuditorTest", "http://www.google.com", "AuditorTest@us.es", "695478452", "Address Test", null
			},
			/*
			 * B: Auditor::name is blank
			 * 
			 * C: Approximately 61% of sentence coverage, since it has been
			 * covered 11 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "", "AuditorTest", "AuditorTest", "http://www.google.com", "AuditorTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::surname is blank
			 * 
			 * C: Approximately 61% of sentence coverage, since it has been
			 * covered 11 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AuditorTest", "AuditorTest", "", "http://www.google.com", "AuditorTest@us.es", "695478452", "Address Test", ConstraintViolationException.class
			},
			/*
			 * B: Auditor::email is blank
			 * 
			 * C: Approximately 33% of sentence coverage, since it has been
			 * covered 6 lines of code of 18 possible.
			 * 
			 * D: Approximately 60% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"admin1", "AuditorTest", "AuditorTest", "AuditorTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},
			/*
			 * B: Actor unauthenticated tries to register an Auditor account
			 * 
			 * C: Approximately 11% of sentence coverage, since it has been
			 * covered 2 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				null, "AuditorTest", "AuditorTest", "AuditorTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},
			/*
			 * B: A company tries to register an Auditor
			 * 
			 * C: Approximately 17% of sentence coverage, since it has been
			 * covered 3 lines of code of 18 possible.
			 * 
			 * D: Approximately 73% of data coverage, because actors have a lot
			 * of attributes with several restrictions.
			 */
			{
				"company1", "AuditorTest", "AuditorTest", "AuditorTest", "http://www.google.com", "", "695478452", "Address Test", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.registerAuditorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void registerAuditorTemplate(final String username, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber, final String address, final Class<?> expected) {
		Class<?> caught;
		Auditor auditor, saved;
		UserAccount userAccount;
		Authority auth;

		super.startTransaction();

		caught = null;

		try {
			super.authenticate(username);

			auth = new Authority();
			auth.setAuthority("AUDITOR");
			userAccount = new UserAccount();

			userAccount.setAuthorities(Arrays.asList(auth));
			userAccount.setUsername("testingUsername");
			userAccount.setPassword("testingPassword");

			auditor = this.auditorService.create();
			auditor.setName(name);
			auditor.setMiddleName(middleName);
			auditor.setSurname(surname);
			auditor.setAddress(address);
			auditor.setEmail(email);
			auditor.setPhoneNumber(phoneNumber);
			auditor.setPhoto(photo);

			saved = this.auditorService.save(auditor);
			this.auditorService.flush();

			auditor = this.auditorService.findOne(saved.getId());
			Assert.isTrue(saved.equals(auditor));

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
		Auditor auditor, saved;
		String oldName;

		super.authenticate("auditor1");

		this.startTransaction();

		auditor = this.auditorService.findOneToDisplayEdit(super.getEntityId("auditor1"));

		oldName = auditor.getName();

		auditor.setName("TEST");

		saved = this.auditorService.save(auditor);

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
		Auditor auditor, saved;
		String oldName;

		super.authenticate("auditor1");

		this.startTransaction();

		auditor = this.auditorService.findOneToDisplayEdit(super.getEntityId("auditor1"));

		oldName = auditor.getName();

		auditor.setName("");

		saved = this.auditorService.save(auditor);

		Assert.isTrue(!saved.getName().equals(oldName));

		super.rollbackTransaction();

		super.unauthenticate();

	}

}
