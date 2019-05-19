
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// @Autowired
	// private AuditService auditService;

	// Other services and repositories ----------------------------------------

	// @Autowired
	// private AuditRepository auditRepository;

	// Tests ------------------------------------------------------------------

	// /* TODO: Test funcionales y análisis Audit
	// * A: An actor who is authenticated as an auditor must be able to
	// * manage his or her audits, which includes LISTING, showing,
	// * creating, updating and deleting them.
	// *
	// * B: Positive test
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test
	// public void testPositiveListAudit() {
	// Collection<Audit> audits;
	// int auditId, numberAudits;
	// Audit audit1, audit2;
	//
	// super.authenticate("auditor1");
	//
	// auditId = super.getEntityId("audit1");
	// audit1 = this.auditRepository.findOne(auditId);
	// auditId = super.getEntityId("audit2");
	// audit2 = this.auditRepository.findOne(auditId);
	// numberAudits = 2;
	//
	// audits = this.auditService.findAllByPrincipal();
	//
	// super.unauthenticate();
	//
	// Assert.isTrue(audits.contains(audit1) && audits.contains(audit2));
	// Assert.isTrue(audits.size() == numberAudits);
	// }
	//
	// /*
	// * A: An actor who is authenticated as an auditor must be able to
	// * manage his or her audits, which includes LISTING, showing,
	// * creating, updating and deleting them.
	// *
	// * B: The actor principal must be authenticated as Auditor
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test(expected = IllegalArgumentException.class)
	// public void testNegativeListAudit() {
	// Collection<Audit> audits;
	// int auditId, numberAudits;
	// Audit audit1, audit2;
	//
	// super.authenticate("customer1");
	//
	// auditId = super.getEntityId("audit1");
	// audit1 = this.auditRepository.findOne(auditId);
	// auditId = super.getEntityId("audit2");
	// audit2 = this.auditRepository.findOne(auditId);
	// numberAudits = 2;
	//
	// audits = this.auditService.findAllByPrincipal();
	//
	// super.unauthenticate();
	//
	// Assert.isTrue(audits.contains(audit1) && audits.contains(audit2));
	// Assert.isTrue(audits.size() == numberAudits);
	// }
	//
	// /*
	// * A: An actor who is authenticated as an auditor must be able to
	// * manage his or her audits, which includes listing, SHOWING,
	// * creating, updating and deleting them.
	// *
	// * B: Positive test
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test
	// public void testPositiveDisplayAudit() {
	// int auditId;
	// Audit audit, stored;
	//
	// super.authenticate("auditor1");
	//
	// auditId = super.getEntityId("audit1");
	// stored = this.auditRepository.findOne(auditId);
	// audit = this.auditService.findOneToEditDisplay(auditId);
	//
	// super.unauthenticate();
	//
	// Assert.isTrue(stored.equals(audit));
	// }
	//
	// /*
	// * A: An actor who is authenticated as an auditor must be able to
	// * manage his or her audits, which includes listing, SHOWING,
	// * creating, updating and deleting them.
	// *
	// * B: The audit to display must belong to the auditor principal.
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test(expected = IllegalArgumentException.class)
	// public void testNegativeDisplayAudit() {
	// int auditId;
	// Audit audit, stored;
	//
	// super.authenticate("auditor2");
	//
	// auditId = super.getEntityId("audit1");
	// stored = this.auditRepository.findOne(auditId);
	// audit = this.auditService.findOneToEditDisplay(auditId);
	//
	// super.unauthenticate();
	//
	// Assert.isTrue(stored.equals(audit));
	// }
	//
	// /*
	// * A: An actor who is authenticated as a provider must be able to
	// * manage his or her audits, which includes listing, showing,
	// * CREATING, updating and deleting them.
	// *
	// * B: Positive test
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test
	// public void testPositiveCreateAudit() {
	// int curriculumId;
	// Audit audit, saved, stored;
	//
	// super.authenticate("auditor1");
	//
	// curriculumId = super.getEntityId("curriculum1");
	// audit = this.auditService.create(curriculumId);
	// audit.setAttachments("https://url1.com");
	// audit.setDescription("Description test");
	// audit.setTitle("Title test");
	// saved = this.auditService.save(audit);
	// stored = this.auditRepository.findOne(saved.getId());
	//
	// super.unauthenticate();
	//
	// Assert.isTrue(stored.equals(saved));
	// }
	//
	// /*
	// * A: An actor who is authenticated as a provider must be able to
	// * manage his or her audits, which includes listing, showing,
	// * CREATING, updating and deleting them.
	// *
	// * B: Every attachment must be a valid URL
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test(expected = DataIntegrityViolationException.class)
	// public void testNegativeCreateAudit() {
	//	 int curriculumId;
	//	 Audit audit, saved, stored;
	//
	//	 super.authenticate("auditor1");
	//
	//	 curriculumId = super.getEntityId("curriculum1");
	//	 audit = this.auditService.create(curriculumId);
	//	 audit.setAttachments("https/url1.com");
	//	 audit.setDescription("Description test");
	//	 audit.setTitle("Title test");
	//	 saved = this.auditService.save(audit);
	//	 stored = this.auditRepository.findOne(saved.getId());
	//
	//	 super.unauthenticate();
	//
	//	 Assert.isTrue(stored.equals(saved));
	// }
	//
	// /*
	// * A: An actor who is authenticated as a provider must be able to
	// * manage his or her audits, which includes listing, showing,
	// * creating, UPDATING and deleting them.
	// *
	// * B: Positive test
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test
	// public void testPositiveUpdateAudit() {
	// int auditId;
	// Audit audit, saved;
	// String attachment, text;
	//
	// attachment = "http://www.urlTest.com";
	// text = "Esto es un test";
	//
	// super.authenticate("auditor1");
	//
	// auditId = super.getEntityId("audit1");
	// audit = this.auditRepository.findOne(auditId);
	// audit = this.copy(audit);
	// audit.setAttachments(attachment);
	// audit.setDescription(text);
	// audit.setTitle(text);
	// saved = this.auditService.save(audit);
	// this.auditRepository.flush();
	//
	// super.unauthenticate();
	//
	// Assert.isTrue(saved.getAttachments().equals(attachment));
	// Assert.isTrue(saved.getTitle().equals(text));
	// Assert.isTrue(saved.getDescription().equals(text));
	// }
	//
	// /*
	// * A: An actor who is authenticated as a provider must be able to
	// * manage his or her audits, which includes listing, showing,
	// * creating, UPDATING and deleting them.
	// *
	// * B: Title cannot be blank.
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test(expected = ConstraintViolationException.class)
	// public void testNegativeUpdateAudit() {
	//	 int auditId;
	//	 Audit audit, saved;
	//	 String attachment, text;
	//
	//	 attachment = "http://www.urlTest.com";
	//	 text = "Esto es un test";
	//
	//	 super.authenticate("auditor1");
	//
	//	 auditId = super.getEntityId("audit1");
	//	 audit = this.auditRepository.findOne(auditId);
	//	 audit = this.copy(audit);
	//	 audit.setAttachments(attachment);
	//	 audit.setDescription(text);
	//	 audit.setTitle("");
	//	 saved = this.auditService.save(audit);
	//	 this.auditRepository.flush();
	//
	//	 super.unauthenticate();
	//
	//	 Assert.isTrue(saved.getAttachments().equals(attachment));
	//	 Assert.isTrue(saved.getTitle().equals(""));
	//	 Assert.isTrue(saved.getDescription().equals(text));
	// }
	//
	// /*
	// * A: An actor who is authenticated as a provider must be able to
	// * manage his or her audits, which includes listing, showing,
	// * creating, updating and DELETING them.
	// *
	// * B: Positive test
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test
	// public void testPositiveDeleteAudit() {
	// int auditId;
	// Audit audit;
	//
	// super.authenticate("auditor1");
	//
	// auditId = super.getEntityId("audit1");
	// audit = this.auditRepository.findOne(auditId);
	// this.auditService.delete(audit);
	//
	// super.unauthenticate();
	//
	// audit = this.auditRepository.findOne(auditId);
	// Assert.isTrue(audit == null);
	// }
	//
	// /*
	// * A: An actor who is authenticated as an auditor must be able to
	// * manage his or her audits, which includes listing, showing,
	// * creating, updating and DELETING them.
	// *
	// * B: The audit to delete must belong to the provider principal.
	// *
	// * C: x% of sentence coverage, since it has been covered
	// * x lines of code of x possible.
	// *
	// * D: Approximately X% of data coverage, since it has been used x
	// * values in the data of x different possible values.
	// */
	// @Test(expected = IllegalArgumentException.class)
	// public void testNegativeDeleteAudit() {
	// int auditId;
	// Audit audit;
	//
	// super.authenticate("auditor2");
	//
	// auditId = super.getEntityId("audit1");
	// audit = this.auditRepository.findOne(auditId);
	// this.auditService.delete(audit);
	//
	// super.unauthenticate();
	//
	// audit = this.auditRepository.findOne(auditId);
	// Assert.isTrue(audit == null);
	// }

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
