
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.EndorsementService;
import domain.Customer;
import domain.Endorsement;

@Controller
@RequestMapping(value = "/endorsement")
public class EndorsementController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructor

	public EndorsementController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int trainerId) {
		ModelAndView result;
		Collection<Endorsement> receivedEndorsements;
		Customer customer;
		boolean trainerAttended;

		try {
			customer = this.customerService.findByPrincipal();
			trainerAttended = this.applicationService.existApplicationAcceptedBetweenCustomerTrainer(customer.getId(), trainerId);
			if (trainerAttended == false)
				throw new IllegalArgumentException();
			else
				receivedEndorsements = this.endorsementService.findReceivedEndorsementsByTrainer(trainerId);

			result = new ModelAndView("endorsement/list");
			result.addObject("receivedEndorsements", receivedEndorsements);
			result.addObject("requestURI", "endorsement/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}
}
