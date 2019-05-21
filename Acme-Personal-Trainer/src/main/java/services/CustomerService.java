
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;
import forms.RegistrationForm;

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

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private Validator			validator;


	// Constructors -------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Customer create() {
		Customer result;

		result = new Customer();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.CUSTOMER));

		return result;
	}

	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	protected Collection<Customer> findAll() {
		Collection<Customer> results;

		results = this.customerRepository.findAll();

		return results;
	}

	protected Collection<Customer> findPremiumCustomers() {
		Collection<Customer> results;

		results = this.customerRepository.findPremiumCustomers();

		return results;
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

	public Collection<Customer> findCustomersWithAcceptedApplicationsByTrainer(final int trainerId) {
		Collection<Customer> result;

		result = this.customerRepository.findCustomersWithAcceptedApplicationsByTrainer(trainerId);

		return result;
	}

	public RegistrationForm createForm(final Customer customer) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(customer.getName());
		registrationForm.setMiddleName(customer.getMiddleName());
		registrationForm.setSurname(customer.getSurname());
		registrationForm.setEmail(customer.getEmail());
		registrationForm.setId(customer.getId());
		registrationForm.setPhoto(customer.getPhoto());
		registrationForm.setPhoneNumber(customer.getPhoneNumber());
		registrationForm.setAddress(customer.getAddress());
		registrationForm.setIsSuspicious(customer.getIsSuspicious());
		registrationForm.setIsPremium(customer.getIsSuspicious());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	public Customer reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Customer result, customerStored;
		UserAccount userAccount;

		if (registrationForm.getId() == 0) {
			result = this.create();

			result.setName(registrationForm.getName());
			result.setMiddleName(registrationForm.getMiddleName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSuspicious(false);
			result.setIsPremium(false);

			userAccount = result.getUserAccount();
			userAccount.setUsername(registrationForm.getUserAccount().getUsername());
			userAccount.setPassword(registrationForm.getUserAccount().getPassword());

			this.validateRegistration(result, registrationForm, binding);
		} else {
			result = new Customer();
			customerStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setMiddleName(registrationForm.getMiddleName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSuspicious(customerStored.getIsSuspicious());
			result.setIsPremium(customerStored.getIsPremium());
			result.setId(customerStored.getId());
			result.setVersion(customerStored.getVersion());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(customerStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = customerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = customerStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = customerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}

	private void validateRegistration(final Customer customer, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(customer.getEmail(), binding);
		if (username.trim().equals(""))
			binding.rejectValue("userAccount.username", "actor.username.blank", "Must entry a username.");
		if (password.trim().equals("") && confirmPassword.trim().equals("")) {
			binding.rejectValue("userAccount.password", "password.empty", "Must entry a password");
			binding.rejectValue("userAccount.confirmPassword", "confirmPassword.empty", "Must entry a confirm password");
		}
		if (!password.equals(confirmPassword))
			binding.rejectValue("userAccount.confirmPassword", "user.missmatch.password", "Does not match with password");
		if (checkBox == false)
			binding.rejectValue("checkBoxAccepted", "actor.checkBox.agree", "Must agree terms and conditions");
		if (checkBoxData == false)
			binding.rejectValue("checkBoxDataProcessesAccepted", "actor.checkBoxData.agree", "Must agree data processes");
		if (this.userAccountService.existUsername(username))
			binding.rejectValue("userAccount.username", "actor.username.used", "Username already in use");
		if (this.actorService.existEmail(customer.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

	protected void flush() {
		this.customerRepository.flush();
	}

}
