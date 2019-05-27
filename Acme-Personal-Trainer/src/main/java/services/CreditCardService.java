
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.Application;
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

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private Validator				validator;

	@Autowired
	private CustomisationService	customisationService;


	//Constructor ----------------------------------------------------
	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public CreditCard create() {
		CreditCard result;
		Customer customer;

		result = new CreditCard();
		customer = this.customerService.findByPrincipal();

		result.setCustomer(customer);

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() == 0);
		Assert.isTrue(!this.utilityService.checkIsExpired(creditCard), "Expired credit card");
		Assert.isTrue(this.customisationService.find().getCreditCardMakes().contains(creditCard.getBrandName()));
		this.checkByPrincipal(creditCard);

		final CreditCard result;

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));
		this.checkByPrincipal(creditCard);
		this.isDeletable(creditCard);

		this.creditCardRepository.delete(creditCard);
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public void deleteByPrincipal() {
		Customer customer;
		Collection<CreditCard> creditCards;

		customer = this.customerService.findByPrincipal();
		creditCards = this.creditCardRepository.findAllByCustomer(customer.getId());

		this.creditCardRepository.delete(creditCards);
	}

	// Other business methods ---------------------

	public List<CreditCard> findAllByCustomer() {
		List<CreditCard> results;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		results = new ArrayList<>(this.creditCardRepository.findAllByCustomer(principal.getId()));

		return results;
	}

	public CreditCard findOneByPrincipal(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		this.checkByPrincipal(result);
		Assert.notNull(result);

		return result;
	}

	// Private methods-----------------------------------------------
	private void checkByPrincipal(final CreditCard creditCard) {
		Customer owner;
		Customer principal;

		owner = creditCard.getCustomer();
		principal = this.customerService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}

	private void isDeletable(final CreditCard creditCard) {
		Collection<Application> applicationsWithCreditCard;

		applicationsWithCreditCard = this.applicationService.applicationsWithCreditCard(creditCard.getId());

		Assert.isTrue(applicationsWithCreditCard.isEmpty());

	}

	// Reconstruct ----------------------------------------------
	public CreditCard reconstruct(final CreditCard creditCard, final BindingResult binding) {
		CreditCard result;

		result = this.create();
		result.setBrandName(creditCard.getBrandName().trim());
		result.setCvvCode(creditCard.getCvvCode());
		result.setExpirationMonth(creditCard.getExpirationMonth().trim());
		result.setExpirationYear(creditCard.getExpirationYear().trim());
		result.setHolderName(creditCard.getHolderName().trim());
		result.setNumber(creditCard.getNumber().trim());

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.creditCardRepository.flush();

	}

}
