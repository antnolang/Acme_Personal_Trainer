
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
import repositories.MiscellaneousRecordRepository;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private MiscellaneousRecordService		miscellaneousRecordService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

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
	 * 34 lines of code of 34 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 3
	 * values in the data of 10 different possible values.
	 */
	@Test
	public void miscellaneousRecordCreateTest() {
		MiscellaneousRecord miscellaneousRecord, savedMiscellaneousRecord;
		Curriculum curriculum;
		int curriculumId, numberMiscData;
		String attachment, title, comments;

		// Data
		attachment = "https://www.attachment1.com";
		title = "Miscellaneous title";
		comments = "Comment text";

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberMiscData = curriculum.getMiscellaneousRecords().size();
		miscellaneousRecord = this.miscellaneousRecordService.create();

		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setComments(comments);

		savedMiscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
		this.miscellaneousRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getMiscellaneousRecords().size() == numberMiscData + 1);
		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(savedMiscellaneousRecord));
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, CREATING, updating,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The miscellaneous data can only be created in one of the curriculum in
	 * which the trainer principal is owner.
	 * 
	 * C: 94.1% of sentence coverage, since it has been covered
	 * 32 lines of code of 34 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 3
	 * values in the data of 10 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void miscellaneousRecordCreateNegativeTest() {
		MiscellaneousRecord miscellaneousRecord, savedMiscellaneousRecord;
		Curriculum curriculum;
		int curriculumId, numberMiscData;
		String attachment, title, comments;

		// Data
		attachment = "https://www.attachment1.com";
		title = "Miscellaneous title";
		comments = "Comment text";

		super.authenticate("trainer2");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberMiscData = curriculum.getMiscellaneousRecords().size();
		miscellaneousRecord = this.miscellaneousRecordService.create();

		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setComments(comments);

		savedMiscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
		this.miscellaneousRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getMiscellaneousRecords().size() == numberMiscData + 1);
		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(savedMiscellaneousRecord));
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
	 * 34 lines of code of 34 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 3
	 * values in the data of 10 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void miscellaneousRecordCreateNegativeTest2() {
		MiscellaneousRecord miscellaneousRecord, savedMiscellaneousRecord;
		Curriculum curriculum;
		int curriculumId, numberMiscData;
		String attachment, title, comments;

		// Data
		attachment = "Esto no es una URL";
		title = "Miscellaneous title";
		comments = "Comment text";

		super.authenticate("trainer1");

		curriculumId = super.getEntityId("curriculum1");
		curriculum = this.curriculumRepository.findOne(curriculumId);
		numberMiscData = curriculum.getMiscellaneousRecords().size();
		miscellaneousRecord = this.miscellaneousRecordService.create();

		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setComments(comments);

		savedMiscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
		this.miscellaneousRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(curriculum.getMiscellaneousRecords().size() == numberMiscData + 1);
		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(savedMiscellaneousRecord));
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
	 * 21 lines of code of 21 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 3
	 * values in the data of 10 different possible values.
	 */
	@Test
	public void miscellaneousRecordEditTest() {
		MiscellaneousRecord miscellaneousRecord, saved;
		int miscellaneousRecordId;
		String title;

		// Data
		title = "Title Edit test";

		super.authenticate("trainer1");

		miscellaneousRecordId = super.getEntityId("miscellaneousRecord1");
		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		miscellaneousRecord = this.cloneMiscellaneousRecord(miscellaneousRecord);

		miscellaneousRecord.setTitle(title);
		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
		this.miscellaneousRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getTitle() == title);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Miscellaneous Data can only be updated by its owner.
	 * 
	 * C: 90.5% of sentence coverage, since it has been covered
	 * 19 lines of code of 21 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 3
	 * values in the data of 10 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void miscellaneousRecordEditNegativeTest() {
		MiscellaneousRecord miscellaneousRecord, saved;
		int miscellaneousRecordId;
		String title;

		// Data
		title = "Title Edit test";

		super.authenticate("trainer2");

		miscellaneousRecordId = super.getEntityId("miscellaneousRecord1");
		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		miscellaneousRecord = this.cloneMiscellaneousRecord(miscellaneousRecord);

		miscellaneousRecord.setTitle(title);
		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
		this.miscellaneousRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getTitle() == title);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, UPDATING,
	 * and deleting them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: Attachment must be a valid URL
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 21 lines of code of 21 possible.
	 * 
	 * D: Approximately 30% of data coverage, since it has been used 3
	 * values in the data of 10 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void miscellaneousRecordEditNegativeTest2() {
		MiscellaneousRecord miscellaneousRecord, saved;
		int miscellaneousRecordId;
		String title, attachment;

		// Data
		title = "Title Edit test";
		attachment = "Esto no es una URL";

		super.authenticate("trainer1");

		miscellaneousRecordId = super.getEntityId("miscellaneousRecord1");
		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		miscellaneousRecord = this.cloneMiscellaneousRecord(miscellaneousRecord);

		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setAttachment(attachment);
		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
		this.miscellaneousRecordRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getTitle() == title);
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
	public void miscellaneousRecordDeleteTest() {
		MiscellaneousRecord miscellaneousRecord, saved;
		int miscellaneousRecordId;

		super.authenticate("trainer1");

		miscellaneousRecordId = super.getEntityId("miscellaneousRecord1");
		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		super.unauthenticate();

		saved = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.isTrue(saved == null);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her records, which includes listing, showing, creating, updating,
	 * and DELETING them. Except personal record which once it is created can not
	 * be deleted.
	 * 
	 * B: The Miscellaneous Data can only be deleted by its owner.
	 * 
	 * C: 32% of sentence coverage, since it has been covered
	 * 16 lines of code of 50 possible.
	 * 
	 * D: 100% of data coverage.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void miscellaneousRecordDeleteNegativeTest() {
		MiscellaneousRecord miscellaneousRecord, saved;
		int miscellaneousRecordId;

		super.authenticate("trainer2");

		miscellaneousRecordId = super.getEntityId("miscellaneousRecord1");
		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		super.unauthenticate();

		saved = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.isTrue(saved == null);
	}

	// Ancillary methods ------------------------------------------------------

	private MiscellaneousRecord cloneMiscellaneousRecord(final MiscellaneousRecord miscellaneousRecord) {
		final MiscellaneousRecord res = new MiscellaneousRecord();

		res.setAttachment(miscellaneousRecord.getAttachment());
		res.setId(miscellaneousRecord.getId());
		res.setTitle(miscellaneousRecord.getTitle());
		res.setComments(miscellaneousRecord.getComments());
		res.setVersion(miscellaneousRecord.getVersion());

		return res;
	}

}
