
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Administrator;
import forms.RegistrationForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository --------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Other supporting services -------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private UtilityService			utilityService;


	// Constructors -------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Administrator create() {
		Administrator result;

		result = new Administrator();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.ADMIN));

		return result;

	}

	public Administrator findOne(final int adminId) {
		Administrator result;

		result = this.administratorRepository.findOne(adminId);
		Assert.notNull(result);

		return result;
	}

	public Administrator findOneToDisplayEdit(final int actorId) {
		Assert.isTrue(actorId != 0);

		Administrator result, principal;

		principal = this.findByPrincipal();
		result = this.administratorRepository.findOne(actorId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == actorId);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result;
		Assert.isTrue(this.findByPrincipal() != null);
		Assert.isTrue(this.findAll().contains(this.findByPrincipal()));

		result = (Administrator) this.actorService.save(administrator);

		return result;
	}
	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(!administrator.getUserAccount().getUsername().equals("system"));
		Assert.isTrue(administrator.getId() != 0);
		Assert.isTrue(this.findByPrincipal().equals(administrator));

		this.actorService.delete(administrator);
	}

	private Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	// Other business methods ---------------------
	public Administrator findSystem() {
		Administrator result;

		result = this.administratorRepository.findSystem();

		return result;
	}

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Administrator findByUserAccount(final int userAccountId) {
		Administrator result;

		result = this.administratorRepository.findByUserAccount(userAccountId);

		return result;
	}

	public Administrator reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Administrator result, administratorStored;
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

			userAccount = result.getUserAccount();
			userAccount.setUsername(registrationForm.getUserAccount().getUsername());
			userAccount.setPassword(registrationForm.getUserAccount().getPassword());

			this.validateRegistration(result, registrationForm, binding);
		} else {
			result = new Administrator();
			administratorStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setMiddleName(registrationForm.getMiddleName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSuspicious(administratorStored.getIsSuspicious());
			result.setId(administratorStored.getId());
			result.setVersion(administratorStored.getVersion());

			this.utilityService.validateEmailAdministrator(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(administratorStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = administratorStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = administratorStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = administratorStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}
	private void validateRegistration(final Administrator administrator, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmailAdministrator(administrator.getEmail(), binding);
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
		if (this.actorService.existEmail(administrator.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

	public RegistrationForm createForm(final Administrator administrator) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(administrator.getName());
		registrationForm.setMiddleName(administrator.getMiddleName());
		registrationForm.setSurname(administrator.getSurname());
		registrationForm.setEmail(administrator.getEmail());
		registrationForm.setId(administrator.getId());
		registrationForm.setPhoto(administrator.getPhoto());
		registrationForm.setPhoneNumber(administrator.getPhoneNumber());
		registrationForm.setAddress(administrator.getAddress());
		registrationForm.setIsSuspicious(administrator.getIsSuspicious());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	protected void flush() {
		this.administratorRepository.flush();
	}

}
