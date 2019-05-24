
package controllers.administrator;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.EndorsementService;
import services.FinderService;
import services.TrainerService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Customer;
import domain.Trainer;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ------------------

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private TrainerService		trainerService;


	// Constructors --------------
	public DashboardAdministratorController() {
		super();
	}

	// methods --------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final Locale locale) {
		final ModelAndView result;

		result = new ModelAndView("dashboard/display");

		// LEVEL C -----------------------------------------

		// Req 11.4.1
		Double[] findDataNumberWorkingOutPerTrainer;
		findDataNumberWorkingOutPerTrainer = this.workingOutService.findDataNumberWorkingOutPerTrainer();
		result.addObject("findDataNumberWorkingOutPerTrainer", findDataNumberWorkingOutPerTrainer);

		// Req 11.4.2
		Double[] findDataNumberApplicationPerWorkingOut;
		findDataNumberApplicationPerWorkingOut = this.workingOutService.findDataNumberApplicationPerWorkingOut();
		result.addObject("findDataNumberApplicationPerWorkingOut", findDataNumberApplicationPerWorkingOut);

		// Req 11.4.3
		Double[] findDataPricePerWorkingOut;
		findDataPricePerWorkingOut = this.workingOutService.findDataPricePerWorkingOut();
		result.addObject("findDataPricePerWorkingOut", findDataPricePerWorkingOut);

		// Req 11.4.4
		Double findRatioPendingApplications;
		findRatioPendingApplications = this.applicationService.findRatioPendingApplications();
		result.addObject("findRatioPendingApplications", findRatioPendingApplications);

		// Req 11.4.5
		Double findRatioAcceptedApplications;
		findRatioAcceptedApplications = this.applicationService.findRatioAcceptedApplications();
		result.addObject("findRatioAcceptedApplications", findRatioAcceptedApplications);

		// Req 11.4.6
		Double findRatioRejectedApplications;
		findRatioRejectedApplications = this.applicationService.findRatioRejectedApplications();
		result.addObject("findRatioRejectedApplications", findRatioRejectedApplications);

		// Req 11.4.7
		Collection<Trainer> findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName;
		findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName = this.trainerService.findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName();
		result.addObject("findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName", findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName);

		// Req 11.4.8
		Collection<Customer> findUsualCustomers;
		findUsualCustomers = this.customerService.findUsualCustomers();
		result.addObject("findUsualCustomers", findUsualCustomers);

		// LEVEL B --------------------------------------

		// Req 37.5.1
		Double[] findDataNumberEndorsementPerTrainer;
		findDataNumberEndorsementPerTrainer = this.endorsementService.findDataNumberEndorsementPerTrainer();
		result.addObject("findDataNumberEndorsementPerTrainer", findDataNumberEndorsementPerTrainer);

		// Req 37.5.2
		Double ratioTrainerWithEndorsement;
		ratioTrainerWithEndorsement = this.trainerService.ratioTrainerWithEndorsement();
		result.addObject("ratioTrainerWithEndorsement", ratioTrainerWithEndorsement);

		// Req 37.5.3
		Collection<Customer> findCustomerWriteMostEndorsement;
		findCustomerWriteMostEndorsement = this.customerService.findCustomerWriteMostEndorsement();
		result.addObject("findCustomerWriteMostEndorsement", findCustomerWriteMostEndorsement);

		// Req 37.5.4
		Double findRatioEmptyVsNonEmpty;
		findRatioEmptyVsNonEmpty = this.finderService.findRatioEmptyVsNonEmpty();
		result.addObject("findRatioEmptyVsNonEmpty", findRatioEmptyVsNonEmpty);

		return result;
	}
}
