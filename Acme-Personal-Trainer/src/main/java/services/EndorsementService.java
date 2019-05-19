
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	// Managed Repository

	@Autowired
	private EndorsementRepository	endorsementRepository;


	// Constructor

	public EndorsementService() {
		super();
	}

	// Other business methods

	protected Collection<Endorsement> findReceivedEndorsementsByTrainer(final int trainerId) {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findReceivedEndorsementsByTrainer(trainerId);

		return result;
	}

}
