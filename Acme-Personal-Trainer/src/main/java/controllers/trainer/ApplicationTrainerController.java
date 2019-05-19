
package controllers.trainer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Application;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/application/trainer")
public class ApplicationTrainerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public ApplicationTrainerController() {
		super();
	}

	// Request List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int workingOutId) {
		ModelAndView result;
		final Collection<Application> submittedApplications;
		Collection<Application> acceptedApplications;
		Collection<Application> rejectedApplications;
		WorkingOut checkWorkingOut;

		try {
			checkWorkingOut = this.workingOutService.findOneFinalByPrincipal(workingOutId);
			submittedApplications = this.applicationService.findPendingApplicationsByWorkingOut(workingOutId);
			acceptedApplications = this.applicationService.findAcceptedApplicationsByWorkingOut(workingOutId);
			rejectedApplications = this.applicationService.findRejectedApplicationsByWorkingOut(workingOutId);

			result = new ModelAndView("application/list");
			result.addObject("submittedApplications", submittedApplications);
			result.addObject("acceptedApplications", acceptedApplications);
			result.addObject("rejectedApplications", rejectedApplications);

			result.addObject("requestURI", "application/trainer/list.do?workingOutId=" + checkWorkingOut.getId());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		try {
			application = this.applicationService.findOneToTrainer(applicationId);

			try {
				this.applicationService.acceptedApplication(application);
				result = new ModelAndView("redirect:../../application/trainer/list.do?workingOutId=" + application.getWorkingOut().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		try {
			application = this.applicationService.findOneToTrainer(applicationId);

			try {
				this.applicationService.rejectedApplication(application);
				result = new ModelAndView("redirect:../../application/trainer/list.do?workingOutId=" + application.getWorkingOut().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;
		int customerId;

		customerId = this.customerService.findByPrincipal().getId();

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("messageCode", messageCode);
		result.addObject("customerId", customerId);

		return result;

	}
}
