
package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Trainer;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository ------------------------------------------------

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private TrainerService				trainerService;


	// Constructors ------------------------------------------------------

	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public EducationRecord create() {
		EducationRecord result;

		result = new EducationRecord();

		return result;
	}

	private EducationRecord saveInternal(final EducationRecord educationRecord) {
		this.checkDates(educationRecord);

		EducationRecord saved;

		saved = this.educationRecordRepository.save(educationRecord);

		return saved;
	}

	// Editing an existing EducationRecord
	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(this.educationRecordRepository.exists(educationRecord.getId()));
		this.checkOwner(educationRecord.getId());

		final EducationRecord saved = this.saveInternal(educationRecord);

		return saved;
	}

	// Creating new EducationRecord
	public EducationRecord save(final EducationRecord educationRecord, final int curriculumId) {
		Assert.notNull(educationRecord);
		Assert.isTrue(!this.educationRecordRepository.exists(educationRecord.getId()));

		final EducationRecord saved = this.saveInternal(educationRecord);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addEducationRecord(curriculum, saved);

		return saved;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(this.educationRecordRepository.exists(educationRecord.getId()));
		this.checkOwner(educationRecord.getId());

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByEducationRecordId(educationRecord.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removeEducationRecord(curriculum, educationRecord);
		this.educationRecordRepository.delete(educationRecord);
	}

	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public EducationRecord findOneToEdit(final int educationRecordId) {
		EducationRecord result;

		result = this.findOne(educationRecordId);
		this.checkOwner(educationRecordId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	private void checkOwner(final int educationRecordId) {
		Trainer principal, owner;

		principal = this.trainerService.findByPrincipal();
		owner = this.trainerService.findByEducationRecordId(educationRecordId);

		Assert.isTrue(principal.equals(owner));
	}

	private void checkDates(final EducationRecord educationRecord) {
		Date startDate, endDate;

		startDate = educationRecord.getStartDate();
		endDate = educationRecord.getEndDate();
		Assert.isTrue(startDate == null || endDate == null || startDate.before(endDate), "Incorrect dates");
	}
}
