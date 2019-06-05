
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.TrainerService;
import domain.Customer;
import domain.Trainer;
import forms.RegistrationForm;

@Controller
@RequestMapping(value = "/actor")
public class ActorController extends ActorAbstractController {

	// Services

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private TrainerService	trainerService;


	//Constructor

	public ActorController() {
		super();
	}

	//Display

	@Override
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;

		try {
			result = super.display(actorId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Register customer

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {
		ModelAndView result;
		String rol;
		Customer customer;

		rol = "Customer";
		customer = new Customer();
		result = this.createModelAndView(customer);
		result.addObject("rol", rol);
		result.addObject("urlAdmin", "");

		return result;
	}

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.reconstruct(registrationForm, binding);

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Customer");
		} else
			try {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.registration.error");
				result.addObject("rol", "Customer");
			}

		return result;
	}

	// Register trainer

	@RequestMapping(value = "/registerTrainer", method = RequestMethod.GET)
	public ModelAndView createTrainer() {
		ModelAndView result;
		String rol;
		Trainer trainer;

		rol = "Trainer";
		trainer = new Trainer();
		result = this.createModelAndView(trainer);
		result.addObject("rol", rol);
		result.addObject("urlAdmin", "");

		return result;
	}

	@RequestMapping(value = "/registerTrainer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveTrainer(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Trainer trainer;

		trainer = this.trainerService.reconstruct(registrationForm, binding);

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Trainer");
		} else
			try {
				this.trainerService.save(trainer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.registration.error");
				result.addObject("rol", "Trainer");
			}

		return result;
	}

	// Ancillary Methods

	protected ModelAndView createModelAndView(final Customer customer) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.customerService.createForm(customer);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Trainer trainer) {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.trainerService.createForm(trainer);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm) {
		ModelAndView result;

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/singup");
		result.addObject("registrationForm", registrationForm);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
