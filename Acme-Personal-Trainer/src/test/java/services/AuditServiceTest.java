
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import utilities.AbstractTest;
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private AuditService	auditService;

	// Other services and repositories ----------------------------------------

	@Autowired
	private AuditRepository	auditRepository;


	// Tests ------------------------------------------------------------------

	/*
	 * A: An actor who is authenticated as an auditor must be able to
	 * manage his or her audits, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 13 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testPositiveListAudit() {
		Collection<Audit> audits;
		int auditId, numberAudits;
		Audit audit1, audit2;

		super.authenticate("auditor1");

		auditId = super.getEntityId("audit1");
		audit1 = this.auditRepository.findOne(auditId);
		auditId = super.getEntityId("audit2");
		audit2 = this.auditRepository.findOne(auditId);
		numberAudits = 2;

		audits = this.auditService.findAllByPrincipal();

		super.unauthenticate();

		Assert.isTrue(audits.contains(audit1) && audits.contains(audit2));
		Assert.isTrue(audits.size() == numberAudits);
	}

	/*
	 * A: An actor who is authenticated as an auditor must be able to
	 * manage his or her audits, which includes LISTING, showing,
	 * creating, updating and deleting them.
	 * 
	 * B: The actor principal must be authenticated as Auditor
	 * 
	 * C: 76.9% of sentence coverage, since it has been covered
	 * 10 lines of code of 13 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeListAudit() {
		Collection<Audit> audits;
		int auditId, numberAudits;
		Audit audit1, audit2;

		super.authenticate("customer1");

		auditId = super.getEntityId("audit1");
		audit1 = this.auditRepository.findOne(auditId);
		auditId = super.getEntityId("audit2");
		audit2 = this.auditRepository.findOne(auditId);
		numberAudits = 2;

		audits = this.auditService.findAllByPrincipal();

		super.unauthenticate();

		Assert.isTrue(audits.contains(audit1) && audits.contains(audit2));
		Assert.isTrue(audits.size() == numberAudits);
	}

	/*
	 * A: An actor who is authenticated as an auditor must be able to
	 * manage his or her audits, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 14 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testPositiveDisplayAudit() {
		int auditId;
		Audit audit, stored;

		super.authenticate("auditor1");

		auditId = super.getEntityId("audit1");
		stored = this.auditRepository.findOne(auditId);
		audit = this.auditService.findOneToEditDisplay(auditId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(audit));
	}

	/*
	 * A: An actor who is authenticated as an auditor must be able to
	 * manage his or her audits, which includes listing, SHOWING,
	 * creating, updating and deleting them.
	 * 
	 * B: The audit to display must belong to the auditor principal.
	 * 
	 * C: 92.8% of sentence coverage, since it has been covered
	 * 13 lines of code of 14 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDisplayAudit() {
		int auditId;
		Audit audit, stored;

		super.authenticate("auditor2");

		auditId = super.getEntityId("audit1");
		stored = this.auditRepository.findOne(auditId);
		audit = this.auditService.findOneToEditDisplay(auditId);

		super.unauthenticate();

		Assert.isTrue(stored.equals(audit));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 96.3% of sentence coverage, since it has been covered
	 * 52 lines of code of 54 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 4
	 * values in the data of 14 different possible values.
	 */
	@Test
	public void testPositiveCreateAudit() {
		int curriculumId;
		Audit audit, saved, stored;

		super.authenticate("auditor1");

		curriculumId = super.getEntityId("curriculum3");
		audit = this.auditService.create(curriculumId);
		audit.setAttachments("https://url1.com");
		audit.setDescription("Description test");
		audit.setTitle("Title test");
		saved = this.auditService.save(audit);
		this.auditRepository.flush();
		stored = this.auditRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: Every attachment must be a valid URL
	 * 
	 * C: 72.2% of sentence coverage, since it has been covered
	 * 39 lines of code of 54 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 4
	 * values in the data of 14 different possible values.
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testNegativeCreateAudit() {
		int curriculumId;
		Audit audit, saved, stored;

		super.authenticate("auditor1");

		curriculumId = super.getEntityId("curriculum3");
		audit = this.auditService.create(curriculumId);
		audit.setAttachments("https/url1.com");
		audit.setDescription("Description test");
		audit.setTitle("Title test");
		saved = this.auditService.save(audit);
		this.auditRepository.flush();
		stored = this.auditRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * CREATING, updating and deleting them.
	 * 
	 * B: Title must not be blank.
	 * 
	 * C: 96.3% of sentence coverage, since it has been covered
	 * 52 lines of code of 54 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 4
	 * values in the data of 14 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testNegativeCreateAudit2() {
		int curriculumId;
		Audit audit, saved, stored;

		super.authenticate("auditor1");

		curriculumId = super.getEntityId("curriculum3");
		audit = this.auditService.create(curriculumId);
		audit.setAttachments("https://url1.com");
		audit.setDescription("Description test");
		audit.setTitle("");
		saved = this.auditService.save(audit);
		this.auditRepository.flush();
		stored = this.auditRepository.findOne(saved.getId());

		super.unauthenticate();

		Assert.isTrue(stored.equals(saved));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: Positive test
	 * 
	 * C: 93.1% of sentence coverage, since it has been covered
	 * 27 lines of code of 29 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 4
	 * values in the data of 14 different possible values.
	 */
	@Test
	public void testPositiveUpdateAudit() {
		int auditId;
		Audit audit, saved;
		String attachment, text;

		attachment = "http://www.urlTest.com";
		text = "Esto es un test";

		super.authenticate("auditor1");

		auditId = super.getEntityId("audit1");
		audit = this.auditRepository.findOne(auditId);
		audit = this.copy(audit);
		audit.setAttachments(attachment);
		audit.setDescription(text);
		audit.setTitle(text);
		saved = this.auditService.save(audit);
		this.auditRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getAttachments().equals(attachment));
		Assert.isTrue(saved.getTitle().equals(text));
		Assert.isTrue(saved.getDescription().equals(text));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: Title cannot be blank.
	 * 
	 * C: 93.1% of sentence coverage, since it has been covered
	 * 27 lines of code of 29 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 4
	 * values in the data of 14 different possible values.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testNegativeUpdateAudit() {
		int auditId;
		Audit audit, saved;
		String attachment, text;

		attachment = "http://www.urlTest.com";
		text = "Esto es un test";

		super.authenticate("auditor1");

		auditId = super.getEntityId("audit1");
		audit = this.auditRepository.findOne(auditId);
		audit = this.copy(audit);
		audit.setAttachments(attachment);
		audit.setDescription(text);
		audit.setTitle("");
		saved = this.auditService.save(audit);
		this.auditRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getAttachments().equals(attachment));
		Assert.isTrue(saved.getTitle().equals(""));
		Assert.isTrue(saved.getDescription().equals(text));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * creating, UPDATING and deleting them.
	 * 
	 * B: Every attachment must be a valid URL.
	 * 
	 * C: 48.3% of sentence coverage, since it has been covered
	 * 14 lines of code of 29 possible.
	 * 
	 * D: Approximately 28.6% of data coverage, since it has been used 4
	 * values in the data of 14 different possible values.
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testNegativeUpdateAudit2() {
		int auditId;
		Audit audit, saved;
		String attachment, text;

		attachment = "httpurlTest.com";
		text = "Esto es un test";

		super.authenticate("auditor1");

		auditId = super.getEntityId("audit1");
		audit = this.auditRepository.findOne(auditId);
		audit = this.copy(audit);
		audit.setAttachments(attachment);
		audit.setDescription(text);
		audit.setTitle("");
		saved = this.auditService.save(audit);
		this.auditRepository.flush();

		super.unauthenticate();

		Assert.isTrue(saved.getAttachments().equals(attachment));
		Assert.isTrue(saved.getTitle().equals(""));
		Assert.isTrue(saved.getDescription().equals(text));
	}

	/*
	 * A: An actor who is authenticated as a provider must be able to
	 * manage his or her audits, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 
	 * B: Positive test
	 * 
	 * C: 100% of sentence coverage, since it has been covered
	 * 15 lines of code of 15 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test
	public void testPositiveDeleteAudit() {
		int auditId;
		Audit audit;

		super.authenticate("auditor1");

		auditId = super.getEntityId("audit1");
		audit = this.auditRepository.findOne(auditId);
		this.auditService.delete(audit);

		super.unauthenticate();

		audit = this.auditRepository.findOne(auditId);
		Assert.isTrue(audit == null);
	}

	/*
	 * A: An actor who is authenticated as an auditor must be able to
	 * manage his or her audits, which includes listing, showing,
	 * creating, updating and DELETING them.
	 * 
	 * B: The audit to delete must belong to the provider principal.
	 * 
	 * C: 93.3% of sentence coverage, since it has been covered
	 * 14 lines of code of 15 possible.
	 * 
	 * D: 100% of data coverage
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDeleteAudit() {
		int auditId;
		Audit audit;

		super.authenticate("auditor2");

		auditId = super.getEntityId("audit1");
		audit = this.auditRepository.findOne(auditId);
		this.auditService.delete(audit);

		super.unauthenticate();

		audit = this.auditRepository.findOne(auditId);
		Assert.isTrue(audit == null);
	}

	// Ancillary methods ------------------------------------------------------

	private Audit copy(final Audit audit) {
		final Audit result = new Audit();

		result.setAttachments(audit.getAttachments());
		result.setAuditor(audit.getAuditor());
		result.setCurriculum(audit.getCurriculum());
		result.setDescription(audit.getDescription());
		result.setId(audit.getId());
		result.setMoment(audit.getMoment());
		result.setTitle(audit.getTitle());
		result.setVersion(audit.getVersion());

		return result;
	}
}
