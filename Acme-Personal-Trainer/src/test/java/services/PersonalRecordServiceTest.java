
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

	//	@Autowired
	//	private PersonalRecordService		personalRecordService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	// Tests ------------------------------------------------------------------

	//	/* TODO: Test funcionales y análisis PersonalRecord
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, UPDATING,
	//	 * and deleting them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test
	//	public void personalRecordEditTest() {
	//		PersonalRecord personalRecord, saved;
	//		int personalRecordId;
	//		String fullname;
	//
	//		// Data
	//		fullname = "Rookie8 Moreno";
	//
	//		super.authenticate("trainer8");
	//
	//		personalRecordId = super.getEntityId("personalRecord81");
	//		personalRecord = this.personalRecordRepository.findOne(personalRecordId);
	//		personalRecord = this.clonePersonalRecord(personalRecord);
	//
	//		personalRecord.setFullname(fullname);
	//		saved = this.personalRecordService.save(personalRecord);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(saved.getFullname() == fullname);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, UPDATING,
	//	 * and deleting them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The curriculum can only be updated by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void personalRecordEditNegativeTest() {
	//		PersonalRecord personalRecord, saved;
	//		int personalRecordId;
	//		String fullname;
	//
	//		// Data
	//		fullname = "Rookie9 Rubio";
	//
	//		super.authenticate("trainer9");
	//
	//		personalRecordId = super.getEntityId("personalRecord81");
	//		personalRecord = this.personalRecordRepository.findOne(personalRecordId);
	//		personalRecord = this.clonePersonalRecord(personalRecord);
	//
	//		personalRecord.setFullname(fullname);
	//		saved = this.personalRecordService.save(personalRecord);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(saved.getFullname() == fullname);
	//	}

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
