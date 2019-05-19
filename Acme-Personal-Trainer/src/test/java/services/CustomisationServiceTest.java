
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customisation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomisationServiceTest extends AbstractTest {

	// Service under test ----------------------------------------
	@Autowired
	private CustomisationService	customisationService;


	// Supporting services --------------------------------------

	// Suite Tests ----------------------------------------------

	/*
	 * A: Requirement 13 (The system must be easy to customise at run time).
	 * B: Customisation is null.
	 * C: (1/5) 20.00% of executed lines codes .
	 * D: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_saveTest_uno() {
		super.authenticate("admin1");

		final Customisation customisation = null;
		Customisation saved;

		saved = this.customisationService.save(customisation);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 13 (The system must be easy to customise at run time).
	 * B: An object of Customisation is inserted in DB.
	 * C: (2/5) 40.00% of executed lines codes .
	 * D: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_saveTest_dos() {
		super.authenticate("admin1");

		Customisation customisation, saved;

		customisation = new Customisation();
		customisation.setName("Acme Testing");
		customisation.setBanner("https://tinyurl.com/logoTesting");
		customisation.setWelcomeMessageEn("Hello world!!");
		customisation.setWelcomeMessageEs("Hola mundo!!");
		customisation.setCountryCode("+34");
		customisation.setCreditCardMakes("VISA");
		customisation.setNegativeWords("negative");
		customisation.setPositiveWords("positive");
		customisation.setPremiumAmount(2.2);
		customisation.setPriorities("ALTO,BAJO");
		customisation.setThreshold(2.2);
		customisation.setTimeResults(5);
		customisation.setNumberResults(21);
		customisation.setVAT(21);
		customisation.setSpamWords("vende,compra,sex");

		saved = this.customisationService.save(customisation);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 13 (The system must be easy to customise at run time)
			 * B: Invalid data in Customisation::name.
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Customisation::name is null => 1/41 -> 2.43%.
			 */
			{
				null, "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+43", 20, 50, "gilipollas,tonto,subnormal", 449.99, 21, ConstraintViolationException.class
			},

			/*
			 * A: Requirement 13 (The system must be easy to customise at run time)
			 * B: Customisation::nmberResults out of range: 101
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: => 1/41 -> 2.43%.
			 */
			{
				"Acme Test", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", "VISA", "negative", "positive", 20, "ALTO,BAJO", 22.1, 10, 101, 12, "spam", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 13 (The system must be easy to customise at run time)
			 * B: Customisation::spamWords is null
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: => 1/41 -> 2.43%.
			 */
			{
				"Acme Test", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", "VISA", "negative", "positive", 20, "ALTO,BAJO", 22.1, 10, 10, 12, null, ConstraintViolationException.class
			},
			/*
			 * A: Requirement 13 (The system must be easy to customise at run time)
			 * B: Customisation::spamWords is a empty string
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: => 1/41 -> 2.43%.
			 */
			{
				"Acme Test", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", "VISA", "negative", "positive", 20, "ALTO,BAJO", 22.1, 10, 10, 12, "", ConstraintViolationException.class
			},

			/*
			 * A: Requirement 13 (The system must be easy to customise at run time)
			 * B: Customisation::VATtax out of range
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: 1/41 -> 2.43%.
			 */
			{
				"Acme Test", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", "VISA", "negative", "positive", 20, "ALTO,BAJO", 22.1, 10, 10, 120, "spam", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 13 (The system must be easy to customise at run time)
			 * C: Analysis of sentence coverage: 4/5 -> 80.00% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 1/41 -> 2.43%.
			 */
			{
				"Acme Test", "https://i.imgur.com/7b8lu4b.png", "Hello world!!", "Hola mundo!!", "+23", "VISA", "negative", "positive", 20, "ALTO,BAJO", 22.1, 10, 10, 21, "gilipollas,tonto,subnormal", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(int) testingData[i][8], (String) testingData[i][9], (double) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (int) testingData[i][13], (String) testingData[i][14], (Class<?>) testingData[i][15]);

	}

	protected void templateEdit(final String name, final String banner, final String welcomeMessageEn, final String welcomeMessageEs, final String countryCode, final String creditCardMakes, final String negativeWords, final String positiveWords,
		final int premiumAmount, final String priorities, final double threshold, final int timeResults, final int numberResults, final int VAT, final String spamWords, final Class<?> expected) {
		Class<?> caught;
		Customisation customisation, saved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("admin1");

			customisation = this.customisationService.find();
			customisation.setName(name);
			customisation.setBanner(banner);
			customisation.setWelcomeMessageEn(welcomeMessageEn);
			customisation.setWelcomeMessageEs(welcomeMessageEs);
			customisation.setCountryCode(countryCode);
			customisation.setCreditCardMakes(creditCardMakes);
			customisation.setNegativeWords(negativeWords);
			customisation.setPositiveWords(positiveWords);
			customisation.setPremiumAmount(premiumAmount);
			customisation.setPriorities(priorities);
			customisation.setThreshold(threshold);
			customisation.setTimeResults(timeResults);
			customisation.setNumberResults(numberResults);
			customisation.setVAT(VAT);
			customisation.setSpamWords(spamWords);

			saved = this.customisationService.save(customisation);
			this.customisationService.flush();

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
