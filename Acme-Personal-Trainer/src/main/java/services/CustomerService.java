
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed repository --------------------------

	@Autowired
	private CustomerRepository	customerRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	// Constructors -------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Customer create() {
		Customer result;

		result = new Customer();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.CUSTOMER));
		result.setIsPremium(false);

		return result;
	}

	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Customer findOneToDisplayEdit(final int customerId) {
		Assert.isTrue(customerId != 0);

		Customer result, principal;

		principal = this.findByPrincipal();
		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == customerId);

		return result;
	}

	public Customer save(final Customer customer) {
		Customer result;

		result = (Customer) this.actorService.save(customer);

		return result;
	}

	// Other business methods ---------------------

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Customer findByUserAccount(final int userAccountId) {
		Customer result;

		result = this.customerRepository.findByUserAccount(userAccountId);

		return result;
	}

}
