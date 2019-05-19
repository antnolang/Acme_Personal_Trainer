
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Customer;

@Service
@Transactional
public class CreditCardService {

	// Managed repository ---------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private CustomerService			customerService;


	//Constructor ----------------------------------------------------

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	// Reconstruct ----------------------------------------------

	// Other business methods ---------------------

	public List<CreditCard> findAllByCustomer() {
		List<CreditCard> results;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		results = new ArrayList<>(this.creditCardRepository.findAllByCustomer(principal.getId()));

		return results;
	}

}
