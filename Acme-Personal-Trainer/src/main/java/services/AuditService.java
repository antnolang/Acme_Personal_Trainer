
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Curriculum;

@Service
@Transactional
public class AuditService {

	// Managed repository ------------------------------------------------

	@Autowired
	private AuditRepository	auditRepository;


	// Other supporting services -----------------------------------------

	// Constructors ------------------------------------------------------

	public AuditService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	// Other business methods --------------------------------------------

	// Ancillary methods -------------------------------------------------

	protected void deleteAudits(final Curriculum curriculum) {
		Collection<Audit> audits;

		audits = this.auditRepository.findAllByCurriculumId(curriculum.getId());
		Assert.notNull(audits);

		this.auditRepository.deleteInBatch(audits);
	}

	protected void deleteAudits(final Auditor auditor) {
		Collection<Audit> audits;

		audits = this.auditRepository.findByAuditorId(auditor.getId());
		Assert.notNull(audits);

		this.auditRepository.deleteInBatch(audits);
	}
}
