
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
public class EndorsementServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	//	/* TODO: Test funcional query 37.5.1
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The minimum, the maximum, the average and the standard deviation of the
	//	 * number of endorsements per trainers.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testDataNumberEndorsementPerTrainer() {
	//		Double[] data;
	//
	//		data = this.curriculumService.findDataNumberEndorsementPerTrainer();
	//
	//		Assert.isTrue(data[0] == 0.0);
	//		Assert.isTrue(data[1] == 3.0);
	//		Assert.isTrue(data[2] == 0.42857);
	//		Assert.isTrue(data[3] == 1.04978);
	//	}

	// Ancillary methods ------------------------------------------------------
}
