
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WorkingOutRepository;
import domain.Category;
import domain.Finder;
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
		Assert.isTrue(this.checkAtLeastOneSession(workingOut));

		workingOut.setIsFinalMode(true);
		workingOut.setPublishedMoment(this.utilityService.current_moment());
		Assert.isTrue(workingOut.getEndMoment().after(workingOut.getStartMoment()));
		Assert.isTrue(workingOut.getStartMoment().after(workingOut.getPublishedMoment()));
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

		workingOuts = this.workingOutRepository.findFinalWorkingOutsByTrainer(trainer.getId());

		return workingOuts;
	}
	public Collection<WorkingOut> findWorkingOutsByPrincipal() {
		Collection<WorkingOut> workingOuts;
		Trainer trainer;

		trainer = this.trainerService.findByPrincipal();
		workingOuts = this.workingOutRepository.findAllWorkingOutsByTrainer(trainer.getId());

		return workingOuts;
	}
	public Collection<WorkingOut> findAllVisible() {
		Collection<WorkingOut> workingOuts;
		Date now;

		now = this.utilityService.current_moment();
		workingOuts = this.workingOutRepository.findAllVisible(now);

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

	// Requirement 4.2: The average, the minimum, the maximum, and the standard deviation of the number of applications per working-out.
	public Double[] findDataNumberApplicationPerWorkingOut() {
		Double[] results;

		results = this.workingOutRepository.findDataNumberApplicationPerWorkingOut();

		return results;
	}

	// Requirement 4.3: The average, the minimum, the maximum, and the standard deviation of the maximum price of the working-outs.
	public Double[] findDataPricePerWorkingOut() {
		Double[] results;

		results = this.workingOutRepository.findDataPricePerWorkingOut();

		return results;
	}

	// Requirement 11.4.1
	public Double[] findDataNumberWorkingOutPerTrainer() {
		Double[] result;

		result = this.workingOutRepository.findDataNumberWorkingOutPerTrainer();
		Assert.notNull(result);

		return result;
	}

	public void deleteByPrincipal() {
		Trainer trainer;
		Collection<WorkingOut> workingOuts;

		trainer = this.trainerService.findByPrincipal();
		workingOuts = this.workingOutRepository.findAllWorkingOutsByTrainer(trainer.getId());

		this.workingOutRepository.delete(workingOuts);
	}

	// Protected methods -----------------------------------------------
	protected String existTicker(final String ticker) {
		String result;

		result = this.workingOutRepository.existTicker(ticker);

		return result;
	}
	protected void checkByPrincipal(final WorkingOut workingOut) {
		Trainer owner;
		Trainer principal;

		owner = workingOut.getTrainer();
		principal = this.trainerService.findByPrincipal();

		Assert.isTrue(owner.equals(principal));
	}

	protected void updateMomentWorkingOut(final WorkingOut workingOut, final Session session) {
		List<Session> sessionsOrdered;
		int sizeSessions;
		Session lastSession;

		sessionsOrdered = this.workingOutRepository.getSssionsOrdered(workingOut.getId());
		sizeSessions = sessionsOrdered.size();

		if (sizeSessions == 0) {
			workingOut.setStartMoment(session.getStartMoment());
			workingOut.setEndMoment(session.getEndMoment());
		} else if (sizeSessions != 0) {
			lastSession = sessionsOrdered.get(sizeSessions - 1);
			Assert.isTrue(!lastSession.getEndMoment().after(session.getStartMoment()), "End moment last session before star moment");
			workingOut.setEndMoment(session.getEndMoment());
		}
	}

	protected void searchWorkingOutFinder(final Finder finder, final Pageable pageable) {
		Page<WorkingOut> workingOuts;

		workingOuts = this.workingOutRepository.searchWorkingOutFinder(finder.getKeyword(), finder.getCategory(), finder.getStartDate(), finder.getEndDate(), finder.getStartPrice(), finder.getEndPrice(), pageable);
		Assert.notNull(workingOuts);

		finder.setWorkingOuts(workingOuts.getContent());
		finder.setUpdatedMoment(this.utilityService.current_moment());
	}

	// Private methods-----------------------------------------------
	private boolean checkAtLeastOneSession(final WorkingOut workingOut) {
		boolean res;
		Collection<Session> sessions;

		sessions = workingOut.getSessions();
		res = sessions.size() >= 1;

		return res;
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

	public WorkingOut findOneToCreateSession(final Integer workingOutId) {
		WorkingOut res;

		res = this.findOne(workingOutId);
		Assert.isTrue(!res.getIsFinalMode());
		this.checkByPrincipal(res);

		return res;
	}

	public WorkingOut findBySession(final int sessionId) {
		WorkingOut res;

		res = this.workingOutRepository.findBySession(sessionId);

		return res;
	}

	public void findToCreateSession(final int workingOutId) {
		WorkingOut workingOut;

		workingOut = this.findOne(workingOutId);
		Assert.isTrue(!workingOut.getIsFinalMode());
		this.checkByPrincipal(workingOut);

	}

}
