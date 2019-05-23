
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import repositories.EducationRecordRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private EducationRecordService		educationRecordService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

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
	 * C: 100% of sentence coverage, since it has been covered
	 * 39 lines of code of 39 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 6
	 * values in the data of 20 different possible values.
	 */
	@Test
	public void educationRecordCreateTest() {
		EducationRecord educationRecord, savedEducationRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String diplomaTitle, institution, attachment, comments;
		Date startDate, endDate;

		// Data
		diplomaTitle = "Degree test";
		institution = "University of Seville";
		attachment = "https://url2.com";
		comments = "Estos son comentarios de test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEducationRecords().size();
		educationRecord = this.educationRecordService.create();

		educationRecord.setDiplomaTitle(diplomaTitle);
		educationRecord.setInstitution(institution);
		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);

		savedEducationRecord = this.educationRecordService.save(educationRecord, curriculumId);
		this.educationRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getEducationRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEducationRecords().contains(savedEducationRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The education data can only be created in one of the curriculum in
	 * which the trainer principal is owner.
	 * 
	 * C: 87.2% of sentence coverage, since it has been covered
	 * 34 lines of code of 39 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 6
	 * values in the data of 20 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationRecordCreateNegativeTest() {
		EducationRecord educationRecord, savedEducationRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String diplomaTitle, institution, attachment, comments;
		Date startDate, endDate;

		// Data
		diplomaTitle = "Degree test";
		institution = "University of Seville";
		attachment = "https://url2.com";
		comments = "Estos son comentarios de test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("trainer2");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEducationRecords().size();
		educationRecord = this.educationRecordService.create();

		educationRecord.setDiplomaTitle(diplomaTitle);
		educationRecord.setInstitution(institution);
		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);

		savedEducationRecord = this.educationRecordService.save(educationRecord, curriculumId);
		this.educationRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getEducationRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEducationRecords().contains(savedEducationRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Start date must be earlier than end date
	 * 
	 * C: 15.4% of sentence coverage, since it has been covered
	 * 6 lines of code of 39 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 6
	 * values in the data of 20 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationRecordCreateNegativeTest2() {
		EducationRecord educationRecord, savedEducationRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String diplomaTitle, institution, attachment, comments;
		Date startDate, endDate;

		// Data
		diplomaTitle = "Degree test";
		institution = "University of Seville";
		attachment = "https://url2.com";
		comments = "Estos son comentarios de test";
		endDate = LocalDate.now().minusYears(2).toDate();
		startDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getEducationRecords().size();
		educationRecord = this.educationRecordService.create();

		educationRecord.setDiplomaTitle(diplomaTitle);
		educationRecord.setInstitution(institution);
		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);

		savedEducationRecord = this.educationRecordService.save(educationRecord, curriculumId);
		this.educationRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getEducationRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getEducationRecords().contains(savedEducationRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 27 lines of code of 27 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 6
	 * values in the data of 20 different possible values.
	 */
	@Test
	public void educationRecordEditTest() {
		EducationRecord educationRecord, saved;
		int educationRecordId;
		String diplomaTitle;

		// Data
		diplomaTitle = "Title Edit test";

		super.authenticate("trainer1");

		educationRecordId = super.getEntityId("educationRecord1");
		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
		educationRecord = this.cloneEducationRecord(educationRecord);

		educationRecord.setDiplomaTitle(diplomaTitle);
		saved = this.educationRecordService.save(educationRecord);
		this.educationRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getDiplomaTitle() == diplomaTitle);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Education Data can only be updated by its owner.
	 * 
	 * C: 70.3% of sentence coverage, since it has been covered
	 * 19 lines of code of 27 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 6
	 * values in the data of 20 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationRecordEditNegativeTest() {
		EducationRecord educationRecord, saved;
		int educationRecordId;
		String diplomaTitle;

		// Data
		diplomaTitle = "Title Edit test";

		super.authenticate("trainer2");

		educationRecordId = super.getEntityId("educationRecord1");
		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
		educationRecord = this.cloneEducationRecord(educationRecord);

		educationRecord.setDiplomaTitle(diplomaTitle);
		saved = this.educationRecordService.save(educationRecord);
		this.educationRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getDiplomaTitle() == diplomaTitle);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Start date must be a past date.
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 27 lines of code of 27 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 6
	 * values in the data of 20 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void educationRecordEditNegativeTest2() {
		EducationRecord educationRecord, saved;
		int educationRecordId;
		String diplomaTitle;
		Date startDate;

		// Data
		diplomaTitle = "Title Edit test";
		startDate = LocalDate.now().plusDays(1).toDate();

		super.authenticate("trainer1");

		educationRecordId = super.getEntityId("educationRecord1");
		educationRecord = this.educationRecordRepository.findOne(educationRecordId);
		educationRecord = this.cloneEducationRecord(educationRecord);

		educationRecord.setDiplomaTitle(diplomaTitle);
		educationRecord.setStartDate(startDate);
		saved = this.educationRecordService.save(educationRecord);
		this.educationRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getDiplomaTitle() == diplomaTitle);
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
	 * D: 100% of data coverage
	 */
	@Test
	public void educationRecordDeleteTest() {
		EducationRecord educationRecord, saved;
		int educationRecordId;

		super.authenticate("trainer1");

		educationRecordId = super.getEntityId("educationRecord1");
		educationRecord = this.educationRecordRepository.findOne(educationRecordId);

		this.educationRecordService.delete(educationRecord);

		super.unauthenticate();

		saved = this.educationRecordRepository.findOne(educationRecordId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, updating,
	 * and DELETING them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Education Data can only be deleted by its owner.
	 * 
	 * C: 32% of sentence coverage, since it has been covered
	 * 16 lines of code of 50 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void educationRecordDeleteNegativeTest() {
		EducationRecord educationRecord, saved;
		int educationRecordId;

		super.authenticate("trainer2");

		educationRecordId = super.getEntityId("educationRecord1");
		educationRecord = this.educationRecordRepository.findOne(educationRecordId);

		this.educationRecordService.delete(educationRecord);

		super.unauthenticate();

		saved = this.educationRecordRepository.findOne(educationRecordId);
		Assert.isTrue(saved == null);
	}

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
