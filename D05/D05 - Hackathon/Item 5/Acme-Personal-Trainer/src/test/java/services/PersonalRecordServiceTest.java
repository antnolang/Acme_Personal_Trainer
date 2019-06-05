
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import utilities.AbstractTest;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private PersonalRecordService		personalRecordService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Positive test
	 * 
	 * C: 95.1% of sentence coverage, since it has been covered
	 * 39 lines of code of 41 possible.
	 * 
	 * D: Approximately 22.7% of data coverage, since it has been used 5
	 * values in the data of 22 different possible values.
	 */
	@Test
	public void personalRecordEditTest() {
		PersonalRecord personalRecord, saved;
		int personalRecordId;
		String fullname;

		// Data
		fullname = "Trainer1 trainer";

		super.authenticate("trainer1");

		personalRecordId = super.getEntityId("personalRecord1");
		personalRecord = this.personalRecordRepository.findOne(personalRecordId);
		personalRecord = this.clonePersonalRecord(personalRecord);

		personalRecord.setFullName(fullname);
		saved = this.personalRecordService.save(personalRecord);
		this.personalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getFullName() == fullname);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The curriculum can only be updated by its owner.
	 * 
	 * C: 41.5% of sentence coverage, since it has been covered
	 * 17 lines of code of 41 possible.
	 * 
	 * D: Approximately 22.7% of data coverage, since it has been used 5
	 * values in the data of 22 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void personalRecordEditNegativeTest() {
		PersonalRecord personalRecord, saved;
		int personalRecordId;
		String fullname;

		// Data
		fullname = "Trainer1 trainer";

		super.authenticate("trainer2");

		personalRecordId = super.getEntityId("personalRecord1");
		personalRecord = this.personalRecordRepository.findOne(personalRecordId);
		personalRecord = this.clonePersonalRecord(personalRecord);

		personalRecord.setFullName(fullname);
		saved = this.personalRecordService.save(personalRecord);
		this.personalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getFullName() == fullname);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The fullname of the personal record must match with the fullname of
	 * the owner.
	 * 
	 * C: 95.1% of sentence coverage, since it has been covered
	 * 39 lines of code of 41 possible.
	 * 
	 * D: Approximately 22.7% of data coverage, since it has been used 5
	 * values in the data of 22 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void personalRecordEditNegativeTest2() {
		PersonalRecord personalRecord, saved;
		int personalRecordId;
		String fullname;

		// Data
		fullname = "Este fullname no es correcto";

		super.authenticate("trainer1");

		personalRecordId = super.getEntityId("personalRecord1");
		personalRecord = this.personalRecordRepository.findOne(personalRecordId);
		personalRecord = this.clonePersonalRecord(personalRecord);

		personalRecord.setFullName(fullname);
		saved = this.personalRecordService.save(personalRecord);
		this.personalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getFullName() == fullname);
	}

	// Ancillary methods ------------------------------------------------------

	private PersonalRecord clonePersonalRecord(final PersonalRecord personalRecord) {
		final PersonalRecord res = new PersonalRecord();

		res.setFullName(personalRecord.getFullName());
		res.setId(personalRecord.getId());
		res.setLinkedInProfile(personalRecord.getLinkedInProfile());
		res.setPhoneNumber(personalRecord.getPhoneNumber());
		res.setEmail(personalRecord.getEmail());
		res.setPhoto(personalRecord.getPhoto());
		res.setVersion(personalRecord.getVersion());

		return res;
	}
}
