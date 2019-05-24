
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
import repositories.ProfessionalRecordRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private ProfessionalRecordService		professionalRecordService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	@Autowired
	private CurriculumRepository			curriculumRepository;


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
	 * 40 lines of code of 40 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 6
	 * values in the data of 21 different possible values.
	 */
	@Test
	public void professionalRecordCreateTest() {
		ProfessionalRecord professionalRecord, savedProfessionalRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String company, role, attachment, comments;
		Date startDate, endDate;

		// Data
		company = "Company test";
		role = "Programmer";
		attachment = "http://www.url.com";
		comments = "Comment test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getProfessionalRecords().size();
		professionalRecord = this.professionalRecordService.create();

		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);
		professionalRecord.setCompany(company);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setRole(role);

		savedProfessionalRecord = this.professionalRecordService.save(professionalRecord, curriculumId);
		this.professionalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getProfessionalRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getProfessionalRecords().contains(savedProfessionalRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The professional data can only be created in one of the curriculum in
	 * which the trainer principal is owner.
	 * 
	 * C: 95% of sentence coverage, since it has been covered
	 * 38 lines of code of 40 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 6
	 * values in the data of 21 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void professionalRecordCreateNegativeTest() {
		ProfessionalRecord professionalRecord, savedProfessionalRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String company, role, attachment, comments;
		Date startDate, endDate;

		// Data
		company = "Company test";
		role = "Programmer";
		attachment = "http://www.url.com";
		comments = "Comment test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("trainer2");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getProfessionalRecords().size();
		professionalRecord = this.professionalRecordService.create();

		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);
		professionalRecord.setCompany(company);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setRole(role);

		savedProfessionalRecord = this.professionalRecordService.save(professionalRecord, curriculumId);
		this.professionalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getProfessionalRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getProfessionalRecords().contains(savedProfessionalRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Attachment must be a valid URL
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 40 lines of code of 40 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 6
	 * values in the data of 21 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void professionalRecordCreateNegativeTest2() {
		ProfessionalRecord professionalRecord, savedProfessionalRecord;
		Curriculum curriculum;
		int curriculumId, numberEdData;
		String company, role, attachment, comments;
		Date startDate, endDate;

		// Data
		company = "Company test";
		role = "Programmer";
		attachment = "Esto no es una URL";
		comments = "Comment test";
		startDate = LocalDate.now().minusYears(2).toDate();
		endDate = LocalDate.now().minusYears(1).toDate();

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberEdData = curriculum.getProfessionalRecords().size();
		professionalRecord = this.professionalRecordService.create();

		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);
		professionalRecord.setCompany(company);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setRole(role);

		savedProfessionalRecord = this.professionalRecordService.save(professionalRecord, curriculumId);
		this.professionalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getProfessionalRecords().size() == numberEdData + 1);
		Assert.isTrue(curriculum.getProfessionalRecords().contains(savedProfessionalRecord));
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
	 * 26 lines of code of 26 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 6
	 * values in the data of 21 different possible values.
	 */
	@Test
	public void professionalRecordEditTest() {
		ProfessionalRecord professionalRecord, saved;
		int professionalRecordId;
		String company;

		// Data
		company = "Company test";

		super.authenticate("trainer1");

		professionalRecordId = super.getEntityId("professionalRecord1");
		professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
		professionalRecord = this.cloneProfessionalRecord(professionalRecord);

		professionalRecord.setCompany(company);
		saved = this.professionalRecordService.save(professionalRecord);
		this.professionalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getCompany() == company);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Professional Data can only be updated by its owner.
	 * 
	 * C: 69.2% of sentence coverage, since it has been covered
	 * 18 lines of code of 26 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 6
	 * values in the data of 21 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void professionalRecordEditNegativeTest() {
		ProfessionalRecord professionalRecord, saved;
		int professionalRecordId;
		String company;

		// Data
		company = "Company test";

		super.authenticate("trainer2");

		professionalRecordId = super.getEntityId("professionalRecord1");
		professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
		professionalRecord = this.cloneProfessionalRecord(professionalRecord);

		professionalRecord.setCompany(company);
		saved = this.professionalRecordService.save(professionalRecord);
		this.professionalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getCompany() == company);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Company must not be blank
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 26 lines of code of 26 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 6
	 * values in the data of 21 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void professionalRecordEditNegativeTest2() {
		ProfessionalRecord professionalRecord, saved;
		int professionalRecordId;
		String company;

		// Data
		company = "";

		super.authenticate("trainer1");

		professionalRecordId = super.getEntityId("professionalRecord1");
		professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);
		professionalRecord = this.cloneProfessionalRecord(professionalRecord);

		professionalRecord.setCompany(company);
		saved = this.professionalRecordService.save(professionalRecord);
		this.professionalRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getCompany() == company);
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
	 * 48 lines of code of 48 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test
	public void professionalRecordDeleteTest() {
		ProfessionalRecord professionalRecord, saved;
		int professionalRecordId;

		super.authenticate("trainer1");

		professionalRecordId = super.getEntityId("professionalRecord1");
		professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);

		this.professionalRecordService.delete(professionalRecord);

		super.unauthenticate();

		saved = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, updating,
	 * and DELETING them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Professional Data can only be deleted by its owner.
	 * 
	 * C: 33.3% of sentence coverage, since it has been covered
	 * 16 lines of code of 48 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void professionalRecordDeleteNegativeTest() {
		ProfessionalRecord professionalRecord, saved;
		int professionalRecordId;

		super.authenticate("trainer2");

		professionalRecordId = super.getEntityId("professionalRecord1");
		professionalRecord = this.professionalRecordRepository.findOne(professionalRecordId);

		this.professionalRecordService.delete(professionalRecord);

		super.unauthenticate();

		saved = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.isTrue(saved == null);
	}

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
