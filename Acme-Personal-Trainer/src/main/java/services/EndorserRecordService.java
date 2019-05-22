
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.Trainer;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository ------------------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private TrainerService				trainerService;

	@Autowired
	private UtilityService				utilityService;

	@Autowired
	private CurriculumService			curriculumService;


	// Constructors ------------------------------------------------------

	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public EndorserRecord create() {
		EndorserRecord result;

		result = new EndorserRecord();

		return result;
	}

	private EndorserRecord saveInternal(final EndorserRecord endorserRecord) {
		this.utilityService.checkEmail(endorserRecord.getEmail());

		EndorserRecord saved;

		saved = this.endorserRecordRepository.save(endorserRecord);

		return saved;
	}

	// Editing an existing EndorserRecord
	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(this.endorserRecordRepository.exists(endorserRecord.getId()));
		this.checkOwner(endorserRecord.getId());

		final EndorserRecord saved = this.saveInternal(endorserRecord);

		return saved;
	}

	// Creating new EndorserRecord
	public EndorserRecord save(final EndorserRecord endorserRecord, final int curriculumId) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(!this.endorserRecordRepository.exists(endorserRecord.getId()));

		final EndorserRecord saved = this.saveInternal(endorserRecord);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addEndorserRecord(curriculum, saved);

		return saved;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(this.endorserRecordRepository.exists(endorserRecord.getId()));
		this.checkOwner(endorserRecord.getId());

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByEndorserRecordId(endorserRecord.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removeEndorserRecord(curriculum, endorserRecord);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public EndorserRecord findOneToEdit(final int endorserRecordId) {
		EndorserRecord result;

		result = this.findOne(endorserRecordId);
		this.checkOwner(endorserRecordId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	private void checkOwner(final int endorserRecordId) {
		Trainer principal, owner;

		principal = this.trainerService.findByPrincipal();
		owner = this.trainerService.findByEndorserRecordId(endorserRecordId);

		Assert.isTrue(principal.equals(owner));
	}
}
