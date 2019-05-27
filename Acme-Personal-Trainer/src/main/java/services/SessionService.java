
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

		sessionsWO = workingOut.getSessions();

		Assert.notNull(session);
		Assert.isTrue(!workingOut.getIsFinalMode());
		this.workingOutService.checkByPrincipal(workingOut);

		if (session.getId() == 0) {
			Assert.isTrue(session.getEndMoment().after(session.getStartMoment()), "Start moment before end moment");
			Assert.isTrue(session.getStartMoment().after(this.utilityService.current_moment()), "Start moment in the future");
			Assert.isTrue(sessionsWO.contains(workingOut));
			this.workingOutService.updateMomentWorkingOut(workingOut, session);

			result = this.sessionRepository.save(session);

			sessionsWO.add(result);
		} else {
			Assert.isTrue(workingOut.equals(this.workingOutService.findBySession(session.getId())));
			result = this.sessionRepository.save(session);
		}

		return result;
	}

	public Session findOne(final int sessionId) {
		Session result;

		result = this.sessionRepository.findOne(sessionId);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Session session) {
		WorkingOut workingOutSession;

		workingOutSession = this.workingOutService.findBySession(session.getId());

		this.workingOutService.checkByPrincipal(workingOutSession);
		Assert.isTrue(!workingOutSession.getIsFinalMode());
		Assert.notNull(session);
		Assert.isTrue(this.sessionRepository.exists(session.getId()));
		this.workingOutService.updateDeleteSession(session, workingOutSession);
		this.sessionRepository.delete(session);
	}

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

		result.setAddress(session.getAddress().trim());
		result.setDescription(session.getDescription().trim());
		result.setTitle(session.getTitle().trim());

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
