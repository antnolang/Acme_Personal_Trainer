
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TrainerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Trainer;
import forms.RegistrationForm;

@Service
@Transactional
public class TrainerService {

	// Managed repository --------------------------

	@Autowired
	private TrainerRepository	trainerRepository;

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

	public TrainerService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Trainer create() {
		Trainer result;

		result = new Trainer();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.TRAINER));

		return result;
	}

	public Trainer findOne(final int trainerId) {
		Trainer result;

		result = this.trainerRepository.findOne(trainerId);
		Assert.notNull(result);

		return result;
	}

	public Trainer findOneToDisplayEdit(final int trainerId) {
		Assert.isTrue(trainerId != 0);

		Trainer result, principal;

		principal = this.findByPrincipal();
		result = this.trainerRepository.findOne(trainerId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == trainerId);

		return result;
	}

	public Trainer save(final Trainer trainer) {
		Trainer result;

		result = (Trainer) this.actorService.save(trainer);

		return result;
	}

	// Other business methods ---------------------

	public Trainer findByPrincipal() {
		Trainer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Trainer findByUserAccount(final int userAccountId) {
		Trainer result;

		result = this.trainerRepository.findByUserAccount(userAccountId);

		return result;
	}

	public RegistrationForm createForm(final Trainer trainer) {
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();

		registrationForm.setName(trainer.getName());
		registrationForm.setMiddleName(trainer.getMiddleName());
		registrationForm.setSurname(trainer.getSurname());
		registrationForm.setEmail(trainer.getEmail());
		registrationForm.setId(trainer.getId());
		registrationForm.setPhoto(trainer.getPhoto());
		registrationForm.setPhoneNumber(trainer.getPhoneNumber());
		registrationForm.setAddress(trainer.getAddress());
		registrationForm.setIsSuspicious(trainer.getIsSuspicious());
		registrationForm.setCheckBoxAccepted(false);
		registrationForm.setCheckBoxDataProcessesAccepted(false);

		return registrationForm;
	}

	public Trainer reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Trainer result, trainerStored;
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
			result.setMark(registrationForm.getMark());
			result.setScore(registrationForm.getScore());

			userAccount = result.getUserAccount();
			userAccount.setUsername(registrationForm.getUserAccount().getUsername());
			userAccount.setPassword(registrationForm.getUserAccount().getPassword());

			this.validateRegistration(result, registrationForm, binding);
		} else {
			result = new Trainer();
			trainerStored = this.findOneToDisplayEdit(registrationForm.getId());

			result.setName(registrationForm.getName());
			result.setMiddleName(registrationForm.getMiddleName());
			result.setSurname(registrationForm.getSurname());
			result.setEmail(registrationForm.getEmail());
			result.setPhoneNumber(registrationForm.getPhoneNumber());
			result.setPhoto(registrationForm.getPhoto());
			result.setAddress(registrationForm.getAddress());
			result.setIsSuspicious(trainerStored.getIsSuspicious());
			result.setMark(trainerStored.getMark());
			result.setScore(trainerStored.getScore());
			result.setId(trainerStored.getId());
			result.setVersion(trainerStored.getVersion());

			this.utilityService.validateEmail(registrationForm.getEmail(), binding);

			if (registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) // No ha actualizado ningun atributo de user account
				result.setUserAccount(trainerStored.getUserAccount());
			else if (!registrationForm.getUserAccount().getUsername().isEmpty() && registrationForm.getUserAccount().getPassword().isEmpty() && registrationForm.getUserAccount().getConfirmPassword().isEmpty()) {// Modifica el username
				this.utilityService.validateUsernameEdition(registrationForm.getUserAccount().getUsername(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = trainerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					result.setUserAccount(userAccount);
				}
			} else if (registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica la password
				this.utilityService.validatePasswordEdition(registrationForm.getUserAccount().getPassword(), registrationForm.getUserAccount().getConfirmPassword(), binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = trainerStored.getUserAccount();
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			} else if (!registrationForm.getUserAccount().getUsername().isEmpty() && !registrationForm.getUserAccount().getPassword().isEmpty() && !registrationForm.getUserAccount().getConfirmPassword().isEmpty()) { // Modifica el username y la password
				this.utilityService.validateUsernamePasswordEdition(registrationForm, binding);
				if (binding.hasErrors()) {

				} else {
					userAccount = trainerStored.getUserAccount();
					userAccount.setUsername(registrationForm.getUserAccount().getUsername());
					userAccount.setPassword(registrationForm.getUserAccount().getPassword());
					result.setUserAccount(userAccount);
				}
			}

		}
		this.validator.validate(result, binding);

		return result;
	}

	private void validateRegistration(final Trainer trainer, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword, username;
		boolean checkBox, checkBoxData;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getUserAccount().getConfirmPassword();
		username = registrationForm.getUserAccount().getUsername();
		checkBox = registrationForm.getCheckBoxAccepted();
		checkBoxData = registrationForm.getCheckBoxDataProcessesAccepted();

		this.utilityService.validateEmail(trainer.getEmail(), binding);
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
		if (this.actorService.existEmail(trainer.getEmail()))
			binding.rejectValue("email", "actor.email.used", "Email already in use");

	}

}
