
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WorkingOutRepository;
import domain.Category;
import domain.Session;
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

	@Autowired
	private Validator				validator;


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
		result.setSessions(Collections.<Session> emptySet());
		result.setCategories(Collections.<Category> emptySet());

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

		this.workingOutRepository.delete(workingOut);
	}

	public WorkingOut findOne(final int workingOutId) {
		WorkingOut result;

		result = this.workingOutRepository.findOne(workingOutId);
		Assert.notNull(result);

		return result;
	}

	public WorkingOut findOneToPrincipal(final int workingOutId) {
		WorkingOut result;

		result = this.workingOutRepository.findOne(workingOutId);
		this.checkByPrincipal(result);
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

	// Other business methods ---------------------

	public void makeFinal(final WorkingOut workingOut) {
		this.checkByPrincipal(workingOut);

		workingOut.setIsFinalMode(true);
		//TODO
		//		this.messageService.notification_newWorkingOut(workingOut);

	}

	public WorkingOut findOneFinalByPrincipal(final int workingOutId) {
		WorkingOut result;

		result = this.workingOutRepository.findOne(workingOutId);
		this.checkByPrincipal(result);
		Assert.isTrue(result.getIsFinalMode());

		return result;
	}

	public Collection<WorkingOut> findWorkingOutsByTrainer(final Trainer trainer) {
		Collection<WorkingOut> workingOuts;

		workingOuts = this.workingOutRepository.findWorkingOutsByTrainer(trainer.getId());

		return workingOuts;
	}
	public Collection<WorkingOut> findAllVisible() {
		Collection<WorkingOut> workingOuts;

		workingOuts = this.workingOutRepository.findAllVisible();

		return workingOuts;
	}

	public Collection<Session> getSessionsByWorkingOut(final WorkingOut workingOut) {
		Collection<Session> sessions;

		sessions = this.workingOutRepository.getSessionsByWorkingOut(workingOut.getId());

		return sessions;
	}
	public Collection<Category> getCategoriesByWorkingOut(final WorkingOut workingOut) {
		Collection<Category> caregories;

		caregories = this.workingOutRepository.getCategoriesByWorkingOut(workingOut.getId());

		return caregories;
	}
	// Protected methods -----------------------------------------------
	protected String existTicker(final String ticker) {
		String result;

		result = this.workingOutRepository.existTicker(ticker);

		return result;
	}

	// Private methods-----------------------------------------------
	private void checkByPrincipal(final WorkingOut workingOut) {
		Trainer owner;
		Trainer principal;

		owner = workingOut.getTrainer();
		principal = this.trainerService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}

	// Reconstruct ----------------------------------------------
	public WorkingOut reconstruct(final WorkingOut workingOut, final BindingResult binding) {
		WorkingOut result, workingOutStored;

		if (workingOut.getId() != 0) {
			result = new WorkingOut();
			workingOutStored = this.findOne(workingOut.getId());
			result.setId(workingOutStored.getId());
			result.setIsFinalMode(workingOutStored.getIsFinalMode());
			result.setPublishedMoment(workingOutStored.getPublishedMoment());
			result.setTicker(workingOutStored.getTicker());
			result.setVersion(workingOutStored.getVersion());
			result.setTrainer(workingOutStored.getTrainer());
			result.setStartMoment(workingOutStored.getStartMoment());
			result.setEndMoment(workingOutStored.getEndMoment());
			result.setSessions(workingOutStored.getSessions());

		} else
			result = this.create();

		result.setDescription(workingOut.getDescription().trim());
		result.setPrice(workingOut.getPrice());
		result.setCategories(workingOut.getCategories());

		if (workingOut.getCategories() == null)
			result.setCategories(Collections.<Category> emptySet());
		else
			result.setCategories(workingOut.getCategories());

		this.validator.validate(result, binding);

		return result;
	}

}
