
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import repositories.EndorserRecordRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private EndorserRecordService		endorserRecordService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	@Autowired
	private CurriculumRepository		curriculumRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Positive test
	 * 
	 * C: 92.3% of sentence coverage, since it has been covered
	 * 48 lines of code of 52 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test
	public void endorserRecordCreateTest() {
		EndorserRecord endorserRecord, savedEndorserRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String fullname, email, phoneNumber, linkedInProf;

		// Data
		fullname = "Trainer Valera";
		email = "tvalera@gmail.com";
		phoneNumber = "+34 123123123";
		linkedInProf = "https://www.linkedin.com/tvalera";

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEndorserRecords().size();
		endorserRecord = this.endorserRecordService.create();

		endorserRecord.setEmail(email);
		endorserRecord.setFullname(fullname);
		endorserRecord.setLinkedInProfile(linkedInProf);
		endorserRecord.setPhoneNumber(phoneNumber);

		savedEndorserRecord = this.endorserRecordService.save(endorserRecord, curriculumId);
		this.endorserRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getEndorserRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEndorserRecords().contains(savedEndorserRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The endorser data can only be created in one of the curriculum in
	 * which the trainer principal is owner.
	 * 
	 * C: 86.5% of sentence coverage, since it has been covered
	 * 45 lines of code of 52 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void endorserRecordCreateNegativeTest() {
		EndorserRecord endorserRecord, savedEndorserRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String fullname, email, phoneNumber, linkedInProf;

		// Data
		fullname = "Trainer Valera";
		email = "tvalera@gmail.com";
		phoneNumber = "+34 123123123";
		linkedInProf = "https://www.linkedin.com/tvalera";

		super.authenticate("trainer2");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEndorserRecords().size();
		endorserRecord = this.endorserRecordService.create();

		endorserRecord.setEmail(email);
		endorserRecord.setFullname(fullname);
		endorserRecord.setLinkedInProfile(linkedInProf);
		endorserRecord.setPhoneNumber(phoneNumber);

		savedEndorserRecord = this.endorserRecordService.save(endorserRecord, curriculumId);
		this.endorserRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getEndorserRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEndorserRecords().contains(savedEndorserRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: LinkedInProfile must belong to the LinkedIn domain.
	 * 
	 * C: 92.3% of sentence coverage, since it has been covered
	 * 48 lines of code of 52 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void endorserRecordCreateNegativeTest2() {
		EndorserRecord endorserRecord, savedEndorserRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String fullname, email, phoneNumber, linkedInProf;

		// Data
		fullname = "Trainer Valera";
		email = "tvalera@gmail.com";
		phoneNumber = "+34 123123123";
		linkedInProf = "https://linkedin.org/tvalera";

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEndorserRecords().size();
		endorserRecord = this.endorserRecordService.create();

		endorserRecord.setEmail(email);
		endorserRecord.setFullname(fullname);
		endorserRecord.setLinkedInProfile(linkedInProf);
		endorserRecord.setPhoneNumber(phoneNumber);

		savedEndorserRecord = this.endorserRecordService.save(endorserRecord, curriculumId);
		this.endorserRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getEndorserRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEndorserRecords().contains(savedEndorserRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Positive test
	 * 
	 * C: 90.2% of sentence coverage, since it has been covered
	 * 37 lines of code of 41 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test
	public void endorserRecordEditTest() {
		EndorserRecord endorserRecord, saved;
		int endorserRecordId;
		String fullname;

		// Data
		fullname = "Name test";

		super.authenticate("trainer1");

		endorserRecordId = super.getEntityId("endorserRecord1");
		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
		endorserRecord = this.cloneEndorserRecord(endorserRecord);

		endorserRecord.setFullname(fullname);
		saved = this.endorserRecordService.save(endorserRecord);
		this.endorserRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getFullname() == fullname);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Endorser Data can only be updated by its owner.
	 * 
	 * C: 39% of sentence coverage, since it has been covered
	 * 16 lines of code of 41 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void endorserRecordEditNegativeTest() {
		EndorserRecord endorserRecord, saved;
		int endorserRecordId;
		String fullname;

		// Data
		fullname = "Name test";

		super.authenticate("trainer2");

		endorserRecordId = super.getEntityId("endorserRecord1");
		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
		endorserRecord = this.cloneEndorserRecord(endorserRecord);

		endorserRecord.setFullname(fullname);
		saved = this.endorserRecordService.save(endorserRecord);
		this.endorserRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getFullname() == fullname);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: LinkedInProfile must belong to the LinkedIn domain.
	 * 
	 * C: 90.2% of sentence coverage, since it has been covered
	 * 37 lines of code of 41 possible.
	 * 
	 * D: Approximately 26.7% of data coverage, since it has been used 4
	 * values in the data of 15 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void endorserRecordEditNegativeTest2() {
		EndorserRecord endorserRecord, saved;
		int endorserRecordId;
		String fullname, linkedinprof;

		// Data
		fullname = "Name test";
		linkedinprof = "http://linkedin.org/pilohu";

		super.authenticate("trainer1");

		endorserRecordId = super.getEntityId("endorserRecord1");
		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
		endorserRecord = this.cloneEndorserRecord(endorserRecord);

		endorserRecord.setFullname(fullname);
		endorserRecord.setLinkedInProfile(linkedinprof);
		saved = this.endorserRecordService.save(endorserRecord);
		this.endorserRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getFullname() == fullname);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, updating,
	 * and DELETING them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 50 lines of code of 50 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void endorserRecordDeleteTest() {
		EndorserRecord endorserRecord, saved;
		int endorserRecordId;

		super.authenticate("trainer1");

		endorserRecordId = super.getEntityId("endorserRecord1");
		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);

		this.endorserRecordService.delete(endorserRecord);

		super.unauthenticate();

		saved = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, updating,
	 * and DELETING them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Endorser Data can only be deleted by its owner.
	 * 
	 * C: 32% of sentence coverage, since it has been covered
	 * 16 lines of code of 50 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void endorserRecordDeleteNegativeTest() {
		EndorserRecord endorserRecord, saved;
		int endorserRecordId;

		super.authenticate("trainer2");

		endorserRecordId = super.getEntityId("endorserRecord1");
		endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);

		this.endorserRecordService.delete(endorserRecord);

		super.unauthenticate();

		saved = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.isTrue(saved == null);
	}

	// Ancillary methods ------------------------------------------------------

	private EndorserRecord cloneEndorserRecord(final EndorserRecord endorserRecord) {
		final EndorserRecord res = new EndorserRecord();

		res.setEmail(endorserRecord.getEmail());
		res.setFullname(endorserRecord.getFullname());
		res.setLinkedInProfile(endorserRecord.getLinkedInProfile());
		res.setPhoneNumber(endorserRecord.getPhoneNumber());
		res.setId(endorserRecord.getId());
		res.setVersion(endorserRecord.getVersion());

		return res;
	}
}
