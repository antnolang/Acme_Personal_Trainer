
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;
import domain.Trainer;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository ------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private TrainerService				trainerService;


	// Constructors ------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	protected PersonalRecord create(final String fullname) {
		PersonalRecord result;

		result = new PersonalRecord();
		result.setFullName(fullname);

		return result;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		Assert.isTrue(this.personalRecordRepository.exists(personalRecord.getId()));

		final Trainer principal = this.trainerService.findByPrincipal();
		PersonalRecord saved;

		this.checkOwner(principal, personalRecord.getId());
		this.checkFullname(principal, personalRecord);

		saved = this.personalRecordRepository.save(personalRecord);

		return saved;
	}

	private PersonalRecord findOne(final int personalRecordId) {
		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods --------------------------------------------

	public PersonalRecord findOneToEdit(final int personalRecordId) {
		PersonalRecord result;

		result = this.findOne(personalRecordId);
		this.checkOwner(personalRecordId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	protected void checkFullname(final Trainer trainer, final PersonalRecord personalRecord) {
		Assert.isTrue(trainer.getFullname().equals(personalRecord.getFullName()), "Fullname does not match");
	}

	private void checkOwner(final int personalRecordId) {
		Trainer principal;

		principal = this.trainerService.findByPrincipal();
		this.checkOwner(principal, personalRecordId);
	}

	private void checkOwner(final Trainer trainer, final int personalRecordId) {
		Trainer owner;

		owner = this.trainerService.findByPersonalRecordId(personalRecordId);

		Assert.isTrue(trainer.equals(owner));
	}
}
