
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository --------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Other supporting services -------------------

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;


	// Constructors -------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		this.utilityService.checkEmailActors(actor);

		final Actor result;
		boolean isUpdating;

		isUpdating = this.actorRepository.exists(actor.getId());
		Assert.isTrue(!isUpdating || this.isOwnerAccount(actor));

		result = this.actorRepository.save(actor);
		if (actor.getAddress() != null)
			result.setAddress(actor.getAddress().trim());
		if (actor.getPhoto() != null)
			result.setPhoto(actor.getPhoto().trim());
		result.setPhoneNumber(this.utilityService.getValidPhone(actor.getPhoneNumber()));

		return result;

	}

	public void delete(final Actor actor) {

		// Delete messages
		this.messageService.deleteMessagesFromActor(actor);

		// Delete boxes

		// Delete social profiles
		this.socialProfileService.deleteSocialProfiles(actor);

		this.actorRepository.delete(actor);
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor findOneToDisplayEdit(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result, principal;

		principal = this.findPrincipal();
		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == actorId);

		return result;
	}

	// Other business methods ---------------------

	public Actor findPrincipal() {
		Actor result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();

		result = this.findActorByUserAccount(userAccountId);
		Assert.notNull(result);

		return result;
	}

	private Actor findActorByUserAccount(final int id) {
		Actor result;

		result = this.actorRepository.findActorByUserAccount(id);

		return result;
	}

	private boolean isOwnerAccount(final Actor actor) {
		int principalId;

		principalId = LoginService.getPrincipal().getId();
		return principalId == actor.getUserAccount().getId();
	}

	public void ban(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getIsSuspicious());

		final UserAccount userAccount;

		userAccount = actor.getUserAccount();

		userAccount.setIsBanned(true);

		Assert.isTrue(actor.getUserAccount().getIsBanned());
	}

	public void unBan(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getIsBanned());

		final UserAccount userAccount;

		userAccount = actor.getUserAccount();

		userAccount.setIsBanned(false);

		Assert.isTrue(!actor.getUserAccount().getIsBanned());
	}

	public void markAsSuspicious(final Actor actor, final Boolean bool) {
		actor.setIsSuspicious(bool);
	}

	public void suspiciousProcess() {
		Collection<Actor> all;

		all = this.findAll();

		for (final Actor a : all)
			this.launchSuspiciousProcess(a);
	}

	private void launchSuspiciousProcess(final Actor actor) {
		Double numberSpamMessages;

		numberSpamMessages = this.messageService.numberSpamMessagesSentByActor(actor.getId());

		if (numberSpamMessages >= 1.0)
			this.markAsSuspicious(actor, true);
		else
			this.markAsSuspicious(actor, false);
	}

	public boolean existEmail(final String email) {
		boolean result;
		Actor actor;

		actor = this.actorRepository.findActorByEmail(email);
		result = !(actor == null);

		return result;
	}

	public Collection<Actor> findActorsWithoutPrincipal() {
		Collection<Actor> results;
		Actor principal;

		principal = this.findPrincipal();
		results = this.findAll();
		results.remove(principal);

		return results;
	}

}
