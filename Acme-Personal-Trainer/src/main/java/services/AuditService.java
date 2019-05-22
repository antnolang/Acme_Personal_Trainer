
package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Curriculum;

@Service
@Transactional
public class AuditService {

	// Managed repository ------------------------------------------------

	// Other supporting services -----------------------------------------

	// Constructors ------------------------------------------------------

	public AuditService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------

	// Other business methods --------------------------------------------

	// Ancillary methods -------------------------------------------------

	protected void deleteAudits(final Curriculum curriculum) {

	}

}
