
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NutritionistRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Nutritionist;
import forms.RegistrationForm;

@Service
@Transactional
public class NutritionistService {

	// Managed repository --------------------------

	@Autowired
	private NutritionistRepository	nutritionistRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private Validator				validator;


	// Constructors -------------------------------

	public NutritionistService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Nutritionist create() {
		Nutritionist result;

		result = new Nutritionist();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.NUTRITIONIST));

		return result;
	}

	public Nutritionist findOne(final int nutritionistId) {
		Nutritionist result;

		result = this.nutritionistRepository.findOne(nutritionistId);
		Assert.notNull(result);

		return result;
	}

	public Nutritionist findOneToDisplayEdit(final int nutritionistId) {
		Assert.isTrue(nutritionistId != 0);

		Nutritionist result, principal;

		principal = this.findByPrincipal();
		result = this.nutritionistRepository.findOne(nutritionistId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == nutritionistId);

		return result;
	}

	public Nutritionist save(final Nutritionist nutritionist) {
		Nutritionist result;

		result = (Nutritionist) this.actorService.save(nutritionist);

		return result;
	}

	// Other business methods ---------------------

	public Nutritionist findByPrincipal() {
		Nutritionist result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Nutritionist findByUserAccount(final int userAccountId) {
		Nutritionist result;

		result = this.nutritionistRepository.findByUserAccount(userAccountId);

		return result;
	}

	public RegistrationForm createForm(final Nutritionist nutritionist) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(nutritionist.getName());
		registrationForm.setMiddleName(nutritionist.getMiddleName());
		registrationForm.setSurname(nutritionist.getSurname());
		registrationForm.setEmail(nutritionist.getEmail());
		registrationForm.setId(nutritionist.getId());
		registrationForm.setPhoto(nutritionist.getPhoto());
		registrationForm.setPhoneNumber(nutritionist.getPhoneNumber());
		registrationForm.setAddress(nutritionist.getAddress());
		registrationForm.setIsSuspicious(nutritionist.getIsSuspicious());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	public Nutritionist reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Nutritionist result, nutritionistStored;
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
			result = new Nutritionist();
			nutritionistStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setMiddleName(registrationForm.getMiddleName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSuspicious(nutritionistStored.getIsSuspicious());
			result.setId(nutritionistStored.getId());
			result.setVersion(nutritionistStored.getVersion());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(nutritionistStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = nutritionistStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = nutritionistStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = nutritionistStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}

	private void validateRegistration(final Nutritionist nutritionist, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(nutritionist.getEmail(), binding);
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
		if (this.actorService.existEmail(nutritionist.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

}
