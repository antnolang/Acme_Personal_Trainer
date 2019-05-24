
package services;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import domain.Customer;
import domain.Customisation;
import domain.Finder;
import domain.WorkingOut;

@Service
@Transactional
public class FinderService {

	// Managed repository ------------------------------------------------

	@Autowired
	private FinderRepository		finderRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private WorkingOutService		workingOutService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private Validator				validator;


	// Constructors ------------------------------------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	private Finder create() {
		Finder result;

		result = new Finder();
		result.setKeyword("");
		result.setUpdatedMoment(new Date(Integer.MIN_VALUE));
		result.setWorkingOuts(Collections.<WorkingOut> emptySet());

		return result;
	}

	public void save(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(this.validDates(finder));
		Assert.isTrue(this.validPrices(finder));
		this.checkOwner(finder);

		Finder saved;
		Pageable pageable;
		Customisation customisation;

		customisation = this.customisationService.find();
		pageable = new PageRequest(0, customisation.getNumberResults());

		saved = this.finderRepository.save(finder);
		this.workingOutService.searchWorkingOutFinder(saved, pageable);
	}

	// Other business methods --------------------------------------------

	public Finder evaluateSearch(final Finder finder) {
		Pageable pageable;
		Customisation customisation;

		customisation = this.customisationService.find();
		pageable = new PageRequest(0, customisation.getNumberResults());

		if (this.isFinderOutdated(finder.getUpdatedMoment(), customisation.getTimeResults()))
			this.workingOutService.searchWorkingOutFinder(finder, pageable);

		return finder;
	}

	public void clear(final Finder finder) {
		this.checkOwner(finder);

		finder.setKeyword("");
		finder.setCategory(null);
		finder.setStartPrice(null);
		finder.setEndPrice(null);
		finder.setStartDate(null);
		finder.setEndDate(null);
		finder.setUpdatedMoment(new Date(Integer.MIN_VALUE));
	}

	public Finder findByCustomerPrincipal() {
		Finder result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		result = this.finderRepository.findByCustomerId(customer.getId());
		Assert.notNull(result);

		return result;
	}

	public Double findRatioEmptyVsNonEmpty() {
		Double result;

		result = this.finderRepository.findRatioEmptyVsNonEmpty();

		return result;
	}

	// Ancillary methods -------------------------------------------------

	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder result, finderStored;

		result = new Finder();
		finderStored = this.finderRepository.findOne(finder.getId());

		result.setId(finder.getId());
		result.setKeyword(finder.getKeyword().trim());
		result.setCategory(finder.getCategory());
		result.setStartPrice(finder.getStartPrice());
		result.setEndPrice(finder.getEndPrice());
		result.setStartDate(finderStored.getStartDate());
		result.setEndDate(finderStored.getEndDate());
		result.setCustomer(finderStored.getCustomer());
		result.setWorkingOuts(finderStored.getWorkingOuts());
		result.setUpdatedMoment(finderStored.getUpdatedMoment());
		result.setVersion(finderStored.getVersion());

		this.validator.validate(result, binding);
		if (!this.validDates(finder))
			binding.rejectValue("startDate", "finder.dates.error", "End date cannot be earlier than Start date.");
		else if (!this.validPrices(finder))
			binding.rejectValue("startPrice", "finder.prices.error", "End price cannot be fewer than Start price.");

		return result;
	}

	private boolean validDates(final Finder finder) {
		boolean result;

		if (finder.getStartDate() != null && finder.getEndDate() != null)
			result = !finder.getEndDate().before(finder.getStartDate());
		else
			result = true;

		return result;
	}

	private boolean validPrices(final Finder finder) {
		boolean result;

		if (finder.getStartPrice() != null && finder.getEndPrice() != null)
			result = finder.getStartPrice() <= finder.getEndPrice();
		else
			result = true;

		return result;
	}

	private boolean isFinderOutdated(final Date updatedUpdate, final int timeCache) {
		Long diff, milisTimeCache;

		diff = this.utilityService.current_moment().getTime() - updatedUpdate.getTime();
		milisTimeCache = TimeUnit.HOURS.toMillis(timeCache);

		return diff >= milisTimeCache;
	}

	private void checkOwner(final Finder finder) {
		Customer principal;

		if (finder.getId() != 0) {
			principal = this.customerService.findByPrincipal();
			Assert.isTrue(finder.getCustomer().equals(principal));
		}
	}

	protected void assignNewFinder(final Customer customer) {
		Finder finder;

		finder = this.create();
		finder.setCustomer(customer);
		this.save(finder);
	}

	protected void deleteFinder(final Customer customer) {
		Finder finder;

		finder = this.finderRepository.findByCustomerId(customer.getId());
		Assert.notNull(finder);

		this.finderRepository.delete(finder);
	}

}
