
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NutritionistRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Nutritionist;

@Service
@Transactional
public class NutritionistService {

	// Managed repository --------------------------

	@Autowired
	private NutritionistRepository	nutritionistRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;


	// Constructors -------------------------------

	public NutritionistService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Nutritionist create() {
		Nutritionist result;

		result = new Nutritionist();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.NUTRITIONIST));

		return result;
	}

	public Nutritionist findOne(final int nutritionistId) {
		Nutritionist result;

		result = this.nutritionistRepository.findOne(nutritionistId);
		Assert.notNull(result);

		return result;
	}

	public Nutritionist findOneToDisplayEdit(final int nutritionistId) {
		Assert.isTrue(nutritionistId != 0);

		Nutritionist result, principal;

		principal = this.findByPrincipal();
		result = this.nutritionistRepository.findOne(nutritionistId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == nutritionistId);

		return result;
	}

	public Nutritionist save(final Nutritionist nutritionist) {
		Nutritionist result;

		result = (Nutritionist) this.actorService.save(nutritionist);

		return result;
	}

	// Other business methods ---------------------

	public Nutritionist findByPrincipal() {
		Nutritionist result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Nutritionist findByUserAccount(final int userAccountId) {
		Nutritionist result;

		result = this.nutritionistRepository.findByUserAccount(userAccountId);

		return result;
	}

}
