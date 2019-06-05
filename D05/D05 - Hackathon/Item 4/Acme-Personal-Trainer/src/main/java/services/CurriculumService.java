
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculumRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Trainer;

@Service
@Transactional
public class CurriculumService {

	// Managed repository ------------------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private Validator				validator;


	// Constructors ------------------------------------------------------

	public CurriculumService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public Curriculum create() {
		Curriculum result;
		Trainer principal;
		PersonalRecord personalRecord;

		principal = this.trainerService.findByPrincipal();
		personalRecord = this.personalRecordService.create(principal.getFullname());
		result = new Curriculum();

		result.setTrainer(principal);
		result.setTicker("000000-XXXXXX");
		result.setPersonalRecord(personalRecord);
		result.setEndorserRecords(new HashSet<EndorserRecord>());
		result.setProfessionalRecords(new HashSet<ProfessionalRecord>());
		result.setEducationRecords(new HashSet<EducationRecord>());
		result.setMiscellaneousRecords(new HashSet<MiscellaneousRecord>());

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(!this.curriculumRepository.exists(curriculum.getId()));
		this.utilityService.checkEmail(curriculum.getPersonalRecord().getEmail());

		Trainer principal;
		Curriculum saved;
		PersonalRecord personalRecord;

		principal = this.trainerService.findByPrincipal();
		personalRecord = curriculum.getPersonalRecord();

		this.checkOwner(principal, curriculum);
		this.personalRecordService.checkFullname(principal, curriculum.getPersonalRecord());

		curriculum.setTicker(this.utilityService.generateValidTicker());
		personalRecord.setPhoneNumber(this.utilityService.getValidPhone(personalRecord.getPhoneNumber()));
		saved = this.curriculumRepository.save(curriculum);

		return saved;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public Curriculum findOneToEdit(final int curriculumId) {
		Curriculum result;

		result = this.findOne(curriculumId);
		this.checkOwner(result);

		return result;
	}

	public Curriculum findByPrincipal() {
		Curriculum result;
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		result = this.curriculumRepository.findByPrincipal(principal.getId());

		return result;
	}

	// Ancillary methods -------------------------------------------------

	public Curriculum findByTrainerId(final int trainerId) {
		Curriculum result;

		result = this.curriculumRepository.findByTrainerId(trainerId);

		return result;
	}

	public Integer findIdByPersonalRecordId(final int personalRecordId) {
		Integer result;

		result = this.curriculumRepository.findIdByPersonalRecordId(personalRecordId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByProfessionalRecordId(final int professionalRecordId) {
		Integer result;

		result = this.curriculumRepository.findIdByProfessionalRecordId(professionalRecordId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByEndorserRecordId(final int endorserRecordId) {
		Integer result;

		result = this.curriculumRepository.findIdByEndorserRecordId(endorserRecordId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByEducationRecordId(final int educationRecordId) {
		Integer result;

		result = this.curriculumRepository.findIdByEducationRecordId(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	public Integer findIdByMiscellaneousRecordId(final int miscellaneousRecordId) {
		Integer result;

		result = this.curriculumRepository.findIdByMiscellaneousRecordId(miscellaneousRecordId);
		Assert.notNull(result);

		return result;
	}

	public boolean checkPrincipalIsOwner(final Curriculum curriculum) {
		Trainer trainer;
		UserAccount principal;
		Authority auth;
		boolean res;

		principal = LoginService.getPrincipal();
		auth = new Authority();
		auth.setAuthority(Authority.TRAINER);

		if (principal != null && principal.getAuthorities().contains(auth)) {
			trainer = this.trainerService.findByPrincipal();
			res = curriculum.getTrainer().equals(trainer);
		} else
			res = false;

		return res;
	}

	public boolean checkIsAuditable(final Curriculum curriculum) {
		Audit audit;
		Auditor auditor;
		UserAccount principal;
		Authority auth;
		boolean res;

		principal = LoginService.getPrincipal();
		auth = new Authority();
		auth.setAuthority(Authority.AUDITOR);

		if (principal != null && principal.getAuthorities().contains(auth)) {
			auditor = this.auditorService.findByUserAccount(principal.getId());
			audit = this.auditService.findByAuditorIdCurriculumId(auditor.getId(), curriculum.getId());
			res = audit == null;
		} else
			res = false;

		return res;
	}

	public Curriculum reconstruct(final Curriculum curriculum, final BindingResult binding) {
		Assert.isTrue(!this.curriculumRepository.exists(curriculum.getId()));

		Curriculum result;
		PersonalRecord personalRecordResult, personalRecordForm;

		result = this.create();
		personalRecordResult = result.getPersonalRecord();
		personalRecordForm = curriculum.getPersonalRecord();
		personalRecordResult.setFullName(personalRecordForm.getFullName().trim());
		personalRecordResult.setPhoto(personalRecordForm.getPhoto().trim());
		personalRecordResult.setEmail(personalRecordForm.getEmail().trim());
		personalRecordResult.setLinkedInProfile(personalRecordForm.getLinkedInProfile().trim());
		personalRecordResult.setPhoneNumber(personalRecordForm.getPhoneNumber().trim());

		this.validator.validate(result, binding);

		return result;
	}

	protected void addEndorserRecord(final Curriculum curriculum, final EndorserRecord endorserRecord) {
		curriculum.getEndorserRecords().add(endorserRecord);
	}

	protected void addEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		curriculum.getEducationRecords().add(educationRecord);
	}

	protected void addMiscellaneousRecord(final Curriculum curriculum, final MiscellaneousRecord miscellaneousRecord) {
		curriculum.getMiscellaneousRecords().add(miscellaneousRecord);
	}

	protected void addProfessionalRecord(final Curriculum curriculum, final ProfessionalRecord professionalRecord) {
		curriculum.getProfessionalRecords().add(professionalRecord);
	}

	protected void removeEndorserRecord(final Curriculum curriculum, final EndorserRecord endorserRecord) {
		curriculum.getEndorserRecords().remove(endorserRecord);
	}

	protected void removeEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		curriculum.getEducationRecords().remove(educationRecord);
	}

	protected void removeMiscellaneousRecord(final Curriculum curriculum, final MiscellaneousRecord miscellaneousRecord) {
		curriculum.getMiscellaneousRecords().remove(miscellaneousRecord);
	}

	protected void removeProfessionalRecord(final Curriculum curriculum, final ProfessionalRecord professionalRecord) {
		curriculum.getProfessionalRecords().remove(professionalRecord);
	}

	protected void deleteCurriculums(final Trainer trainer) {
		Curriculum curriculum;

		curriculum = this.curriculumRepository.findByPrincipal(trainer.getId());

		if (curriculum != null) {
			this.auditService.deleteAudits(curriculum);
			this.curriculumRepository.delete(curriculum);
		}
	}

	private void checkOwner(final Curriculum curriculum) {
		Assert.isTrue(this.checkPrincipalIsOwner(curriculum));
	}

	private void checkOwner(final Trainer trainer, final Curriculum curriculum) {
		Assert.isTrue(trainer.equals(curriculum.getTrainer()));
	}
}
