
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

import repositories.TrainerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Administrator;
import domain.Endorsement;
import domain.Trainer;
import forms.RegistrationForm;

@Service
@Transactional
public class TrainerService {

	// Managed repository --------------------------

	@Autowired
	private TrainerRepository		trainerRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private Validator				validator;

	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private WorkingOutService		workingOutService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ApplicationService		applicationService;


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

	private Collection<Trainer> findAll() {
		Collection<Trainer> result;

		result = this.trainerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Trainer trainer) {
		Assert.notNull(trainer);
		Assert.isTrue(trainer.getId() != 0);

		// Delete applications of his/her working outs
		this.applicationService.deleteApplicationByTrainer(trainer);

		// Delete working outs
		this.workingOutService.deleteByPrincipal();

		//Delete curriculums
		this.curriculumService.deleteCurriculums(trainer);

		// Delete endorsements
		this.endorsementService.deleteEndorsements(trainer);

		this.actorService.delete(trainer);

	}

	// Other business methods ---------------------

	public Collection<Trainer> findAllNotBanned() {
		Collection<Trainer> result;

		result = this.trainerRepository.findAllNotBanned();
		Assert.notNull(result);

		return result;
	}

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

	public Collection<Trainer> findTrainersWithAcceptedApplicationsByCustomer(final int customerId) {
		Collection<Trainer> result;

		result = this.trainerRepository.findTrainersWithAcceptedApplicationsByCustomer(customerId);

		return result;
	}

	public void scoreProcess() {
		Assert.isTrue(this.actorService.findPrincipal() instanceof Administrator);
		Collection<Trainer> all;

		all = this.findAll();

		for (final Trainer t : all)
			this.launchScoreProcess(t);
	}

	private void launchScoreProcess(final Trainer trainer) {
		Assert.notNull(trainer);
		Assert.isTrue(trainer.getId() != 0);

		final Double score;
		final Integer p, n;
		final Double maximo;
		final List<Integer> ls;
		Collection<Endorsement> endorsementsReceived;

		endorsementsReceived = this.endorsementService.findReceivedEndorsementsByTrainer(trainer.getId());

		ls = new ArrayList<>(this.positiveNegativeWordNumbers(endorsementsReceived));
		p = ls.get(0);
		n = ls.get(1);

		maximo = this.max(p, n);

		if (maximo != 0)
			score = (p - n) / maximo;
		else
			score = 0.0;

		Assert.isTrue(score >= -1.00 && score <= 1.00);

		trainer.setScore(Math.round(score * 100d) / 100d);

		if (score < this.customisationService.find().getThreshold())
			this.actorService.markAsSuspicious(trainer, true);

	}

	private List<Integer> positiveNegativeWordNumbers(final Collection<Endorsement> endorsements) {
		Assert.isTrue(endorsements != null);

		final List<Integer> results = new ArrayList<Integer>();
		String comment, positiveWords_str, negativeWords_str;
		Integer positive = 0, negative = 0;
		String[] words = {};

		positiveWords_str = this.customisationService.find().getPositiveWords();
		negativeWords_str = this.customisationService.find().getNegativeWords();

		final List<String> positive_ls = new ArrayList<>(this.utilityService.ListByString(positiveWords_str));
		final List<String> negative_ls = new ArrayList<>(this.utilityService.ListByString(negativeWords_str));

		for (final Endorsement e : endorsements) {
			comment = e.getComments().toLowerCase();
			words = comment.split(" ");

			for (final String word : words)
				if (positive_ls.contains(word))
					positive++;
				else if (negative_ls.contains(word))
					negative++;
		}

		results.add(positive);
		results.add(negative);

		return results;

	}

	private Double max(final Integer n, final Integer p) {
		Double result;

		if (n >= p)
			result = n * 1.0;
		else
			result = p * 1.0;

		return result;
	}

	protected void calculateMark(final Trainer trainer) {
		Assert.isTrue(trainer.getId() != 0);

		Double avgMark;

		avgMark = this.endorsementService.avgMarkByTrainer(trainer.getId());

		if (avgMark == null)
			trainer.setMark(0.0);
		else
			trainer.setMark(Math.round(avgMark * 100d) / 100d);

	}
	public Collection<Trainer> findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName() {
		Collection<Trainer> result;

		result = this.trainerRepository.findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName();
		Assert.notNull(result);

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

	protected Trainer findByPersonalRecordId(final int personalRecordId) {
		Trainer result;

		result = this.trainerRepository.findByPersonalRecordId(personalRecordId);
		Assert.notNull(result);

		return result;
	}

	protected Trainer findByEndorserRecordId(final int endorserRecordId) {
		Trainer result;

		result = this.trainerRepository.findByEndorserRecordId(endorserRecordId);
		Assert.notNull(result);

		return result;
	}

	protected Trainer findByEducationRecordId(final int educationRecordId) {
		Trainer result;

		result = this.trainerRepository.findByEducationRecordId(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	protected Trainer findByMiscellaneousRecordId(final int miscellaneousRecordId) {
		Trainer result;

		result = this.trainerRepository.findByMiscellaneousRecordId(miscellaneousRecordId);
		Assert.notNull(result);

		return result;
	}

	protected Trainer findByProfessionalRecordId(final int professionalRecordId) {
		Trainer result;

		result = this.trainerRepository.findByProfessionalRecordId(professionalRecordId);
		Assert.notNull(result);

		return result;
	}

	protected void flush() {
		this.trainerRepository.flush();
	}

	public Double ratioTrainerWithEndorsement() {
		Double result;

		result = this.trainerRepository.ratioTrainerWithEndorsement();

		return result;
	}

}
