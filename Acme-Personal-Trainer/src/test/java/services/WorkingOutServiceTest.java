
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
import domain.WorkingOut;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class WorkingOutServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------
	@Autowired
	private WorkingOutService	workingOutService;


	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * A: Requirement 4.2: The average, the minimum, the maximum, and the
	 * standard deviation of the number of applications per working-out.
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findDataNumberApplicationPerWorkingOut_positiveTest() {
		Double[] data;

		data = this.workingOutService.findDataNumberApplicationPerWorkingOut();

		Assert.isTrue(data[0] == 0.44444);
		Assert.isTrue(data[1] == 0.0);
		Assert.isTrue(data[2] == 1.0);
		Assert.isTrue(data[3] == 0.4969);
	}

	/*
	 * A: Requirement 4.3: The average, the minimum, the maximum, and the
	 * standard deviation of the number of applications per working-out.
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findDataPricePerWorkingOut_positiveTest() {
		Double[] data;

		data = this.workingOutService.findDataPricePerWorkingOut();

		Assert.isTrue(data[0] == 32.656666666666666);
		Assert.isTrue(data[1] == 15.99);
		Assert.isTrue(data[2] == 75.99);
		Assert.isTrue(data[3] == 20.94967514996089);
	}

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The average, the minimum, the maximum and the standard deviation of the
	 * number of working-outs per trainer.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testDataNumberWorkingOutPerTrainer() {
		Double[] data;

		data = this.workingOutService.findDataNumberWorkingOutPerTrainer();

		Assert.isTrue(data[0] == 1.28571);
		Assert.isTrue(data[1] == 0.0);
		Assert.isTrue(data[2] == 2.0);
		Assert.isTrue(data[3] == 0.69985);
	}

	/*
	 * Req 9.1 An actor who is authenticated as a trainer must be able to manage
	 * their workingOuts, which includes listing,showing,creating, updating and deleting them
	 */

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A:Req 9.1 Trainer create workingOut
			 * C: Analysis of sentence coverage: 89/89 (100%) we test 89 of 89 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer1", "description", 22.2, null
			},
			/*
			 * A:Req 9.1 Trainer create workingOut
			 * B: Empty description
			 * C: Analysis of sentence coverage: 88/89 (98.8%) we test 88 of 89 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer1", "", 22.2, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1 Trainer create workingOut
			 * B : Negative price
			 * C: Analysis of sentence coverage: 88/89 (98.8%) we test 88 of 89 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer1", "description", -3.2, ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateCreate(final String username, final String description, final Double price, final Class<?> expected) {
		Class<?> caught;
		final WorkingOut workingOut;
		final WorkingOut workingOutSaved;
		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			workingOut = this.workingOutService.create();

			workingOut.setDescription(description);
			workingOut.setPrice(price);

			workingOutSaved = this.workingOutService.save(workingOut);

			this.workingOutService.flush();

			Assert.notNull(workingOutSaved);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
			/*
			 * A:Req 9.1 Trainer edit his/her workingOut
			 * C: Analysis of sentence coverage: 60/60 (100%) we test 60 of 60 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer4", "workingOut6", "description", 22.2, null
			},
			/*
			 * A:Req 9.1 Trainer edit his/her workingOut
			 * B: Empty description
			 * C: Analysis of sentence coverage: 59/60 (98.3%) we test 59 of 60 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer4", "workingOut6", "", 22.2, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1Trainer edit his/her workingOut
			 * B : Negative price
			 * C: Analysis of sentence coverage: 59/60 (98.3%) we test 59 of 60 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer4", "workingOut6", "description", -3.2, ConstraintViolationException.class
			},
			/*
			 * A:Req 9.1Trainer edit his/her workingOut
			 * B : Trainer edit workingOut to another user
			 * C: Analysis of sentence coverage: 30/60 (50%) we test 30 of 60 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer1", "workingOut6", "description", 22.2, IllegalArgumentException.class
			},

			/*
			 * A:Req 9.1Trainer edit his/her workingOut
			 * B : Edit workingOut in final mode
			 * C: Analysis of sentence coverage: 3/60 (5%) we test 3 of 60 total lines.
			 * D: Analysis of data coverage: 4/4 -> 100% of executed lines codes .
			 */
			{
				"trainer1", "workingOut1", "description", 22.2, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Double) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	protected void templateEdit(final String username, final int workingOutId, final String description, final Double price, final Class<?> expected) {
		Class<?> caught;
		final WorkingOut workingOut;
		final WorkingOut workingOutSaved;
		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);

			workingOut = this.workingOutService.findOneToEditDelete(workingOutId);

			workingOut.setDescription(description);
			workingOut.setPrice(price);

			workingOutSaved = this.workingOutService.save(workingOut);

			this.workingOutService.flush();

			Assert.notNull(workingOutSaved);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Req 9.1 Trainer delete his/her workingOut
	 * C: Analysis of sentence coverage: 58/58 (100%) we test 58 of 58 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void delete_positive_test() {
		super.authenticate("trainer4");

		int workingOutId;
		WorkingOut workingOut;

		workingOutId = super.getEntityId("workingOut6");
		workingOut = this.workingOutService.findOneToEditDelete(workingOutId);

		this.workingOutService.delete(workingOut);

		super.unauthenticate();
	}

	/*
	 * A: Req 9.1 Trainer delete his/her workingOut
	 * B: Delete workingOut in final mode
	 * C: Analysis of sentence coverage: 3/58 (5%) we test 3 of 58 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative1_test() {
		super.authenticate("trainer1");

		int workingOutId;
		WorkingOut workingOut;

		workingOutId = super.getEntityId("workingOut1");
		workingOut = this.workingOutService.findOneToEditDelete(workingOutId);

		this.workingOutService.delete(workingOut);

		super.unauthenticate();
	}
	/*
	 * A: Req 9.1 Trainer delete his/her workingOut
	 * B: Delete workingOut to another user
	 * C: Analysis of sentence coverage: 28/58 (47.2%) we test 28 of 58 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void delete_negative2_test() {
		super.authenticate("trainer1");

		int workingOutId;
		WorkingOut workingOut;

		workingOutId = super.getEntityId("workingOut6");
		workingOut = this.workingOutService.findOneToEditDelete(workingOutId);

		this.workingOutService.delete(workingOut);

		super.unauthenticate();
	}

	/*
	 * A: Req 9.1 Trainer list their workingouts
	 * C: Analysis of sentence coverage: 29/29 (100%) we test 29 of 29 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void list_positive_test() {
		super.authenticate("trainer1");

		Collection<WorkingOut> workingOuts;

		workingOuts = this.workingOutService.findWorkingOutsByPrincipal();

		Assert.isTrue(workingOuts.size() == 2);

		super.unauthenticate();
	}

	/*
	 * A: Req 9.1 Trainer display their workingOut
	 * C: Analysis of sentence coverage: 29/29 (100%) we test 29 of 29 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void display_positive_test() {
		super.authenticate("trainer1");

		int workingOutId;

		workingOutId = super.getEntityId("workingOut1");
		this.workingOutService.findOneToPrincipal(workingOutId);

		super.unauthenticate();
	}
	/*
	 * A: Req 9.1 Trainer display their workingOut
	 * B: Display workingout to another user
	 * C: Analysis of sentence coverage: 27/29 (93%) we test 27 of 29 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_negative_test() {
		super.authenticate("trainer2");

		int workingOutId;

		workingOutId = super.getEntityId("workingOut1");
		this.workingOutService.findOneToPrincipal(workingOutId);

		super.unauthenticate();
	}

	/*
	 * A: Req 10.1 Customer display workingOut
	 * C: Analysis of sentence coverage: 4/4 (100%) we test 3 of 4 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test
	public void displayCustomer_positive_test() {
		super.authenticate("customer1");

		int workingOutId;

		workingOutId = super.getEntityId("workingOut1");
		this.workingOutService.findOneToDisplay(workingOutId);

		super.unauthenticate();
	}
	/*
	 * A: Req 10.1 Customer display workingOut
	 * B: Display workingout in draft mode
	 * C: Analysis of sentence coverage: 3/4 (75%) we test 3 of 4 total lines.
	 * D:intentionally blank.there's nothing to check
	 */
	@Test(expected = IllegalArgumentException.class)
	public void displayCustomer_negative_test() {
		super.authenticate("customer1");

		int workingOutId;

		workingOutId = super.getEntityId("workingOut6");
		this.workingOutService.findOneToDisplay(workingOutId);

		super.unauthenticate();
	}

}
