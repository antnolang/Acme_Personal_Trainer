
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EndorsementRepository;
import security.LoginService;
import domain.Customer;
import domain.Endorsement;
import domain.Trainer;

@Service
@Transactional
public class EndorsementService {

	// Managed Repository

	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private Validator				validator;


	// Constructor

	public EndorsementService() {
		super();
	}

	// Simple CRUD methods

	public Endorsement create() {
		Endorsement result;
		Trainer trainer;
		Customer customer;

		trainer = null;
		customer = null;

		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
			trainer = this.trainerService.findByPrincipal();

		result = new Endorsement();
		result.setWrittenMoment(this.utilityService.current_moment());

		if (trainer == null && customer != null) {
			// Customer is logged so we have to set Endorsement::trainerToCustomer to false and set Endorsement::customer
			result.setTrainerToCustomer(false);
			result.setCustomer(customer);
		} else if (trainer != null && customer == null) {
			// Trainer is logged so we have to set Endorsement::trainerToCustomer to true and set Endorsement::Trainer
			result.setTrainerToCustomer(true);
			result.setTrainer(trainer);
		}

		return result;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		this.checkByPrincipal(endorsement);
		Assert.isTrue(this.applicationService.existApplicationAcceptedBetweenCustomerTrainer(endorsement.getCustomer().getId(), endorsement.getTrainer().getId()) == true);

		Endorsement result;

		result = this.endorsementRepository.save(endorsement);

		if (endorsement.isTrainerToCustomer() == false)
			this.trainerService.calculateMark(endorsement.getTrainer());

		return result;

	}

	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		this.checkByPrincipal(endorsement);

		Trainer trainer;

		trainer = endorsement.getTrainer();

		this.endorsementRepository.delete(endorsement);

		if (endorsement.isTrainerToCustomer() == false)
			this.trainerService.calculateMark(trainer);
	}

	public Endorsement findOne(final int endorsementId) {
		Endorsement result;
		Trainer trainerPrincipal;
		Customer customerPrincipal;

		trainerPrincipal = null;
		customerPrincipal = null;

		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
			customerPrincipal = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
			trainerPrincipal = this.trainerService.findByPrincipal();

		result = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);
		Assert.isTrue(result.getTrainer().equals(trainerPrincipal) || result.getCustomer().equals(customerPrincipal));

		return result;
	}

	// Other business methods

	public Endorsement reconstruct(final Endorsement endorsement, final BindingResult binding) {
		Endorsement result, endorsementSaved;

		if (endorsement.getId() == 0) {
			result = this.create();

			result.setMark(endorsement.getMark());
			result.setComments(endorsement.getComments());
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
				result.setTrainer(endorsement.getTrainer());
			else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
				result.setCustomer(endorsement.getCustomer());
		} else {
			result = new Endorsement();
			endorsementSaved = this.findOne(endorsement.getId());

			result.setComments(endorsement.getComments());
			result.setMark(endorsement.getMark());
			result.setId(endorsementSaved.getId());
			result.setVersion(endorsementSaved.getVersion());
			result.setCustomer(endorsementSaved.getCustomer());
			result.setTrainer(endorsementSaved.getTrainer());
			result.setTrainerToCustomer(endorsementSaved.isTrainerToCustomer());
			result.setWrittenMoment(endorsementSaved.getWrittenMoment());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public void checkByPrincipal(final Endorsement endorsement) {
		Trainer trainerPrincipal;
		Customer customerPrincipal;

		trainerPrincipal = null;
		customerPrincipal = null;

		if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
			customerPrincipal = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
			trainerPrincipal = this.trainerService.findByPrincipal();

		if (trainerPrincipal == null && customerPrincipal != null)
			Assert.isTrue(customerPrincipal.equals(endorsement.getCustomer()));
		else if (trainerPrincipal != null && customerPrincipal == null)
			Assert.isTrue(trainerPrincipal.equals(endorsement.getTrainer()));

	}

	public Collection<Endorsement> findReceivedEndorsementsByTrainer(final int trainerId) {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findReceivedEndorsementsByTrainer(trainerId);

		return result;
	}

	public Collection<Endorsement> findSendEndorsementsByTrainer(final int trainerId) {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findSendEndorsementsByTrainer(trainerId);

		return result;
	}

	public Collection<Endorsement> findSendEndorsementsByCustomer(final int customerId) {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findSendEndorsementsByCustomer(customerId);

		return result;
	}

	public Collection<Endorsement> findReceivedEndorsementsByCustomer(final int customerId) {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findReceivedEndorsementsByCustomer(customerId);

		return result;
	}

}
