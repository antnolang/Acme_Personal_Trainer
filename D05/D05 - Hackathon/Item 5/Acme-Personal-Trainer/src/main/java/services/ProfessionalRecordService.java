
package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Curriculum;
import domain.ProfessionalRecord;
import domain.Trainer;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private TrainerService					trainerService;


	// Constructors ------------------------------------------------------

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public ProfessionalRecord create() {
		ProfessionalRecord result;

		result = new ProfessionalRecord();

		return result;
	}

	private ProfessionalRecord saveInternal(final ProfessionalRecord professionalRecord) {
		this.checkDates(professionalRecord);

		ProfessionalRecord saved;

		saved = this.professionalRecordRepository.save(professionalRecord);

		return saved;
	}

	// Editing an existing ProfessionalRecord
	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(this.professionalRecordRepository.exists(professionalRecord.getId()));
		this.checkOwner(professionalRecord.getId());

		final ProfessionalRecord saved = this.saveInternal(professionalRecord);

		return saved;
	}

	// Creating new ProfessionalRecord
	public ProfessionalRecord save(final ProfessionalRecord professionalRecord, final int curriculumId) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(!this.professionalRecordRepository.exists(professionalRecord.getId()));

		final ProfessionalRecord saved = this.saveInternal(professionalRecord);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addProfessionalRecord(curriculum, saved);

		return saved;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(this.professionalRecordRepository.exists(professionalRecord.getId()));
		this.checkOwner(professionalRecord.getId());

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByProfessionalRecordId(professionalRecord.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removeProfessionalRecord(curriculum, professionalRecord);
		this.professionalRecordRepository.delete(professionalRecord);
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public ProfessionalRecord findOneToEdit(final int professionalRecordId) {
		ProfessionalRecord result;

		result = this.findOne(professionalRecordId);
		this.checkOwner(professionalRecordId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	private void checkOwner(final int professionalRecordId) {
		Trainer principal, owner;

		principal = this.trainerService.findByPrincipal();
		owner = this.trainerService.findByProfessionalRecordId(professionalRecordId);

		Assert.isTrue(principal.equals(owner));
	}

	private void checkDates(final ProfessionalRecord professionalRecord) {
		Date startDate, endDate;

		startDate = professionalRecord.getStartDate();
		endDate = professionalRecord.getEndDate();
		Assert.isTrue(startDate == null || endDate == null || startDate.before(endDate), "Incorrect dates");
	}
}
