
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	// Tests ------------------------------------------------------------------

	//	/* TODO: Test funcionales y análisis MiscellaneousRecord
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
	//	public void miscellaneousRecordCreateTest() {
	//		MiscellaneousRecord miscellaneousRecord, savedMiscellaneousRecord;
	//		Curriculum curriculum;
	//		int curriculumId, numberMiscData;
	//		String attachment, title, comments;
	//
	//		// Data
	//		attachment = "https://www.attachment1.com\rhttps://www.attachments2.com\rhttps://www.attachment3.com";
	//		title = "Miscellaneous title";
	//		comments = "Comment text";
	//
	//		super.authenticate("trainer8");
	//
	//		curriculumId = super.getEntityId("curriculum81");
	//		curriculum = this.curriculumRepository.findOne(curriculumId);
	//		numberMiscData = curriculum.getMiscellaneousRecords().size();
	//		miscellaneousRecord = this.miscellaneousRecordService.create();
	//
	//		miscellaneousRecord.setAttachment(attachment);
	//		miscellaneousRecord.setTitle(title);
	//		miscellaneousRecord.setComments(comments);
	//
	//		savedMiscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(curriculum.getMiscellaneousRecords().size() == numberMiscData + 1);
	//		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(savedMiscellaneousRecord));
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, CREATING, updating,
	//	 * and deleting them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The miscellaneous data can only be created in one of the curriculum in
	//	 * which the trainer principal is owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void miscellaneousRecordCreateNegativeTest() {
	//		MiscellaneousRecord miscellaneousRecord, savedMiscellaneousRecord;
	//		Curriculum curriculum;
	//		int curriculumId, numberMiscData;
	//		String attachment, title, comments;
	//
	//		// Data
	//		attachment = "https://www.attachment1.com\rhttps://www.attachments2.com\rhttps://www.attachment3.com";
	//		title = "Miscellaneous title";
	//		comments = "Comment text";
	//
	//		super.authenticate("trainer9");
	//
	//		curriculumId = super.getEntityId("curriculum81");
	//		curriculum = this.curriculumRepository.findOne(curriculumId);
	//		numberMiscData = curriculum.getMiscellaneousRecords().size();
	//		miscellaneousRecord = this.miscellaneousRecordService.create();
	//
	//		miscellaneousRecord.setAttachment(attachment);
	//		miscellaneousRecord.setTitle(title);
	//		miscellaneousRecord.setComments(comments);
	//
	//		savedMiscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(curriculum.getMiscellaneousRecords().size() == numberMiscData + 1);
	//		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(savedMiscellaneousRecord));
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
	//	public void miscellaneousRecordEditTest() {
	//		MiscellaneousRecord miscellaneousRecord, saved;
	//		int miscellaneousRecordId;
	//		String title;
	//
	//		// Data
	//		title = "Title Edit test";
	//
	//		super.authenticate("trainer7");
	//
	//		miscellaneousRecordId = super.getEntityId("miscellaneousRecord71");
	//		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	//		miscellaneousRecord = this.cloneMiscellaneousRecord(miscellaneousRecord);
	//
	//		miscellaneousRecord.setTitle(title);
	//		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(saved.getTitle() == title);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, UPDATING,
	//	 * and deleting them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The Miscellaneous Data can only be updated by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void miscellaneousRecordEditNegativeTest() {
	//		MiscellaneousRecord miscellaneousRecord, saved;
	//		int miscellaneousRecordId;
	//		String title;
	//
	//		// Data
	//		title = "Title Edit test";
	//
	//		super.authenticate("trainer9");
	//
	//		miscellaneousRecordId = super.getEntityId("miscellaneousRecord71");
	//		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	//		miscellaneousRecord = this.cloneMiscellaneousRecord(miscellaneousRecord);
	//
	//		miscellaneousRecord.setTitle(title);
	//		saved = this.miscellaneousRecordService.save(miscellaneousRecord);
	//
	//		super.unauthenticate();
	//
	//		Assert.isTrue(saved.getTitle() == title);
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
	//	public void miscellaneousRecordDeleteTest() {
	//		MiscellaneousRecord miscellaneousRecord, saved;
	//		int miscellaneousRecordId;
	//
	//		super.authenticate("trainer7");
	//
	//		miscellaneousRecordId = super.getEntityId("miscellaneousRecord71");
	//		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	//
	//		this.miscellaneousRecordService.delete(miscellaneousRecord);
	//
	//		super.unauthenticate();
	//
	//		saved = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	//		Assert.isTrue(saved == null);
	//	}
	//
	//	/*
	//	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	//	 * or her records, which includes listing, showing, creating, updating,
	//	 * and DELETING them. Except personal record which once it is created can not
	//	 * be deleted.
	//	 * 
	//	 * B: The Miscellaneous Data can only be deleted by its owner.
	//	 * 
	//	 * C: x% of sentence coverage, since it has been covered
	//	 * x lines of code of x possible.
	//	 * 
	//	 * D: Approximately X% of data coverage, since it has been used x
	//	 * values in the data of x different possible values.
	//	 */
	//	@Test(expected = IllegalArgumentException.class)
	//	public void miscellaneousRecordDeleteNegativeTest() {
	//		MiscellaneousRecord miscellaneousRecord, saved;
	//		int miscellaneousRecordId;
	//
	//		super.authenticate("trainer9");
	//
	//		miscellaneousRecordId = super.getEntityId("miscellaneousRecord71");
	//		miscellaneousRecord = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	//
	//		this.miscellaneousRecordService.delete(miscellaneousRecord);
	//
	//		super.unauthenticate();
	//
	//		saved = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	//		Assert.isTrue(saved == null);
	//	}

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
