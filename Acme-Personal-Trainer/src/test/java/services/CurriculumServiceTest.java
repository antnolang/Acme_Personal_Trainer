
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
import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	// Service under testing --------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her curriculum, which includes showing, CREATING them.
	 * 
	 * B: Positive test
	 * 
	 * C: 97.5% of sentence coverage, since it has been covered
	 * 78 lines of code of 80 possible.
	 * 
	 * D: Approximately 26.3% of data coverage, since it has been used 5
	 * values in the data of 19 different possible values.
	 * (See Possible values below.)
	 */
	@Test
	public void curriculumCreateTest() {
		Curriculum curriculum, savedCurriculum;
		PersonalRecord personalRecord, savedPersonalRecord;
		String fullname, photo, linkedinProf, phone, email;

		// Data
		fullname = "Trainer6 trainer"; // Possible values: string, blank string, null, string that doesn't match with fullname's actor principal
		photo = "https://photo.com"; // Possible values: valid url, blank string, null, invalid url
		linkedinProf = "https://www.linkedin.com/antonio"; // Possible values: string, blank string, null, string that doesn't start with https://www.linkedin.com/
		phone = "789654123"; // Possible values: string, blank string, null
		email = "pepe <kililoliki@gmail.com>"; // Possible values: blank string, null, normal email, email with alias

		super.authenticate("trainer6");

		curriculum = this.curriculumService.create();
		personalRecord = curriculum.getPersonalRecord();

		personalRecord.setFullName(fullname);
		personalRecord.setEmail(email);
		personalRecord.setLinkedInProfile(linkedinProf);
		personalRecord.setPhoneNumber(phone);
		personalRecord.setPhoto(photo);

		savedCurriculum = this.curriculumService.save(curriculum);
		this.curriculumRepository.flush();
		savedPersonalRecord = savedCurriculum.getPersonalRecord();

		super.unauthenticate();

		Assert.isTrue(savedCurriculum.getEducationRecords().isEmpty() && savedCurriculum.getMiscellaneousRecords().isEmpty() && savedCurriculum.getEndorserRecords().isEmpty() && savedCurriculum.getProfessionalRecords().isEmpty());
		Assert.isTrue(savedPersonalRecord.getFullName() == fullname);
		Assert.isTrue(savedPersonalRecord.getEmail() == email);
		Assert.isTrue(savedPersonalRecord.getLinkedInProfile() == linkedinProf);
		Assert.isTrue(savedPersonalRecord.getPhoneNumber().equals("+34 " + phone));
		Assert.isTrue(savedPersonalRecord.getPhoto() == photo);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her curriculum, which includes showing, CREATING them
	 * 
	 * B: LinkedIn profile must be a valid URL
	 * 
	 * C: 97.5% of sentence coverage, since it has been covered
	 * 78 lines of code of 80 possible.
	 * 
	 * D: Approximately 26.3% of data coverage, since it has been used 5
	 * values in the data of 19 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void curriculumCreateNegativeTest() {
		Curriculum curriculum, savedCurriculum;
		PersonalRecord personalRecord, savedPersonalRecord;
		String fullname, photo, linkedinProf, phone, email;

		// Data
		fullname = "Trainer6 trainer";
		photo = "https://photo.com";
		linkedinProf = "Esto no es una URL";
		phone = "789654123";
		email = "pepe <kililoliki@gmail.com>";

		super.authenticate("trainer6");

		curriculum = this.curriculumService.create();
		personalRecord = curriculum.getPersonalRecord();

		personalRecord.setFullName(fullname);
		personalRecord.setEmail(email);
		personalRecord.setLinkedInProfile(linkedinProf);
		personalRecord.setPhoneNumber(phone);
		personalRecord.setPhoto(photo);

		savedCurriculum = this.curriculumService.save(curriculum);
		this.curriculumRepository.flush();
		savedPersonalRecord = savedCurriculum.getPersonalRecord();

		super.unauthenticate();

		Assert.isTrue(savedCurriculum.getEducationRecords().isEmpty() && savedCurriculum.getMiscellaneousRecords().isEmpty() && savedCurriculum.getEndorserRecords().isEmpty() && savedCurriculum.getProfessionalRecords().isEmpty());
		Assert.isTrue(savedPersonalRecord.getFullName() == fullname);
		Assert.isTrue(savedPersonalRecord.getEmail() == email);
		Assert.isTrue(savedPersonalRecord.getLinkedInProfile() == linkedinProf);
		Assert.isTrue(savedPersonalRecord.getPhoneNumber().equals("+34 " + phone));
		Assert.isTrue(savedPersonalRecord.getPhoto() == photo);
	}

	/*
	 * A: An actor who is authenticated as a trainer must be able to: Manage his
	 * or her curriculum, which includes showing, CREATING them.
	 * 
	 * B: Fullname must match with the full name of the trainer principal
	 * 
	 * C: 21.2% of sentence coverage, since it has been covered
	 * 17 lines of code of 80 possible.
	 * 
	 * D: Approximately 26.3% of data coverage, since it has been used 5
	 * values in the data of 19 different possible values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void curriculumCreateNegativeTest2() {
		Curriculum curriculum, savedCurriculum;
		PersonalRecord personalRecord, savedPersonalRecord;
		String fullname, photo, linkedinProf, phone, email;

		// Data
		fullname = "Álvaro Gutierrez";
		photo = "https://photo.com";
		linkedinProf = "https://www.linkedin.com/antonio";
		phone = "789654123";
		email = "pepe <kililoliki@gmail.com>";

		super.authenticate("trainer6");

		curriculum = this.curriculumService.create();
		personalRecord = curriculum.getPersonalRecord();

		personalRecord.setFullName(fullname);
		personalRecord.setEmail(email);
		personalRecord.setLinkedInProfile(linkedinProf);
		personalRecord.setPhoneNumber(phone);
		personalRecord.setPhoto(photo);

		savedCurriculum = this.curriculumService.save(curriculum);
		this.curriculumRepository.flush();
		savedPersonalRecord = savedCurriculum.getPersonalRecord();

		super.unauthenticate();

		Assert.isTrue(savedCurriculum.getEducationRecords().isEmpty() && savedCurriculum.getMiscellaneousRecords().isEmpty() && savedCurriculum.getEndorserRecords().isEmpty() && savedCurriculum.getProfessionalRecords().isEmpty());
		Assert.isTrue(savedPersonalRecord.getFullName() == fullname);
		Assert.isTrue(savedPersonalRecord.getEmail() == email);
		Assert.isTrue(savedPersonalRecord.getLinkedInProfile() == linkedinProf);
		Assert.isTrue(savedPersonalRecord.getPhoneNumber().equals("+34 " + phone));
		Assert.isTrue(savedPersonalRecord.getPhoto() == photo);
	}

}
