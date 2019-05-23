
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Customer;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed repository ------------------------------------------------

	@Autowired
	private FinderRepository	finderRepository;


	// Other supporting services -----------------------------------------

	// Constructors ------------------------------------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	// Other business methods --------------------------------------------

	public Double findRatioEmptyVsNonEmpty() {
		Double result;

		result = this.finderRepository.findRatioEmptyVsNonEmpty();

		return result;
	}

	// Ancillary methods -------------------------------------------------

	protected void assignNewFinder(final Customer customer) {
		//		Finder finder;
		//
		//		finder = this.create();
		//		finder.setRookie(rookie);
		//		this.save(finder);
	}

	protected void deleteFinder(final Customer customer) {
		Finder finder;

		finder = this.finderRepository.findByCustomerId(customer.getId());
		Assert.notNull(finder);

		this.finderRepository.delete(finder);
	}

}
