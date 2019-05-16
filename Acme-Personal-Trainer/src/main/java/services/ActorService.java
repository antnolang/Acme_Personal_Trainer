
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

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
	private Validator				validator;

	@Autowired
	private SocialProfileService	socialProfileService;


	// Constructors -------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		this.utilityService.checkEmailActors(actor);
		//Assert.isTrue(!this.utilityService.checkIsExpired(actor.getCreditCard()), "Expired credit card");

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
		//this.messageService.deleteMessages(actor);

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
		//Assert.isTrue(actor.getIsSpammer());

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

	public void markAsSpammer(final Actor actor, final Boolean bool) {
		//actor.setIsSpammer(bool);
	}

	public void spammerProcess() {
		Collection<Actor> all;

		all = this.findAll();

		//		for (final Actor a : all)
		//			this.launchSpammerProcess(a);
	}

	//	private void launchSpammerProcess(final Actor actor) {
	//		Double percentage, numberMessages, numberSpamMessages;
	//		Boolean value;
	//
	//		numberMessages = this.messageService.numberMessagesSentByActor(actor.getId());
	//		numberSpamMessages = this.messageService.numberSpamMessagesSentByActor(actor.getId());
	//
	//		percentage = numberSpamMessages / numberMessages;
	//
	//		value = percentage >= 0.1;
	//
	//		this.markAsSpammer(actor, value);
	//	}

	public boolean existEmail(final String email) {
		boolean result;
		Actor actor;

		actor = this.actorRepository.findActorByEmail(email);
		result = !(actor == null);

		return result;
	}

}
