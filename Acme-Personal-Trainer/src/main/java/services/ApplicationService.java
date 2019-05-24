
package services;

import java.util.ArrayList;
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

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private CustomisationService	customisationService;


	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create(final WorkingOut workingOut) {
		Application result;
		Customer customer;
		final CreditCard creditCard;
		final List<CreditCard> creditCards;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(this.isApplied(workingOut, customer));
		creditCards = new ArrayList<>(this.creditCardService.findAllByCustomer());

		creditCard = creditCards.get(0);

		result = new Application();
		result.setCustomer(customer);
		result.setCreditCard(creditCard);
		result.setWorkingOut(workingOut);
		result.setStatus("PENDING");

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(this.isApplied(application.getWorkingOut(), application.getCustomer()));
		Assert.isTrue(this.customerService.findByPrincipal().equals(application.getCustomer()));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isNull(this.applicationRepository.findOne(application.getId()));
		Assert.isTrue(!(this.creditCardService.findAllByCustomer()).isEmpty());
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
		Assert.isTrue(this.checkWorkingOutNoAcceptedApplication(application.getWorkingOut().getId()));
		Assert.isTrue(application.getStatus().equals("PENDING"));

		Collection<Application> pendingApplications;

		application.setStatus("ACCEPTED");
		if (this.customerService.spendCustomer(application.getCustomer()) >= (this.customisationService.find().getPremiumAmount()))
			application.getCustomer().setIsPremium(true);

		pendingApplications = this.findPendingApplicationsByWorkingOut(application.getWorkingOut().getId());
		//this.messageService.notification_applicationStatusChanges(application);

		if (!(pendingApplications.isEmpty()))
			for (final Application a : pendingApplications)
				this.rejectedApplication(a);

	}

	public void rejectedApplication(final Application application) {
		Assert.isTrue(this.trainerService.findByPrincipal().equals(application.getWorkingOut().getTrainer()));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		application.setStatus("REJECTED");
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

	public Application findOneToCustomer(final int applicationId) {
		Application result;

		result = this.findOne(applicationId);

		Assert.notNull(result);
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

	protected void deleteApplicationByCustomer(final Customer customer) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByCustomer(customer.getId());
		this.applicationRepository.deleteInBatch(applications);
	}

	// Reconstruct ----------------------------------------------
	public Application reconstruct(final Application application, final BindingResult binding) {
		Application result;

		result = this.create(application.getWorkingOut());
		result.setCreditCard(application.getCreditCard());
		result.setComments(application.getComments());

		return result;
	}

	// Other business methods ---------------------

	private boolean checkWorkingOutNoAcceptedApplication(final int workingOutId) {
		return this.applicationRepository.findAcceptedApplicationsByWorkingOut(workingOutId).isEmpty();

	}

	public Collection<Application> findPendingApplicationsByCustomer() {
		Collection<Application> applications;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		applications = this.applicationRepository.findPendingApplicationsByCustomer(customer.getId());

		return applications;
	}

	public Collection<Application> findAcceptedApplicationsByCustomer() {
		Collection<Application> applications;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		applications = this.applicationRepository.findAcceptedApplicationsByCustomer(customer.getId());

		return applications;
	}

	public Collection<Application> findRejectedApplicationsByCustomer() {
		Collection<Application> applications;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		applications = this.applicationRepository.findRejectedApplicationsByCustomer(customer.getId());

		return applications;
	}

	public Collection<Application> findPendingApplicationsByWorkingOut(final int workingOutId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findPendingApplicationsByWorkingOut(workingOutId);

		return applications;
	}

	public Collection<Application> findAcceptedApplicationsByWorkingOut(final int workingOutId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findAcceptedApplicationsByWorkingOut(workingOutId);

		return applications;
	}

	public Collection<Application> findRejectedApplicationsByWorkingOut(final int workingOutId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findRejectedApplicationsByWorkingOut(workingOutId);

		return applications;
	}

	public boolean existApplicationAcceptedBetweenCustomerTrainer(final int customerId, final int trainerId) {
		boolean result;

		result = this.applicationRepository.existApplicationAcceptedBetweenCustomerTrainer(customerId, trainerId);

		return result;
	}

	protected Collection<Application> applicationsWithCreditCard(final int id) {
		Collection<Application> res;

		res = this.applicationRepository.applicationsWithCreditCard(id);

		return res;
	}

	protected void flush() {
		this.applicationRepository.flush();
	}

	public boolean isApplied(final WorkingOut workingOut, final Customer customerPrincipal) {
		boolean result;
		Date moment;
		final List<CreditCard> creditCards;

		creditCards = new ArrayList<>(this.creditCardService.findAllByCustomer());
		Assert.isTrue(!creditCards.isEmpty());

		moment = this.utilityService.current_moment();
		result = this.checkWorkingOutNoAcceptedApplication(workingOut.getId()) && this.applicationRepository.findApplicationsByWorkingOutByCustomer(workingOut.getId(), customerPrincipal.getId()).isEmpty() && workingOut.getIsFinalMode()
			&& workingOut.getStartMoment().after(moment) && (!creditCards.isEmpty());

		return result;
	}

	public Double findRatioRejectedApplications() {
		Double res;

		res = this.applicationRepository.findRatioRejectedApplications();

		return res;
	}
	public Double findRatioAcceptedApplications() {
		Double res;

		res = this.applicationRepository.findRatioAcceptedApplications();

		return res;
	}

	public Double findRatioPendingApplications() {
		Double res;

		res = this.applicationRepository.findRatioPendingApplications();

		return res;
	}

}
