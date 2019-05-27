
package controllers.customerTrainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping(value = "/application/customer,trainer")
public class ApplicationCustomerTrainerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public ApplicationCustomerTrainerController() {
		super();
	}

	// Application Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		String rolActor;

		try {
			result = new ModelAndView("application/display");
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]")) {
				application = this.applicationService.findOneToCustomer(applicationId);
				result.addObject("application", application);
				rolActor = "customer";
			} else {
				application = this.applicationService.findOneToTrainer(applicationId);
				result.addObject("application", application);
				rolActor = "trainer";
			}
			result.addObject("rolActor", rolActor);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

}
