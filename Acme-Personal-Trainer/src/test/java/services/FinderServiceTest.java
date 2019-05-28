
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import utilities.AbstractTest;
import domain.Finder;
import domain.WorkingOut;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private FinderService		finderService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private FinderRepository	finderRepository;


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

		Assert.isTrue(data == 0.16666666666666666);
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to: Change
	 * the filters of his or her finder.
	 * 
	 * B: Positive test
	 * 
	 * C: 90% of sentence coverage, since it has been covered
	 * 36 lines of code of 40 possible.
	 * 
	 * D: Approximately 23% of data coverage, since it has been used 6
	 * values in the data of 26 different possible values.
	 */
	@Test()
	public void finderSearchTest() {
		Collection<WorkingOut> results;
		Finder finder, saved;
		int finderId;

		super.authenticate("customer1");

		finderId = super.getEntityId("finder1");

		finder = this.finderRepository.findOne(finderId);
		finder = this.cloneFinder(finder);

		finder.setKeyword("14");
		finder.setStartDate(LocalDate.parse("2020-01-01").toDate());
		this.finderService.save(finder);
		this.finderRepository.flush();
		saved = this.finderService.findByCustomerPrincipal();

		results = saved.getWorkingOuts();

		super.unauthenticate();

		Assert.isTrue(results.size() == 2);
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to: Change
	 * the filters of his or her finder.
	 * 
	 * B: The finder can only be used by its owner.
	 * 
	 * C: 50% of sentence coverage, since it has been covered
	 * 20 lines of code of 40 possible.
	 * 
	 * D: Approximately 23% of data coverage, since it has been used 6
	 * values in the data of 26 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void finderSearchNegativeTest() {
		Collection<WorkingOut> results;
		Finder finder, saved;
		int finderId;

		super.authenticate("customer1");

		finderId = super.getEntityId("finder2");

		finder = this.finderRepository.findOne(finderId);
		finder = this.cloneFinder(finder);

		finder.setKeyword("14");
		finder.setStartDate(LocalDate.parse("2020-01-01").toDate());
		this.finderService.save(finder);
		this.finderRepository.flush();
		saved = this.finderService.findByCustomerPrincipal();

		results = saved.getWorkingOuts();

		super.unauthenticate();

		Assert.isTrue(results.size() == 2);
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to: Change
	 * the filters of his or her finder.
	 * 
	 * B: Start date must be earlier than End date
	 * 
	 * C: 12.5% of sentence coverage, since it has been covered
	 * 5 lines of code of 40 possible.
	 * 
	 * D: Approximately 23% of data coverage, since it has been used 6
	 * values in the data of 26 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void finderSearchNegativeTest2() {
		Finder finder;
		int finderId;

		super.authenticate("customer1");

		finderId = super.getEntityId("finder1");

		finder = this.finderRepository.findOne(finderId);
		finder = this.cloneFinder(finder);

		finder.setKeyword("14");
		finder.setStartDate(LocalDate.parse("2020-01-01").toDate());
		finder.setEndDate(LocalDate.parse("2018-01-01").toDate());
		this.finderService.save(finder);
		this.finderRepository.flush();
		this.finderService.findByCustomerPrincipal();

		super.unauthenticate();
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to: Change
	 * the filters of his or her finder.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 18 lines of code of 18 possible.
	 * 
	 * D: Approximately 23% of data coverage, since it has been used 6
	 * values in the data of 26 different possible values.
	 */
	@Test()
	public void finderClearTest() {
		Finder finder;
		int finderId;

		super.authenticate("customer1");

		finderId = super.getEntityId("finder1");

		finder = this.finderRepository.findOne(finderId);

		this.finderService.clear(finder);
		this.finderRepository.flush();

		super.unauthenticate();

		Assert.isTrue(finder.getCategory() == null);
		Assert.isTrue(finder.getKeyword().isEmpty());
		Assert.isTrue(finder.getEndDate() == null);
		Assert.isTrue(finder.getStartDate() == null);
		Assert.isTrue(finder.getStartPrice() == null);
		Assert.isTrue(finder.getEndPrice() == null);
		Assert.isTrue(finder.getUpdatedMoment().equals(new Date(Integer.MIN_VALUE)));
	}

	/*
	 * A: An actor who is authenticated as a customer must be able to: Change
	 * the filters of his or her finder.
	 * 
	 * B: The finder can only be cleared by its owner.
	 * 
	 * C: 61.1% of sentence coverage, since it has been covered
	 * 11 lines of code of 18 possible.
	 * 
	 * D: Approximately 23% of data coverage, since it has been used 6
	 * values in the data of 26 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void finderClearNegativeTest() {
		Finder finder;
		int finderId;

		super.authenticate("customer1");

		finderId = super.getEntityId("finder2");

		finder = this.finderRepository.findOne(finderId);

		this.finderService.clear(finder);
		this.finderRepository.flush();

		super.unauthenticate();

		Assert.isTrue(finder.getCategory() == null);
		Assert.isTrue(finder.getKeyword().isEmpty());
		Assert.isTrue(finder.getEndDate() == null);
		Assert.isTrue(finder.getStartDate() == null);
		Assert.isTrue(finder.getStartPrice() == null);
		Assert.isTrue(finder.getEndPrice() == null);
		Assert.isTrue(finder.getUpdatedMoment().equals(new Date(Integer.MIN_VALUE)));
	}

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
