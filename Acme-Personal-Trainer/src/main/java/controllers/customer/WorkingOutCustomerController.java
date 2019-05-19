
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.TrainerService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/workingOut/customer")
public class WorkingOutCustomerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public WorkingOutCustomerController() {
		super();
	}

	// Working-out list by principal trainer -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<WorkingOut> workingOuts;

		try {
			result = new ModelAndView("workingOut/list");
			workingOuts = this.workingOutService.findAllVisible();

			result.addObject("workingOuts", workingOuts);
			result.addObject("requestURI", "workingOut/customer/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

}
