
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
public class EndorsementServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private EndorsementService	endorsementService;


	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The minimum, the maximum, the average and the standard deviation of the
	 * number of endorsements per trainers.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testDataNumberEndorsementPerTrainer() {
		Double[] data;

		data = this.endorsementService.findDataNumberEndorsementPerTrainer();

		Assert.isTrue(data[0] == 0.0);
		Assert.isTrue(data[1] == 2.0);
		Assert.isTrue(data[2] == 0.28571);
		Assert.isTrue(data[3] == 0.69985);
	}

	// Ancillary methods ------------------------------------------------------
}
