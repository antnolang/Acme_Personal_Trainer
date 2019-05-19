
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ApplicationRepository;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.Trainer;
import domain.WorkingOut;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private UtilityService			utilityService;


	//TODO
	@Autowired
	//private MessageService			messageService;
	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create(final WorkingOut workingOut) {
		Assert.isTrue(workingOut.getIsFinalMode());
		this.checkWorkingOutNoAcceptedApplication(workingOut.getId());

		Date moment;
		Application result;
		Customer customer;
		final CreditCard creditCard;
		final List<CreditCard> creditCards;

		customer = this.customerService.findByPrincipal();
		moment = this.utilityService.current_moment();
		//TODO
		//creditCards = new ArrayList<>(this.creditCardService.findAllByCustomer(customer.getId()));

		//TODO
		//Assert.isTrue(!creditCards.isEmpty());
		this.checkWorkingOutNoThisCustomerApplication(workingOut.getId(), customer.getId());

		Assert.isTrue(workingOut.getStartMoment().after(moment));
		//TODO
		//creditCard = creditCards.get(0);

		result = new Application();
		result.setCustomer(customer);
		//TODO
		//result.setCreditCard(creditCard);
		result.setWorkingOut(workingOut);
		result.setStatus("PENDING");

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getWorkingOut().getIsFinalMode());
		this.checkWorkingOutNoAcceptedApplication(application.getWorkingOut().getId());
		this.checkWorkingOutNoThisCustomerApplication(application.getWorkingOut().getId(), application.getCustomer().getId());
		Assert.isTrue(this.customerService.findByPrincipal().equals(application.getCustomer()));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isNull(this.applicationRepository.findOne(application.getId()));
		//TODO
		//Assert.isTrue(!(this.creditCardService.findAllByCustomer(application.getCustomer().getId())).isEmpty());
		Assert.isTrue(application.getCreditCard().getCustomer().equals(this.customerService.findByPrincipal()));
		Assert.isNull(application.getRegisteredMoment());

		Application result;

		Date moment;
		moment = this.utilityService.current_moment();

		application.setRegisteredMoment(moment);

		result = this.applicationRepository.save(application);

		return result;
	}

	public void acceptedApplication(final Application application) {
		Assert.isTrue(this.trainerService.findByPrincipal().equals(application.getWorkingOut().getTrainer()));
		this.checkWorkingOutNoAcceptedApplication(application.getWorkingOut().getId());
		Assert.isTrue(application.getStatus().equals("PENDING"));

		Collection<Application> pendingApplications;

		application.setStatus("ACCEPTED");
		//TODO
		//this.messageService.notification_applicationStatusChanges(application);

		pendingApplications = this.applicationRepository.findPendingApplicationsByWorkingOut(application.getWorkingOut().getId());

		for (final Application a : pendingApplications)
			this.rejectedApplication(a);

	}
	public void rejectedApplication(final Application application) {
		Assert.isTrue(this.trainerService.findByPrincipal().equals(application.getWorkingOut().getTrainer()));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		application.setStatus("REJECTED");
		//TODO
		//this.messageService.notification_applicationStatusChanges(application);
	}

	protected Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Application findOneToDisplay(final int applicationId) {
		Application result;

		result = this.findOne(applicationId);

		Assert.notNull(result);
		Assert.isTrue(this.trainerService.findByPrincipal().equals(result.getWorkingOut().getTrainer()));
		Assert.isTrue(this.customerService.findByPrincipal().equals(result.getCustomer()));

		return result;
	}

	public Application findOneToTrainer(final int applicationId) {
		Application result;

		result = this.findOne(applicationId);

		Assert.notNull(result);
		Assert.isTrue(this.trainerService.findByPrincipal().equals(result.getWorkingOut().getTrainer()));

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> results;

		results = this.applicationRepository.findAll();

		return results;
	}

	protected void deleteApplicationByTrainer(final Trainer trainer) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByTrainer(trainer.getId());

		this.applicationRepository.deleteInBatch(applications);
	}

	protected void deleteApplicationByRookie(final Customer customer) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByCustomer(customer.getId());
		this.applicationRepository.deleteInBatch(applications);
	}

	// Reconstruct ----------------------------------------------
	public Application reconstruct(final Application application, final BindingResult binding) {
		Application result;

		result = this.create(application.getWorkingOut());
		result.setCreditCard(application.getCreditCard());

		return result;
	}

	// Other business methods ---------------------

	private void checkWorkingOutNoAcceptedApplication(final int workingOutId) {
		Assert.isTrue(this.applicationRepository.findAcceptedApplicationsByWorkingOut(workingOutId).isEmpty());

	}

	private void checkWorkingOutNoThisCustomerApplication(final int workingOutId, final int customerId) {
		Assert.isTrue(this.applicationRepository.findApplicationsByWorkingOutByCustomer(workingOutId, customerId).isEmpty());

	}

	protected void flush() {
		this.applicationRepository.flush();
	}

}
