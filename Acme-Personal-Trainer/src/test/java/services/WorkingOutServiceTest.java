
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class WorkingOutServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

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
	//		Assert.isTrue(data[0] == 0.0);
	//		Assert.isTrue(data[1] == 1.0);
	//		Assert.isTrue(data[2] == 0.8889);
	//		Assert.isTrue(data[3] == 0.3143);
	//	}

	// Ancillary methods ------------------------------------------------------
}
