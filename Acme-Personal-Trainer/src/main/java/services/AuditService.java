
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Curriculum;

@Service
@Transactional
public class AuditService {

	// Managed repository ------------------------------------------------

	@Autowired
	private AuditRepository		auditRepository;

	// Other supporting services -----------------------------------------

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private Validator			validator;


	// Constructors ------------------------------------------------------

	public AuditService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	public Audit create(final Curriculum curriculum) {
		Audit result;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();

		result = new Audit();
		result.setCurriculum(curriculum);
		result.setAuditor(principal);
		result.setMoment(this.utilityService.current_moment());

		return result;
	}

	public Audit create(final int curriculumId) {
		Audit result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);
		result = this.create(curriculum);

		return result;
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		this.utilityService.checkURLS(audit.getAttachments());
		this.checkOwner(audit);

		Audit saved;

		saved = this.auditRepository.save(audit);

		return saved;
	}

	public void delete(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(this.auditRepository.exists(audit.getId()));
		this.checkOwnerStored(audit);

		this.auditRepository.delete(audit.getId());
	}

	// Other business methods --------------------------------------------

	public Audit findOneToEditDisplay(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);
		Assert.notNull(result);
		this.checkOwner(result);

		return result;
	}

	public Collection<Audit> findAllByPrincipal() {
		Collection<Audit> result;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		result = this.auditRepository.findAllByAuditorId(principal.getId());
		Assert.notNull(result);

		return result;
	}

	protected Audit findByAuditorIdCurriculumId(final int auditorId, final int curriculumId) {
		Audit result;

		result = this.auditRepository.findByAuditorIdCurriculumId(auditorId, curriculumId);

		return result;
	}

	// Ancillary methods -------------------------------------------------

	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		Audit result, auditStored;

		if (audit.getId() == 0)
			result = this.create(audit.getCurriculum());
		else {
			result = new Audit();
			auditStored = this.auditRepository.findOne(audit.getId());

			result.setId(audit.getId());
			result.setVersion(auditStored.getVersion());
			result.setCurriculum(auditStored.getCurriculum());
			result.setAuditor(auditStored.getAuditor());
			result.setMoment(auditStored.getMoment());
		}

		result.setTitle(audit.getTitle().trim());
		result.setDescription(audit.getDescription().trim());
		result.setAttachments(audit.getAttachments());

		this.validator.validate(result, binding);

		return result;
	}

	private void checkOwner(final Audit audit) {
		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		Assert.isTrue(audit.getAuditor().equals(principal));
	}

	private void checkOwnerStored(final Audit audit) {
		Audit stored;

		stored = this.auditRepository.findOne(audit.getId());
		this.checkOwner(stored);
	}

	protected void deleteAudits(final Curriculum curriculum) {
		Collection<Audit> audits;

		audits = this.auditRepository.findAllByCurriculumId(curriculum.getId());
		Assert.notNull(audits);

		this.auditRepository.deleteInBatch(audits);
	}

	protected void deleteAudits(final Auditor auditor) {
		Collection<Audit> audits;

		audits = this.auditRepository.findAllByAuditorId(auditor.getId());
		Assert.notNull(audits);

		this.auditRepository.deleteInBatch(audits);
	}
}
