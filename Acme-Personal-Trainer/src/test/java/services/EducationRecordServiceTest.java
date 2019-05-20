
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, CREATING, updating,
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
	//	public void educationRecordCreateTest() {
	//		EducationRecord educationRecord, savedEducationRecord;
	//		Curriculum curriculum;
	//		int curriculumId, numberEdData;
	//		String diplomaTitle, institution, attachment, comments;
	//		Date startDate, endDate;
	//
	//		// Data
	//		diplomaTitle = "Degree test";
	//		institution = "University of Seville";
	//		attachment = "https://url2.com";
	//		comments = "Estos son comentarios de test";
	//		startDate = LocalDate.now().minusYears(2).toDate();
	//		endDate = LocalDate.now().minusYears(1).toDate();
	//
	//		super.authenticate("trainer8");
	//
	//		curriculumId = super.getEntityId("curriculum81");
	//		curriculum = this.curriculumRepository.findOne(curriculumId);
	//		numberEdData = curriculum.getEducationRecords().size();
	//		educationRecord = this.educationRecordService.create();
	//
	//		educationRecord.setDiplomaTitle(diplomaTitle);
	//		educationRecord.setInstitution(institution);
	//		educationRecord.setAttachment(attachment);
	//		educationRecord.setComments(comments);
	//		educationRecord.setStartDate(startDate);
	//		educationRecord.setEndDate(endDate);
	//
	//		savedEducationRecord = this.educationRecordService.save(educationRecord, curriculumId);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(curriculum.getEducationRecords().size() == numberEdData + 1);
	//		Assert.isTrue(curriculum.getEducationRecords().contains(savedEducationRecord));
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, CREATING, updating,
	//	 * and deleting them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The education data can only be created in one of the curriculum in
	//	 * which the trainer principal is owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void educationRecordCreateNegativeTest() {
	//		EducationRecord educationRecord, savedEducationRecord;
	//		Curriculum curriculum;
	//		int curriculumId, numberEdData;
	//		String diplomaTitle, institution, attachment, comments;
	//		Date startDate, endDate;
	//
	//		// Data
	//		diplomaTitle = "Degree test";
	//		institution = "University of Seville";
	//		attachment = "https://url2.com";
	//		comments = "Estos son comentarios de test";
	//		startDate = LocalDate.now().minusYears(2).toDate();
	//		endDate = LocalDate.now().minusYears(1).toDate();
	//
	//		super.authenticate("trainer9");
	//
	//		curriculumId = super.getEntityId("curriculum81");
	//		curriculum = this.curriculumRepository.findOne(curriculumId);
	//		numberEdData = curriculum.getEducationRecords().size();
	//		educationRecord = this.educationRecordService.create();
	//
	//		educationRecord.setDiplomaTitle(diplomaTitle);
	//		educationRecord.setInstitution(institution);
	//		educationRecord.setAttachment(attachment);
	//		educationRecord.setComments(comments);
	//		educationRecord.setStartDate(startDate);
	//		educationRecord.setEndDate(endDate);
	//
	//		savedEducationRecord = this.educationRecordService.save(educationRecord, curriculumId);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(curriculum.getEducationRecords().size() == numberEdData + 1);
	//		Assert.isTrue(curriculum.getEducationRecords().contains(savedEducationRecord));
	//	}
	//
	//	/*
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
	//	public void educationRecordEditTest() {
	//		EducationRecord educationRecord, saved;
	//		int educationRecordId;
	//		String diplomaTitle;
	//
	//		// Data
	//		diplomaTitle = "Title Edit test";
	//
	//		super.authenticate("trainer8");
	//
	//		educationRecordId = super.getEntityId("educationRecord81");
	//		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
	//		educationRecord = this.cloneEducationRecord(educationRecord);
	//
	//		educationRecord.setDiplomaTitle(diplomaTitle);
	//		saved = this.educationRecordService.save(educationRecord);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(saved.getDiplomaTitle() == diplomaTitle);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, UPDATING,
	//	 * and deleting them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The Education Data can only be updated by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void educationRecordEditNegativeTest() {
	//		EducationRecord educationRecord, saved;
	//		int educationRecordId;
	//		String diplomaTitle;
	//
	//		// Data
	//		diplomaTitle = "Title Edit test";
	//
	//		super.authenticate("trainer9");
	//
	//		educationRecordId = super.getEntityId("educationRecord81");
	//		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
	//		educationRecord = this.cloneEducationRecord(educationRecord);
	//
	//		educationRecord.setDiplomaTitle(diplomaTitle);
	//		saved = this.educationRecordService.save(educationRecord);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(saved.getDiplomaTitle() == diplomaTitle);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, updating,
	//	 * and DELETING them. Except personal record which once it is created can not
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
	//	public void educationRecordDeleteTest() {
	//		EducationRecord educationRecord, saved;
	//		int educationRecordId;
	//
	//		super.authenticate("trainer8");
	//
	//		educationRecordId = super.getEntityId("educationRecord81");
	//		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
	//
	//		this.educationRecordService.delete(educationRecord);
	//
	//		super.unauthenticate();
	//
	//		saved = this.educationRecordRepository.findOne(educationRecordId);
	//		Assert.isTrue(saved == null);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, updating,
	//	 * and DELETING them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The Education Data can only be deleted by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void educationRecordDeleteNegativeTest() {
	//		EducationRecord educationRecord, saved;
	//		int educationRecordId;
	//
	//		super.authenticate("trainer9");
	//
	//		educationRecordId = super.getEntityId("educationRecord81");
	//		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
	//
	//		this.educationRecordService.delete(educationRecord);
	//
	//		super.unauthenticate();
	//
	//		saved = this.educationRecordRepository.findOne(educationRecordId);
	//		Assert.isTrue(saved == null);
	//	}

	// Ancillary methods ------------------------------------------------------

	private EducationRecord cloneEducationRecord(final EducationRecord educationRecord) {
		final EducationRecord res = new EducationRecord();

		res.setAttachment(educationRecord.getAttachment());
		res.setComments(educationRecord.getComments());
		res.setDiplomaTitle(educationRecord.getDiplomaTitle());
		res.setEndDate(educationRecord.getEndDate());
		res.setStartDate(educationRecord.getStartDate());
		res.setInstitution(educationRecord.getInstitution());
		res.setId(educationRecord.getId());
		res.setVersion(educationRecord.getVersion());

		return res;
	}
}
