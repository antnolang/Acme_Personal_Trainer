
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	//		/* TODO: Test funcionales y análisis EndorserRecord
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, CREATING, updating,
	//		 * and deleting them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: Positive test
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test
	//		public void endorserRecordCreateTest() {
	//			EndorserRecord endorserRecord, savedEndorserRecord;
	//			Curriculum curriculum;
	//			int curriculumId, numberEdData;
	//			String fullname, email, phoneNumber, linkedInProf;
	//	
	//			// Data
	//			fullname = "Trainer Valera";
	//			email = "tvalera@gmail.com";
	//			phoneNumber = "+34 123123123";
	//			linkedInProf = "https://www.linkedin.com/tvalera";
	//	
	//			super.authenticate("trainer8");
	//	
	//			curriculumId = super.getEntityId("curriculum81");
	//			curriculum = this.curriculumRepository.findOne(curriculumId);
	//			numberEdData = curriculum.getEndorserRecords().size();
	//			endorserRecord = this.endorserRecordService.create();
	//	
	//			endorserRecord.setEmail(email);
	//			endorserRecord.setFullname(fullname);
	//			endorserRecord.setLinkedInProfile(linkedInProf);
	//			endorserRecord.setPhoneNumber(phoneNumber);
	//	
	//			savedEndorserRecord = this.endorserRecordService.save(endorserRecord, curriculumId);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(curriculum.getEndorserRecords().size() == numberEdData + 1);
	//			Assert.isTrue(curriculum.getEndorserRecords().contains(savedEndorserRecord));
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, CREATING, updating,
	//		 * and deleting them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: The endorser data can only be created in one of the curriculum in
	//		 * which the trainer principal is owner.
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test(expected = IllegalArgumentException.class)
	//		public void endorserRecordCreateNegativeTest() {
	//			EndorserRecord endorserRecord, savedEndorserRecord;
	//			Curriculum curriculum;
	//			int curriculumId, numberEdData;
	//			String fullname, email, phoneNumber, linkedInProf;
	//	
	//			// Data
	//			fullname = "Trainer Valera";
	//			email = "tvalera@gmail.com";
	//			phoneNumber = "+34 123123123";
	//			linkedInProf = "https://www.linkedin.com/tvalera";
	//	
	//			super.authenticate("trainer9");
	//	
	//			curriculumId = super.getEntityId("curriculum81");
	//			curriculum = this.curriculumRepository.findOne(curriculumId);
	//			numberEdData = curriculum.getEndorserRecords().size();
	//			endorserRecord = this.endorserRecordService.create();
	//	
	//			endorserRecord.setEmail(email);
	//			endorserRecord.setFullname(fullname);
	//			endorserRecord.setLinkedInProfile(linkedInProf);
	//			endorserRecord.setPhoneNumber(phoneNumber);
	//	
	//			savedEndorserRecord = this.endorserRecordService.save(endorserRecord, curriculumId);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(curriculum.getEndorserRecords().size() == numberEdData + 1);
	//			Assert.isTrue(curriculum.getEndorserRecords().contains(savedEndorserRecord));
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, creating, UPDATING,
	//		 * and deleting them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: Positive test
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test
	//		public void endorserRecordEditTest() {
	//			EndorserRecord endorserRecord, saved;
	//			int endorserRecordId;
	//			String fullname;
	//	
	//			// Data
	//			fullname = "Name test";
	//	
	//			super.authenticate("trainer8");
	//	
	//			endorserRecordId = super.getEntityId("endorserRecord81");
	//			endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
	//			endorserRecord = this.cloneEndorserRecord(endorserRecord);
	//	
	//			endorserRecord.setFullname(fullname);
	//			saved = this.endorserRecordService.save(endorserRecord);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(saved.getFullname() == fullname);
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, creating, UPDATING,
	//		 * and deleting them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: The Endorser Data can only be updated by its owner.
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test(expected = IllegalArgumentException.class)
	//		public void endorserRecordEditNegativeTest() {
	//			EndorserRecord endorserRecord, saved;
	//			int endorserRecordId;
	//			String fullname;
	//	
	//			// Data
	//			fullname = "Name test";
	//	
	//			super.authenticate("trainer9");
	//	
	//			endorserRecordId = super.getEntityId("endorserRecord81");
	//			endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
	//			endorserRecord = this.cloneEndorserRecord(endorserRecord);
	//	
	//			endorserRecord.setFullname(fullname);
	//			saved = this.endorserRecordService.save(endorserRecord);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(saved.getFullname() == fullname);
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, creating, updating,
	//		 * and DELETING them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: Positive test
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test
	//		public void endorserRecordDeleteTest() {
	//			EndorserRecord endorserRecord, saved;
	//			int endorserRecordId;
	//	
	//			super.authenticate("trainer8");
	//	
	//			endorserRecordId = super.getEntityId("endorserRecord81");
	//			endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
	//	
	//			this.endorserRecordService.delete(endorserRecord);
	//	
	//			super.unauthenticate();
	//	
	//			saved = this.endorserRecordRepository.findOne(endorserRecordId);
	//			Assert.isTrue(saved == null);
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, creating, updating,
	//		 * and DELETING them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: The Endorser Data can only be deleted by its owner.
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test(expected = IllegalArgumentException.class)
	//		public void endorserRecordDeleteNegativeTest() {
	//			EndorserRecord endorserRecord, saved;
	//			int endorserRecordId;
	//	
	//			super.authenticate("trainer9");
	//	
	//			endorserRecordId = super.getEntityId("endorserRecord81");
	//			endorserRecord = this.endorserRecordRepository.findOne(endorserRecordId);
	//	
	//			this.endorserRecordService.delete(endorserRecord);
	//	
	//			super.unauthenticate();
	//	
	//			saved = this.endorserRecordRepository.findOne(endorserRecordId);
	//			Assert.isTrue(saved == null);
	//		}

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
