
package controllers.customerTrainer;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CustomerService;
import services.EndorsementService;
import services.TrainerService;
import controllers.AbstractController;
import domain.Customer;
import domain.Endorsement;
import domain.Trainer;

@Controller
@RequestMapping(value = "/endorsement/customer,trainer")
public class EndorsementCustomerTrainerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TrainerService		trainerService;


	// Constructor

	public EndorsementCustomerTrainerController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;

		try {
			endorsement = this.endorsementService.findOneToDisplay(endorsementId);

			result = new ModelAndView("endorsement/display");
			result.addObject("endorsement", endorsement);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Endorsement> sentEndorsements, receivedEndorsements;
		Customer customerPrincipal;
		Trainer trainerPrincipal;

		sentEndorsements = new ArrayList<>();
		receivedEndorsements = new ArrayList<>();

		try {
			try {
				customerPrincipal = this.customerService.findByPrincipal();
			} catch (final Throwable oops) {
				customerPrincipal = null;
			}

			try {
				trainerPrincipal = this.trainerService.findByPrincipal();
			} catch (final Throwable oops) {
				trainerPrincipal = null;
			}

			if (trainerPrincipal != null && customerPrincipal == null) {
				sentEndorsements = this.endorsementService.findSendEndorsementsByTrainer(trainerPrincipal.getId());
				receivedEndorsements = this.endorsementService.findReceivedEndorsementsByTrainer(trainerPrincipal.getId());
			} else if (trainerPrincipal == null && customerPrincipal != null) {
				sentEndorsements = this.endorsementService.findSendEndorsementsByCustomer(customerPrincipal.getId());
				receivedEndorsements = this.endorsementService.findReceivedEndorsementsByCustomer(customerPrincipal.getId());
			}

			result = new ModelAndView("endorsement/list");
			result.addObject("sentEndorsements", sentEndorsements);
			result.addObject("receivedEndorsements", receivedEndorsements);
			result.addObject("requestURI", "endorsement/customer,trainer/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.create();

		result = this.createEditModelAndView(endorsement);

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;

		try {

			endorsement = this.endorsementService.findOne(endorsementId);
			result = this.createEditModelAndView(endorsement);
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
				result.addObject("trainerFullname", endorsement.getTrainer().getFullname());
			else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
				result.addObject("customerFullname", endorsement.getCustomer().getFullname());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;
		Endorsement endorsementRec;

		endorsementRec = null;

		try {
			endorsementRec = this.endorsementService.reconstruct(endorsement, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
		}

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(endorsement);
			if (endorsement.getId() != 0)
				if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
					result.addObject("trainerFullname", endorsementRec.getTrainer().getFullname());
				else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
					result.addObject("customerFullname", endorsementRec.getCustomer().getFullname());
		} else
			try {
				this.endorsementService.save(endorsementRec);
				result = new ModelAndView("redirect:list.do");
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}

		return result;
	}

	// Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;
		Endorsement endorsementRec;

		endorsementRec = null;

		try {
			endorsementRec = this.endorsementService.findOne(endorsement.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement);
		else
			try {
				this.endorsementService.delete(endorsementRec);
				result = new ModelAndView("redirect:list.do");
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String messageCode) {
		ModelAndView result;
		Collection<Trainer> trainers;
		Collection<Customer> customers;
		Trainer trainerPrincipal;
		Customer customerPrincipal;

		try {
			trainerPrincipal = this.trainerService.findByPrincipal();
			customers = this.customerService.findCustomersWithAcceptedApplicationsByTrainer(trainerPrincipal.getId());
		} catch (final Throwable oops) {
			customers = new ArrayList<>();
		}

		try {
			customerPrincipal = this.customerService.findByPrincipal();
			trainers = this.trainerService.findTrainersWithAcceptedApplicationsByCustomer(customerPrincipal.getId());
		} catch (final Throwable oops) {
			trainers = new ArrayList<>();
		}

		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		if (endorsement.getId() == 0) {
			result.addObject("isUpdating", false);
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
				result.addObject("trainers", trainers);
			else if (LoginService.getPrincipal().getAuthorities().toString().equals("[TRAINER]"))
				result.addObject("customers", customers);
		} else
			result.addObject("isUpdating", true);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
