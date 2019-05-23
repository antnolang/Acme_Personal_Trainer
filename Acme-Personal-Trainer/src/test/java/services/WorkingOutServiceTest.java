
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

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

	//	/* TODO: Test funcional query 11.4.1
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The average, the minimum, the maximum and the standard deviation of the
	//	 * number of working-outs per trainer.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testDataNumberWorkingOutPerTrainer() {
	//		Double[] data;
	//
	//		data = this.curriculumService.findDataNumberWorkingOutPerTrainer();
	//
	//		Assert.isTrue(data[0] == 1.28571);
	//		Assert.isTrue(data[1] == 0.0);
	//		Assert.isTrue(data[2] == 2.0);
	//		Assert.isTrue(data[3] == 0.69985);
	//	}

	// Ancillary methods ------------------------------------------------------
}
