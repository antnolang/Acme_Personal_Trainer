
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Trainer;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository ------------------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private TrainerService					trainerService;


	// Constructors ------------------------------------------------------

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public MiscellaneousRecord create() {
		MiscellaneousRecord result;

		result = new MiscellaneousRecord();

		return result;
	}

	// Editing an existing MiscellaneousRecord
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()));
		this.checkOwner(miscellaneousRecord.getId());

		final MiscellaneousRecord saved = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		return saved;
	}

	// Creating new MiscellaneousRecord
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord, final int curriculumId) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(!this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()));

		final MiscellaneousRecord saved = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		this.curriculumService.addMiscellaneousRecord(curriculum, saved);

		return saved;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()));
		this.checkOwner(miscellaneousRecord.getId());

		int curriculumId;
		Curriculum curriculum;

		curriculumId = this.curriculumService.findIdByMiscellaneousRecordId(miscellaneousRecord.getId());
		curriculum = this.curriculumService.findOneToEdit(curriculumId);
		this.curriculumService.removeMiscellaneousRecord(curriculum, miscellaneousRecord);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public MiscellaneousRecord findOneToEdit(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.findOne(miscellaneousRecordId);
		this.checkOwner(miscellaneousRecordId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	private void checkOwner(final int miscellaneousRecordId) {
		Trainer principal, owner;

		principal = this.trainerService.findByPrincipal();
		owner = this.trainerService.findByMiscellaneousRecordId(miscellaneousRecordId);

		Assert.isTrue(principal.equals(owner));
	}
}
