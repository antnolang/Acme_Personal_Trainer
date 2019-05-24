
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private FinderService	finderService;


	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an administrator must be able to
	 * display a dashboard with the following information:
	 * The ratio of empty versus non-empty finders.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void testRatioEmptyVsNonEmpty() {
		Double data;

		data = this.finderService.findRatioEmptyVsNonEmpty();

		Assert.isTrue(data == 0.16667);
	}

	//	/* TODO: Test funcionales y análisis Finder
	//	 * A: An actor who is authenticated as a trainer must be able to: Change
	//	 * the filters of his or her finder. 
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test()
	//	public void finderSearchTest() {
	//		Collection<Position> results;
	//		Finder finder, saved;
	//		int finderId;
	//
	//		super.authenticate("trainer9");
	//
	//		finderId = super.getEntityId("finder9");
	//
	//		finder = this.finderRepository.findOne(finderId);
	//		finder = this.cloneFinder(finder);
	//		
	//		// ELEGIR PARÁMETROS
	//		// finder.setKeyword("56");
	//		// finder.setMaximumDeadline(LocalDate.parse("2019-11-08").toDate());
	//		this.finderService.save(finder);
	//		saved = this.finderService.findByRookiePrincipal();
	//
	//		results = saved.getPositions();
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(results.size() == 2);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Change
	//	 * the filters of his or her finder. 
	//	 * 
	//	 * B: The finder can only be used by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void finderSearchNegativeTest() {
	//		Collection<Position> results;
	//		Finder finder, saved;
	//		int finderId;
	//
	//		super.authenticate("trainer9");
	//
	//		finderId = super.getEntityId("finder8");
	//
	//		finder = this.finderRepository.findOne(finderId);
	//		finder = this.cloneFinder(finder);
	//
	//		// ELEGIR PARÁMETROS
	//		// finder.setKeyword("56");
	//		// finder.setMaximumDeadline(LocalDate.parse("2019-11-08").toDate());
	//		this.finderService.save(finder);
	//		saved = this.finderService.findByRookiePrincipal();
	//
	//		results = saved.getPositions();
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(results.size() == 2);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Change
	//	 * the filters of his or her finder. 
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test()
	//	public void finderClearTest() {
	//		Finder finder;
	//		int finderId;
	//
	//		super.authenticate("trainer9");
	//
	//		finderId = super.getEntityId("finder9");
	//
	//		finder = this.finderRepository.findOne(finderId);
	//
	//		this.finderService.clear(finder);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(finder.getCategory().isEmpty());
	//		Assert.isTrue(finder.getKeyword().isEmpty());
	//		Assert.isTrue(finder.getEndDate() == null);
	//		Assert.isTrue(finder.getStartDate() == null);
	//		Assert.isTrue(finder.getStartPrice() == null);
	//		Assert.isTrue(finder.getEndPrice() == null);
	//		Assert.isTrue(finder.getWorkingOuts() == null);
	//		Assert.isTrue(finder.getUpdatedMoment().equals(new Date(Integer.MIN_VALUE)));
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Change
	//	 * the filters of his or her finder. 
	//	 * 
	//	 * B: The finder can only be cleared by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void finderClearNegativeTest() {
	//		Finder finder;
	//		int finderId;
	//
	//		super.authenticate("trainer9");
	//
	//		finderId = super.getEntityId("finder8");
	//
	//		finder = this.finderRepository.findOne(finderId);
	//
	//		this.finderService.clear(finder);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(finder.getCategory().isEmpty());
	//		Assert.isTrue(finder.getKeyword().isEmpty());
	//		Assert.isTrue(finder.getEndDate() == null);
	//		Assert.isTrue(finder.getStartDate() == null);
	//		Assert.isTrue(finder.getStartPrice() == null);
	//		Assert.isTrue(finder.getEndPrice() == null);
	//		Assert.isTrue(finder.getWorkingOuts() == null);
	//		Assert.isTrue(finder.getUpdatedMoment().equals(new Date(Integer.MIN_VALUE)));
	//	}

	// Ancillary methods ------------------------------------------------------

	private Finder cloneFinder(final Finder finder) {
		final Finder res = new Finder();

		res.setCategory(finder.getCategory());
		res.setCustomer(finder.getCustomer());
		res.setEndDate(finder.getEndDate());
		res.setEndPrice(finder.getEndPrice());
		res.setId(finder.getId());
		res.setKeyword(finder.getKeyword());
		res.setStartDate(finder.getStartDate());
		res.setStartPrice(finder.getStartPrice());
		res.setVersion(finder.getVersion());
		res.setWorkingOuts(finder.getWorkingOuts());

		return res;
	}
}
