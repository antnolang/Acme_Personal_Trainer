
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ApplicationRepository;
import repositories.CreditCardRepository;
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

	//TODO cambiar al service cuando esté hecho
	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private UtilityService			utilityService;


	//TODO DESCOMENTAR CUANDO ESTÉ SUBIDO MESSAGE
	//@Autowired
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
		List<CreditCard> creditCards;

		customer = this.customerService.findByPrincipal();
		moment = this.utilityService.current_moment();
		//TODO cambiar al service cuando esté hecho
		creditCards = new ArrayList<>(this.creditCardRepository.findAllByCustomer(customer.getId()));

		Assert.isTrue(!creditCards.isEmpty());
		this.checkWorkingOutNoThisCustomerApplication(workingOut.getId(), customer.getId());

		Assert.isTrue(workingOut.getStartMoment().after(moment));
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
		Assert.isTrue(application.getWorkingOut().getIsFinalMode());
		this.checkWorkingOutNoAcceptedApplication(application.getWorkingOut().getId());
		this.checkWorkingOutNoThisCustomerApplication(application.getWorkingOut().getId(), application.getCustomer().getId());
		Assert.isTrue(this.customerService.findByPrincipal().equals(application.getCustomer()));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.isNull(this.applicationRepository.findOne(application.getId()));
		//TODO cambiar al service cuando esté hecho
		Assert.isTrue(!(this.creditCardRepository.findAllByCustomer(application.getCustomer().getId())).isEmpty());
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
		//TODO DESCOMENTAR CUANDO ESTÉ SUBIDO MESSAGE
		//this.messageService.notification_applicationStatusChanges(application);
		
		pendingApplications = this.applicationRepository.findPendingApplicationsByWorkingOut(application.getWorkingOut().getId());
		
		//TODO Rechazar todas las demas
		for (
		
	}
	public void rejectedApplication(final Application application) {
		Assert.isTrue(this.trainerService.findByPrincipal().equals(application.getWorkingOut().getTrainer()));
		Assert.isTrue(application.getStatus().equals("PENDING"));
		application.setStatus("REJECTED");
		//TODO DESCOMENTAR CUANDO ESTÉ SUBIDO MESSAGE
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

	public Collection<Application> findAll() {
		Collection<Application> results;

		results = this.applicationRepository.findAll();

		return results;
	}

	protected void deleteApplicationByTrainer(final Trainer trainer) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationByTrainer(trainer.getId());

		this.applicationRepository.deleteInBatch(applications);
	}

	protected void deleteApplicationByRookie(final Rookie rookie) {
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationByRookie(rookie.getId());
		this.applicationRepository.deleteInBatch(applications);
	}

	// Reconstruct ----------------------------------------------
	public Application reconstruct(final Application application, final BindingResult binding) {
		Application result, applicationStored;

		if (application.getId() != 0) {
			result = new Application();
			applicationStored = this.findOne(application.getId());
			result.setId(applicationStored.getId());
			result.setVersion(applicationStored.getVersion());
			result.setApplicationMoment(applicationStored.getApplicationMoment());
			result.setStatus(applicationStored.getStatus());
			result.setCurriculum(applicationStored.getCurriculum());
			result.setPosition(applicationStored.getPosition());
			result.setProblem(applicationStored.getProblem());

			result.setSubmittedMoment(application.getSubmittedMoment());
			result.setAnswer(application.getAnswer());

		} else {
			result = this.create(application.getPosition());
			result.setCurriculum(application.getCurriculum());
		}

		return result;
	}

	// Other business methods ---------------------

	private void checkWorkingOutNoAcceptedApplication(final int workingOutId) {
		Assert.isTrue(this.applicationRepository.findAcceptedApplicationsByWorkingOut(workingOutId).isEmpty());

	}

	private void checkWorkingOutNoThisCustomerApplication(final int workingOutId, final int customerId) {
		Assert.isTrue(this.applicationRepository.findApplicationsByCustomer(workingOutId, customerId).isEmpty());

	}

	private Problem getRandomProblem(final Collection<Problem> problems) {
		List<Problem> problemList;
		Problem result;

		problemList = new ArrayList<>(problems);
		result = problemList.get(new Random().nextInt(problems.size()));

		return result;
	}

	public Double[] findDataNumberApplicationPerRookie() {
		Double[] result;

		result = this.applicationRepository.findDataNumberApplicationPerRookie();
		Assert.notNull(result);

		return result;
	}

	protected Collection<Application> findApplicationsByProblemRookie(final int idProblem, final int idRookie) {
		Collection<Application> result;

		result = this.applicationRepository.findApplicationsByProblemRookie(idProblem, idRookie);

		return result;
	}

	public Collection<Application> findPendingApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findPendingApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findSubmittedApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findSubmittedApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findAcceptedApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findAcceptedApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findRejectedApplicationsByRookie() {
		Collection<Application> applications;
		Rookie rookie;

		rookie = this.rookieService.findByPrincipal();
		applications = this.applicationRepository.findRejectedApplicationsByRookie(rookie.getId());

		return applications;
	}

	public Collection<Application> findSubmittedApplicationsByPosition(final int positionId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findSubmittedApplicationsByPosition(positionId);

		return applications;
	}

	public Collection<Application> findAcceptedApplicationsByPosition(final int positionId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findAcceptedApplicationsByPosition(positionId);

		return applications;
	}

	public Collection<Application> findRejectedApplicationsByPosition(final int positionId) {
		Collection<Application> applications;

		applications = this.applicationRepository.findRejectedApplicationsByPosition(positionId);

		return applications;
	}

	public Application findApplicationByAnswer(final int answerId) {
		Application result;

		result = this.applicationRepository.findApplicationByAnswer(answerId);

		return result;
	}

	public boolean isApplied(final Position position, final Rookie rookie) {
		boolean result;
		Collection<Application> applications;

		applications = this.applicationRepository.findApplicationsByPositionByRookie(position.getId(), rookie.getId());
		result = applications.isEmpty();

		return result;
	}

	protected Collection<Application> findSubmittedPendingByPosition(final int positionId) {
		Collection<Application> result;

		result = this.applicationRepository.findSubmittedPendingByPosition(positionId);

		return result;
	}

	protected void rejectedCancelPosition(final Application application) {
		Assert.isTrue(this.companyService.findByPrincipal().equals(application.getPosition().getCompany()));
		Assert.isTrue(application.getStatus().equals("SUBMITTED") || application.getStatus().equals("PENDING"));
		application.setStatus("REJECTED");
		this.messageService.notification_applicationStatusChanges(application);
	}

	protected void flush() {
		this.applicationRepository.flush();
	}

}
