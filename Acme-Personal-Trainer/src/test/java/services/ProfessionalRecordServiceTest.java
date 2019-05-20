
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	//		/* TODO: Test funcionales y análisis ProfessionalRecord
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
	//		public void professionalRecordCreateTest() {
	//			ProfessionalRecord professionalRecord, savedProfessionalRecord;
	//			Curriculum curriculum;
	//			int curriculumId, numberEdData;
	//			String company, role, attachment, comments;
	//			Date startDate, endDate;
	//	
	//			// Data
	//			company = "Company test";
	//			role = "Programmer";
	//			attachment = "http://www.url.com";
	//			comments = "Comment test";
	//			startDate = LocalDate.now().minusYears(2).toDate();
	//			endDate = LocalDate.now().minusYears(1).toDate();
	//	
	//			super.authenticate("trainer8");
	//	
	//			curriculumId = super.getEntityId("curriculum81");
	//			curriculum = this.curriculumRepository.findOne(curriculumId);
	//			numberEdData = curriculum.getProfessionalRecords().size();
	//			professionalRecord = this.professionalRecordService.create();
	//	
	//			professionalRecord.setAttachment(attachment);
	//			professionalRecord.setComments(comments);
	//			professionalRecord.setCompany(company);
	//			professionalRecord.setEndDate(endDate);
	//			professionalRecord.setStartDate(startDate);
	//	
	//			savedProfessionalRecord = this.professionalRecordService.save(professionalRecord, curriculumId);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(curriculum.getProfessionalRecords().size() == numberEdData + 1);
	//			Assert.isTrue(curriculum.getProfessionalRecords().contains(savedProfessionalRecord));
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, CREATING, updating,
	//		 * and deleting them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: The professional data can only be created in one of the curriculum in
	//		 * which the trainer principal is owner.
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test(expected = IllegalArgumentException.class)
	//		public void professionalRecordCreateNegativeTest() {
	//			ProfessionalRecord professionalRecord, savedProfessionalRecord;
	//			Curriculum curriculum;
	//			int curriculumId, numberEdData;
	//			String company, role, attachment, comments;
	//			Date startDate, endDate;
	//	
	//			// Data
	//			company = "Company test";
	//			role = "Programmer";
	//			attachment = "http://www.url.com";
	//			comments = "Comment test";
	//			startDate = LocalDate.now().minusYears(2).toDate();
	//			endDate = LocalDate.now().minusYears(1).toDate();
	//	
	//			super.authenticate("trainer9");
	//	
	//			curriculumId = super.getEntityId("curriculum81");
	//			curriculum = this.curriculumRepository.findOne(curriculumId);
	//			numberEdData = curriculum.getProfessionalRecords().size();
	//			professionalRecord = this.professionalRecordService.create();
	//	
	//			professionalRecord.setAttachment(attachment);
	//			professionalRecord.setComments(comments);
	//			professionalRecord.setCompany(company);
	//			professionalRecord.setEndDate(endDate);
	//			professionalRecord.setStartDate(startDate);
	//	
	//			savedProfessionalRecord = this.professionalRecordService.save(professionalRecord, curriculumId);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(curriculum.getProfessionalRecords().size() == numberEdData + 1);
	//			Assert.isTrue(curriculum.getProfessionalRecords().contains(savedProfessionalRecord));
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
	//		public void professionalRecordEditTest() {
	//			ProfessionalRecord professionalRecord, saved;
	//			int professionalRecordId;
	//			String company;
	//	
	//			// Data
	//			company = "Company test";
	//	
	//			super.authenticate("trainer8");
	//	
	//			professionalRecordId = super.getEntityId("professionalRecord81");
	//			professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
	//			professionalRecord = this.cloneProfessionalRecord(professionalRecord);
	//	
	//			professionalRecord.setCompany(company);
	//			saved = this.professionalRecordService.save(professionalRecord);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(saved.getCompany() == company);
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, creating, UPDATING,
	//		 * and deleting them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: The Professional Data can only be updated by its owner.
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test(expected = IllegalArgumentException.class)
	//		public void professionalRecordEditNegativeTest() {
	//			ProfessionalRecord professionalRecord, saved;
	//			int professionalRecordId;
	//			String company;
	//	
	//			// Data
	//			company = "Company test";
	//	
	//			super.authenticate("trainer9");
	//	
	//			professionalRecordId = super.getEntityId("professionalRecord81");
	//			professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
	//			professionalRecord = this.cloneProfessionalRecord(professionalRecord);
	//	
	//			professionalRecord.setCompany(company);
	//			saved = this.professionalRecordService.save(professionalRecord);
	//	
	//			super.unauthenticate();
	//	
	//			Assert.isTrue(saved.getCompany() == company);
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
	//		public void professionalRecordDeleteTest() {
	//			ProfessionalRecord professionalRecord, saved;
	//			int professionalRecordId;
	//	
	//			super.authenticate("trainer8");
	//	
	//			professionalRecordId = super.getEntityId("professionalRecord81");
	//			professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
	//	
	//			this.professionalRecordService.delete(professionalRecord);
	//	
	//			super.unauthenticate();
	//	
	//			saved = this.professionalRecordRepository.findOne(professionalRecordId);
	//			Assert.isTrue(saved == null);
	//		}
	//	
	//		/*
	//		 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//		 * or her records, which includes listing, showing, creating, updating,
	//		 * and DELETING them. Except personal record which once it is created can not
	//		 * be deleted.
	//		 * 
	//		 * B: The Professional Data can only be deleted by its owner.
	//		 * 
	//		 * C: x% of sentence coverage, since it has been covered
	//		 * x lines of code of x possible.
	//		 * 
	//		 * D: Approximately X% of data coverage, since it has been used x
	//		 * values in the data of x different possible values.
	//		 */
	//		@Test(expected = IllegalArgumentException.class)
	//		public void professionalRecordDeleteNegativeTest() {
	//			ProfessionalRecord professionalRecord, saved;
	//			int professionalRecordId;
	//	
	//			super.authenticate("trainer9");
	//	
	//			professionalRecordId = super.getEntityId("professionalRecord81");
	//			professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
	//	
	//			this.professionalRecordService.delete(professionalRecord);
	//	
	//			super.unauthenticate();
	//	
	//			saved = this.professionalRecordRepository.findOne(professionalRecordId);
	//			Assert.isTrue(saved == null);
	//		}

	// Ancillary methods ------------------------------------------------------

	private ProfessionalRecord cloneProfessionalRecord(final ProfessionalRecord professionalRecord) {
		final ProfessionalRecord res = new ProfessionalRecord();

		res.setAttachment(professionalRecord.getAttachment());
		res.setComments(professionalRecord.getComments());
		res.setCompany(professionalRecord.getCompany());
		res.setEndDate(professionalRecord.getEndDate());
		res.setRole(professionalRecord.getRole());
		res.setStartDate(professionalRecord.getStartDate());
		res.setId(professionalRecord.getId());
		res.setVersion(professionalRecord.getVersion());

		return res;
	}
}
