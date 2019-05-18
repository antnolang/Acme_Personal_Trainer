
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TrainerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Trainer;

@Service
@Transactional
public class TrainerService {

	// Managed repository --------------------------

	@Autowired
	private TrainerRepository	trainerRepository;

	// Other supporting services -------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;


	// Constructors -------------------------------

	public TrainerService() {
		super();
	}

	// Simple CRUD methods ------------------------

	public Trainer create() {
		Trainer result;

		result = new Trainer();
		result.setUserAccount(this.userAccountService.createUserAccount(Authority.TRAINER));

		return result;
	}

	public Trainer findOne(final int trainerId) {
		Trainer result;

		result = this.trainerRepository.findOne(trainerId);
		Assert.notNull(result);

		return result;
	}

	public Trainer findOneToDisplayEdit(final int trainerId) {
		Assert.isTrue(trainerId != 0);

		Trainer result, principal;

		principal = this.findByPrincipal();
		result = this.trainerRepository.findOne(trainerId);
		Assert.notNull(result);
		Assert.isTrue(principal.getId() == trainerId);

		return result;
	}

	public Trainer save(final Trainer trainer) {
		Trainer result;

		result = (Trainer) this.actorService.save(trainer);

		return result;
	}

	// Other business methods ---------------------

	public Trainer findByPrincipal() {
		Trainer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	private Trainer findByUserAccount(final int userAccountId) {
		Trainer result;

		result = this.trainerRepository.findByUserAccount(userAccountId);

		return result;
	}

}
