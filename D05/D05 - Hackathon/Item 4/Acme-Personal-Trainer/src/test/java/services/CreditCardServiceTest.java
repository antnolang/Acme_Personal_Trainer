
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;


	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * Req 10.4 An actor who is authenticated as a customer must be able to manage
	 * their credit card, which includes listing,showing,creating and deleting them
	 */

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A:Req 10.4 Customer create creditCard
			 * C: Analysis of sentence coverage: 66/66 (100%) we test 66 of 66 total lines.
			 * D: Analysis of data coverage: 6/9 -> 66.6% of executed lines codes .
			 */
			{
				"customer1", "holderName", "VISA", "4929406212694094", "01", "22", 123, null
			},

			/*
			 * A:Req 10.4 Customer create creditCard
			 * B: Create a credit card with past expirated month-year
			 * C: Analysis of sentence coverage: 39/66 (59%) we test 39 of 66 total lines.
			 * D: Analysis of data coverage: 6/9 -> 66.6% of executed lines codes .
			 */
			{
				"customer1", "holderName", "VISA", "4929406212694094", "01", "18", 123, IllegalArgumentException.class
			},
			/*
			 * A:Req 10.4 Customer create creditCard
			 * B: Create a credit card with invalid number
			 * C:Analysis of sentence coverage: 65/66 (98.4%) we test 65 of 66 total lines.
			 * D: Analysis of data coverage: 6/9 -> 66.6% of executed lines codes .
			 */
			{
				"customer1", "holderName", "VISA", "", "01", "22", 123, ConstraintViolationException.class
			},
			/*
			 * A:Req 10.4 Customer create creditCard
			 * B: Create a credit card with invalid cvv
			 * C: Analysis of sentence coverage: 65/66 (98.4%) we test 65 of 66 total lines.
			 * D: Analysis of data coverage: 6/9 -> 66.6% of executed lines codes .
			 */
			{
				"customer1", "holderName", "VISA", "4929406212694094", "01", "22", 022, ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}

	protected void templateCreate(final String username, final String holderName, final String brandName, final String number, final String expirationMonth, final String expirationYear, final int cvvCode, final Class<?> expected) {
		Class<?> caught;
		final CreditCard creditCard;
		final CreditCard creditCardSaved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			creditCard = this.creditCardService.create();

			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setCvvCode(cvvCode);

			creditCardSaved = this.creditCardService.save(creditCard);

			this.creditCardService.flush();

			Assert.notNull(creditCardSaved);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}
	/*
	 * A: Req 10.4 Customer delete his/her creditCard
	 * C: Analysis of sentence coverage: 40/40 (100%) we test 40 of 40 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void delete_positive_test() {
		super.authenticate("customer1");

		int creditCardId;
		CreditCard creditCard;

		creditCardId = super.getEntityId("creditCard2");
		creditCard = this.creditCardService.findOne(creditCardId);

		this.creditCardService.delete(creditCard);

		super.unauthenticate();
	}
	/*
	 * A: Req 10.4 Customer delete his/her creditCard
	 * B: Delete creditCard to another user
	 * C: Analysis of sentence coverage: 7/40 (17.5%) we test 7 of 40 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative1_test() {
		super.authenticate("customer2");

		int creditCardId;
		CreditCard creditCard;

		creditCardId = super.getEntityId("creditCard1");
		creditCard = this.creditCardService.findOne(creditCardId);

		this.creditCardService.delete(creditCard);

		super.unauthenticate();
	}
	/*
	 * A: Req 10.4 Customer delete his/her creditCard
	 * B: Delete creditCard who is referencied to an application
	 * C: Analysis of sentence coverage: 38/40 (95%) we test 38 of 40 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative2_test() {
		super.authenticate("customer1");

		int creditCardId;
		CreditCard creditCard;

		creditCardId = super.getEntityId("creditCard1");
		creditCard = this.creditCardService.findOne(creditCardId);

		this.creditCardService.delete(creditCard);

		super.unauthenticate();
	}

	/*
	 * A: Req 10.4 Customer list their creditCard
	 * C: Analysis of sentence coverage: 29/29 (100%) we test 29 of 29 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void list_positive_test() {
		super.authenticate("customer1");

		Collection<CreditCard> creditCards;

		creditCards = this.creditCardService.findAllByCustomer();

		Assert.isTrue(creditCards.size() == 3);

		super.unauthenticate();
	}

	/*
	 * A: Req 10.4 Customer display their creditCard
	 * C: Analysis of sentence coverage: 29/29 (100%) we test 29 of 29 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void display_positive_test() {
		super.authenticate("customer1");

		int creditCardId;

		creditCardId = super.getEntityId("creditCard1");
		this.creditCardService.findOneByPrincipal(creditCardId);

		super.unauthenticate();
	}

	/*
	 * A: Req 10.4 Customer display their creditCard
	 * B: Customer display credit card to another user
	 * C: Analysis of sentence coverage: 26/29 (89.5%) we test 26 of 29 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_negative_test() {
		super.authenticate("customer2");

		int creditCardId;

		creditCardId = super.getEntityId("creditCard1");
		this.creditCardService.findOneByPrincipal(creditCardId);

		super.unauthenticate();
	}
}
