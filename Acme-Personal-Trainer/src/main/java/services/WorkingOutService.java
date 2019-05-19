
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WorkingOutRepository;
import domain.Trainer;
import domain.WorkingOut;

@Service
@Transactional
public class WorkingOutService {

	// Managed repository ---------------------------------------------

	@Autowired
	private WorkingOutRepository	workingOutRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private TrainerService			trainerService;


	//Constructor ----------------------------------------------------

	private WorkingOut findOne(final int workingOutId) {
		WorkingOut result;

		result = this.workingOutRepository.findOne(workingOutId);
		Assert.notNull(result);

		return result;
	}

	public WorkingOut findOneToDisplay(final int workingOutId) {
		WorkingOut result;

		result = this.findOne(workingOutId);
		Assert.isTrue(result.getIsFinalMode());

		return result;
	}

	// Reconstruct ----------------------------------------------

	// Other business methods ---------------------

	public WorkingOut findOneFinalByPrincipal(final int workingOutId) {
		WorkingOut result;

		result = this.workingOutRepository.findOne(workingOutId);
		this.checkByPrincipal(result);
		Assert.isTrue(result.getIsFinalMode());

		return result;
	}

	// Private methods-----------------------------------------------
	private void checkByPrincipal(final WorkingOut position) {
		Trainer owner;
		Trainer principal;

		owner = position.getTrainer();
		principal = this.trainerService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}
}
