
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

	@Autowired
	private UtilityService			utilityService;


	//Constructor ----------------------------------------------------


	public WorkingOutService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public WorkingOut create() {
		WorkingOut result;
		Trainer trainer;

		result = new WorkingOut();
		trainer = this.trainerService.findByPrincipal();

		result.setTicker("000000-XXXXXX");
		result.setTrainer(trainer);

		return result;
	}

	public WorkingOut save(final WorkingOut workingOut) {
		Assert.notNull(workingOut);
		this.checkByPrincipal(workingOut);
		Assert.isTrue(!workingOut.getIsFinalMode());
		workingOut.setPublishedMoment(this.utilityService.current_moment());
		Assert.isTrue(workingOut.getEndMoment().after(workingOut.getStartMoment()));
		Assert.isTrue(workingOut.getStartMoment().after(workingOut.getPublishedMoment()));
		workingOut.setTicker(this.utilityService.generateValidTicker());

		final WorkingOut result;

		result = this.workingOutRepository.save(workingOut);

		return result;
	}

	public void delete(final WorkingOut workingOut) {
		Assert.notNull(workingOut);
		Assert.isTrue(this.workingOutRepository.exists(workingOut.getId()));
		this.checkByPrincipal(workingOut);
		Assert.isTrue(!workingOut.getIsFinalMode());

		//TODO
		//		deleteApplicationsToWorkingOut(workingOut);
		//		deleteFinderToWorkingOut(workingOut);

		this.workingOutRepository.delete(workingOut);
	}


	protected WorkingOut findOne(final int workingOutId) {
		WorkingOut result;

		result = this.workingOutRepository.findOne(workingOutId);
		Assert.notNull(result);

		return result;
	}

	public WorkingOut findOneToEditDelete(final int workingOutId) {
		WorkingOut result;

		result = this.findOne(workingOutId);
		Assert.isTrue(!result.getIsFinalMode());
		this.checkByPrincipal(result);

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

	// Protected methods -----------------------------------------------
	protected String existTicker(final String ticker) {
		String result;

		result = this.workingOutRepository.existTicker(ticker);

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
