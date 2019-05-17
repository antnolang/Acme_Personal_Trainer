
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	// Service under testing ---------------------------
	@Autowired
	private SocialProfileService	socialProfileService;


	// Other supporting tests ---------------------------

	// Suite test ---------------------------------------

	/*
	 * A: Requirement 23.1 (A user can list social profiles).
	 * C: Analysis of sentence coverage: 9/9 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findSocialProfilesByActor_positiveTest() {
		super.authenticate("admin1");

		int actorId;
		Collection<SocialProfile> social_profiles;

		actorId = super.getEntityId("administrator1");

		social_profiles = this.socialProfileService.findSocialProfilesByActor(actorId);

		Assert.notNull(social_profiles);
		Assert.notEmpty(social_profiles);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (A user can list social profiles).
	 * B: The business rule that is intended to be broken: the social profiles belong to an administrator.
	 * C: Analysis of sentence coverage: 7/9 -> 77.78% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findSocialProfilesByActor_negativeTest() {
		super.authenticate("rookie1");

		int actorId;
		Collection<SocialProfile> social_profiles;

		actorId = super.getEntityId("administrator1");

		social_profiles = this.socialProfileService.findSocialProfilesByActor(actorId);

		Assert.notNull(social_profiles);
		Assert.notEmpty(social_profiles);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (A user can show social profiles).
	 * B: A rookie try to display a social profile that belongs to an administrator.
	 * C: Analysis of sentence coverage: 13/14 -> 92.85% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void displaySocialProfile_negativeTest() {
		super.authenticate("rookie1");

		int socialProfileId;
		SocialProfile socialProfile;

		socialProfileId = super.getEntityId("socialProfile1");

		socialProfile = this.socialProfileService.findOneDisplay(socialProfileId);

		Assert.isNull(socialProfile);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (A user can show social profiles).
	 * C: Analysis of sentence coverage: 14/14 -> 100% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void displaySocialProfile_positiveTest() {
		super.authenticate("admin1");

		int socialProfileId;
		SocialProfile socialProfile;

		socialProfileId = super.getEntityId("socialProfile1");

		socialProfile = this.socialProfileService.findOneDisplay(socialProfileId);

		Assert.isNull(socialProfile);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (An authenticated user can create a social profiles).
	 * C: Analysis of sentence coverage: 14/14 -> 100% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void create_test() {
		super.authenticate("rookie1");

		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		Assert.notNull(socialProfile);
		Assert.notNull(socialProfile.getActor());
		Assert.isNull(socialProfile.getLinkProfile());
		Assert.isNull(socialProfile.getNick());
		Assert.isNull(socialProfile.getSocialNetwork());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (An authenticated user can save social profiles).
	 * B: The business rule that is intended to be broken: a user try to edit a social profile that belongs to another user.
	 * C: Analysis of sentence coverage: 5/15 -> 33.33% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void save_negativeTest() {
		super.authenticate("rookie1");

		int socialProfileId;
		SocialProfile socialProfile, saved;

		socialProfileId = super.getEntityId("socialProfile1");

		socialProfile = this.socialProfileService.findOne(socialProfileId);
		socialProfile.setSocialNetwork("Tinder");

		saved = this.socialProfileService.save(socialProfile);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (An authenticated user can save social profiles).
	 * C: Analysis of sentence coverage: 15/15 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void save_positiveTest() {
		super.authenticate("admin1");

		int socialProfileId;
		SocialProfile socialProfile, saved;

		socialProfileId = super.getEntityId("socialProfile1");

		socialProfile = this.socialProfileService.findOne(socialProfileId);
		socialProfile.setSocialNetwork("Tinder");

		saved = this.socialProfileService.save(socialProfile);

		Assert.notNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (An authenticated user can delete social profiles).
	 * B: A user try to delete a social profile that belongs to another user.
	 * C: Analysis of sentence coverage: 6/7 -> 85.71% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	public void delete_negativeTest() {
		super.authenticate("rookie1");

		int socialProfileId;
		SocialProfile socialProfile, found;

		socialProfileId = super.getEntityId("socialProfile1");

		socialProfile = this.socialProfileService.findOne(socialProfileId);

		this.socialProfileService.delete(socialProfile);

		found = this.socialProfileService.findOne(socialProfileId);

		Assert.notNull(found);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 23.1 (An authenticated user can delete social profiles).
	 * C: Analysis of sentence coverage: 7/7 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	public void delete_positiveTest() {
		super.authenticate("rookie1");

		int socialProfileId;
		SocialProfile socialProfile, found;

		socialProfileId = super.getEntityId("socialProfile1");

		socialProfile = this.socialProfileService.findOne(socialProfileId);

		this.socialProfileService.delete(socialProfile);

		found = this.socialProfileService.findOne(socialProfileId);

		Assert.notNull(found);

		super.unauthenticate();
	}

	public void driverSave() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::nick.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::nick is null => 1/19 -> 5.26%.
			 */
			{
				null, "Tinder", "http://www.tinder1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::nick.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::nick is empty string => 1/19 -> 5.26%%.
			 */
			{
				"", "Tinder", "http://www.tinder1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::nick.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::nick is a malicious script => 1/19 -> 5.26%.
			 */
			{
				"<script> Alert('hacked'); </script>", "Tinder", "http://www.tinder1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::socialNetwork.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::socialNetwork is null => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", null, "http://www.tinder1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::socialNetwork.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::socialNetwork is empty string => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "", "http://www.tinder1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::socialNetwork.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::socialNetwork is a malicious script => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "<script> Alert('Hacked'); </script>", "http://www.tinder1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::linkProfile.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::linkProfile is null => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "Tinder", null, ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::linkProfile.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::socialNetwork is empty string => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "Tinder", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::linkProfile.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::linkProfile is a malicious script => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "Tinder", "<script> Alert('Hacked'); </script>", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::linkProfile.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::linkProfile is an invalid URL => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "Tinder", "Esto no es una url", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::linkProfile.
			 * C: Analysis of sentence coverage: 14/15 -> 93.33% executed code lines.
			 * D: Analysis of data coverage: SocialProfile::linkProfile already exists in the DB => 1/19 -> 5.26%.
			 */
			{
				"EleanorLamp", "Tinder", "http://www.twitter1.com", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 23.1 (An authenticated user can create/edit social profiles)
			 * B: Invalid data in SocialProfile::linkProfile.
			 * C: Analysis of sentence coverage: 15/15 -> 100.00% executed code lines.
			 * D: Analysis of data coverage: Every attributes have a valid value => 19/19 -> 100.00%.
			 */
			{
				"EleanorLamp", "Tinder", "http://www.tinder1.com", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateSave(final String nick, final String socialNetwork, final String linkProfile, final Class<?> expected) {
		Class<?> caught;
		SocialProfile socialProfile, saved;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("admin1");

			socialProfile = this.socialProfileService.create();
			socialProfile.setNick(nick);
			socialProfile.setSocialNetwork(socialNetwork);
			socialProfile.setLinkProfile(linkProfile);

			saved = this.socialProfileService.save(socialProfile);
			this.socialProfileService.flush();

			Assert.notNull(saved);
			Assert.isTrue(saved.getId() != 0);
		} catch (final Throwable opps) {
			caught = opps.getClass();
		} finally {
			super.unauthenticate();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

}
