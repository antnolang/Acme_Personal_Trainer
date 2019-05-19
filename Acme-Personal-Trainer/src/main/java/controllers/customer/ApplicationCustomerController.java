
package controllers.customer;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CreditCardService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/application/customer")
public class ApplicationCustomerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public ApplicationCustomerController() {
		super();
	}

	// Request List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> pendingApplications;
		Collection<Application> acceptedApplications;
		Collection<Application> rejectedApplications;

		try {
			pendingApplications = this.applicationService.findPendingApplicationsByCustomer();
			acceptedApplications = this.applicationService.findAcceptedApplicationsByCustomer();
			rejectedApplications = this.applicationService.findRejectedApplicationsByCustomer();

			result = new ModelAndView("application/list");
			result.addObject("pendingApplications", pendingApplications);
			result.addObject("acceptedApplications", acceptedApplications);
			result.addObject("rejectedApplications", rejectedApplications);

			result.addObject("requestURI", "application/customer/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Request create -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int workingOutId) {
		ModelAndView result;

		try {
			final WorkingOut workingOut;
			final Application application;

			workingOut = this.workingOutService.findOneToDisplay(workingOutId);
			application = this.applicationService.create(workingOut);

			result = this.createEditModelAndView(application);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:../../error.do");
		}
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		try {

			application = this.applicationService.findOneToCustomer(applicationId);
			result = this.createEditModelAndView(application);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Application application, final BindingResult binding) {
		ModelAndView result;
		Application applicationRec;

		applicationRec = this.applicationService.reconstruct(application, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(applicationRec);
				result = new ModelAndView("redirect:../customer/list.do");
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
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
		List<CreditCard> creditCards;

		creditCards = this.creditCardService.findAllByCustomer();

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("messageCode", messageCode);
		result.addObject("creditCards", creditCards);

		return result;

	}
}
