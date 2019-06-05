
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public SocialProfileService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SocialProfile findOne(final int socialProfileId) {
		SocialProfile result;

		result = this.socialProfileRepository.findOne(socialProfileId);
		Assert.notNull(result);

		return result;
	}

	public SocialProfile findOneDisplay(final int socialProfileId) {
		SocialProfile result;
		Actor actor;

		result = this.findOne(socialProfileId);

		actor = result.getActor();
		if (actor.getUserAccount().getAuthorities().toString().equals("[ADMIN]"))
			this.checkByPrincipal(result);

		return result;
	}

	public SocialProfile findOneToEdit(final int socialProfileId) {
		SocialProfile result;

		result = this.findOne(socialProfileId);
		this.checkByPrincipal(result);

		return result;
	}

	protected Collection<SocialProfile> findAll() {
		Collection<SocialProfile> result;

		result = this.socialProfileRepository.findAll();

		return result;
	}

	public SocialProfile create() {
		SocialProfile result;
		final Actor actor;

		actor = this.actorService.findPrincipal();

		result = new SocialProfile();
		result.setActor(actor);

		return result;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		this.checkByPrincipal(socialProfile);

		SocialProfile result;

		result = this.socialProfileRepository.save(socialProfile);

		return result;
	}

	public void delete(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		Assert.isTrue(this.socialProfileRepository.exists(socialProfile.getId()));
		this.checkByPrincipal(socialProfile);

		this.socialProfileRepository.delete(socialProfile);
	}


	// Other business methods -------------------------------------------------

	@Autowired
	private Validator	validator;


	public SocialProfile reconstruct(final SocialProfile socialProfile, final BindingResult binding) {
		SocialProfile result, stored_socialProfile;

		if (socialProfile.getId() == 0)
			result = this.create();
		else {
			stored_socialProfile = this.findOne(socialProfile.getId());

			result = new SocialProfile();
			result.setId(stored_socialProfile.getId());
			result.setVersion(stored_socialProfile.getVersion());
			result.setActor(stored_socialProfile.getActor());
		}

		result.setNick(socialProfile.getNick().trim());
		result.setSocialNetwork(socialProfile.getSocialNetwork().trim());
		result.setLinkProfile(socialProfile.getLinkProfile().trim());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<SocialProfile> findSocialProfilesByActor(final int actorId) {
		Collection<SocialProfile> result;

		Actor actor;

		actor = this.actorService.findOne(actorId);

		if (actor.getUserAccount().getAuthorities().toString().equals("[ADMIN]")) {
			final Actor principal;
			principal = this.actorService.findPrincipal();
			Assert.isTrue(principal.getId() == actor.getId());
		}

		result = this.socialProfileRepository.findSocialProfilesByActor(actorId);

		return result;
	}

	// This method is used when an actor wants to delete all his or her data.
	public void deleteSocialProfiles(final Actor actor) {
		Collection<SocialProfile> socialProfiles;

		socialProfiles = this.findSocialProfilesByActor(actor.getId());

		this.socialProfileRepository.delete(socialProfiles);
	}

	protected void flush() {
		this.socialProfileRepository.flush();
	}

	// private methods -----------------------------
	private void checkByPrincipal(final SocialProfile socialProfile) {
		final Actor actor;

		actor = this.actorService.findPrincipal();

		Assert.isTrue(socialProfile.getActor().equals(actor));
	}

}
