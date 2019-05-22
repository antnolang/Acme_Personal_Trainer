
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SessionRepository;
import domain.Session;
import domain.WorkingOut;

@Service
@Transactional
public class SessionService {

	// Managed repository ---------------------------------------------

	@Autowired
	private SessionRepository	sessionRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private Validator			validator;

	@Autowired
	private UtilityService		utilityService;


	//Constructor ----------------------------------------------------

	public SessionService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Session create() {
		Session result;

		result = new Session();

		return result;
	}

	public Session save(final Session session, final WorkingOut workingOut) {
		Session result;
		Collection<Session> sessionsWO;

		result = new Session();
		sessionsWO = workingOut.getSessions();

		Assert.isTrue(!workingOut.getIsFinalMode());
		this.workingOutService.checkByPrincipal(workingOut);
		Assert.isTrue(!session.getEndMoment().before(session.getStartMoment()));
		Assert.isTrue(session.getStartMoment().after(this.utilityService.current_moment()));
		this.workingOutService.updateMomentWorkingOut(workingOut, session);

		if (session.getId() == 0)
			sessionsWO.add(session);

		return result;
	}

	public Session findOne(final int sessionId) {
		Session result;

		result = this.sessionRepository.findOne(sessionId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods ---------------------

	// Protected methods -----------------------------------------------

	// Private methods-----------------------------------------------

	// Reconstruct ----------------------------------------------

	public Session reconstruct(final Session session, final BindingResult binding) {
		Session result, sessionStored;

		if (session.getId() != 0) {
			result = new Session();
			sessionStored = this.findOne(session.getId());
			result.setId(sessionStored.getId());
			result.setStartMoment(sessionStored.getStartMoment());
			result.setEndMoment(sessionStored.getEndMoment());
			result.setVersion(sessionStored.getVersion());

		} else {
			result = this.create();
			result.setStartMoment(session.getStartMoment());
			result.setEndMoment(session.getEndMoment());

		}

		result.setAddress(session.getAddress());
		result.setDescription(session.getDescription());
		result.setTitle(session.getTitle());

		this.validator.validate(result, binding);

		return result;
	}

	public Session findOneToEdit(final int sessionId) {
		Session res;
		WorkingOut workingOut;

		res = this.sessionRepository.findOne(sessionId);
		workingOut = this.workingOutService.findBySession(sessionId);
		Assert.isTrue(!workingOut.getIsFinalMode());
		this.workingOutService.checkByPrincipal(workingOut);

		return res;
	}

}
