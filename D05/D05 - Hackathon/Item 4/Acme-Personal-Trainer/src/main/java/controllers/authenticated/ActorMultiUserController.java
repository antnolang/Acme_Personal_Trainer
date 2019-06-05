
package controllers.authenticated;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.CustomerService;
import services.NutritionistService;
import services.TrainerService;
import controllers.ActorAbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Customer;
import domain.Nutritionist;
import domain.Trainer;
import forms.RegistrationForm;

@Controller
@RequestMapping(value = "/actor/administrator,auditor,customer,nutritionist,trainer")
public class ActorMultiUserController extends ActorAbstractController {

	//Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private NutritionistService		nutritionistService;


	// Constructor

	public ActorMultiUserController() {
		super();
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		ModelAndView result;
		Administrator administrator;
		Customer customer;
		Trainer trainer;
		Actor actor;
		Nutritionist nutritionist;
		Auditor auditor;

		result = new ModelAndView();

		try {
			actor = this.actorService.findOneToDisplayEdit(actorId);
			if (actor instanceof Administrator) {
				administrator = this.administratorService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(administrator);
				result.addObject("rol", "Administrator");
			} else if (actor instanceof Customer) {
				customer = this.customerService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(customer);
				result.addObject("rol", "Customer");
			} else if (actor instanceof Trainer) {
				trainer = this.trainerService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(trainer);
				result.addObject("rol", "Trainer");
			} else if (actor instanceof Auditor) {
				auditor = this.auditorService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(auditor);
				result.addObject("rol", "Auditor");
			} else if (actor instanceof Nutritionist) {
				nutritionist = this.nutritionistService.findOneToDisplayEdit(actorId);
				result = this.createModelAndView(nutritionist);
				result.addObject("rol", "Nutritionist");
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView saveAdministrator(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Administrator");
		} else
			try {
				administrator = this.administratorService.reconstruct(registrationForm, binding);
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Administrator");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteAdmin")
	public ModelAndView deleteAdministrator(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		Administrator administrator;
		ModelAndView result;

		administrator = this.administratorService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Administrator");
		} else
			try {
				this.administratorService.delete(administrator);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Administrator");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCustomer")
	public ModelAndView saveCustomer(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Customer customer;

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Customer");
		} else
			try {
				customer = this.customerService.reconstruct(registrationForm, binding);
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Customer");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteCustomer")
	public ModelAndView deleteCustomer(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		Customer customer;
		ModelAndView result;

		customer = this.customerService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Customer");
		} else
			try {
				this.customerService.delete(customer);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Customer");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveTrainer")
	public ModelAndView saveTrainer(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Trainer trainer;

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Trainer");
		} else
			try {
				trainer = this.trainerService.reconstruct(registrationForm, binding);
				this.trainerService.save(trainer);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Trainer");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteTrainer")
	public ModelAndView deleteTrainer(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		Trainer trainer;
		ModelAndView result;

		trainer = this.trainerService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Trainer");
		} else
			try {
				this.trainerService.delete(trainer);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Trainer");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAuditor")
	public ModelAndView saveAuditor(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Auditor auditor;

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Auditor");
		} else
			try {
				auditor = this.auditorService.reconstruct(registrationForm, binding);
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Auditor");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteAuditor")
	public ModelAndView deleteAuditor(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		Auditor auditor;
		ModelAndView result;

		auditor = this.auditorService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Auditor");
		} else
			try {
				this.auditorService.delete(auditor);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Auditor");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveNutritionist")
	public ModelAndView saveNutritionist(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Nutritionist nutritionist;

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Nutritionist");
		} else
			try {
				nutritionist = this.nutritionistService.reconstruct(registrationForm, binding);
				this.nutritionistService.save(nutritionist);
				result = new ModelAndView("redirect:/actor/display.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Nutritionist");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteNutritionist")
	public ModelAndView deleteNutritionist(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
		Nutritionist nutritionist;
		ModelAndView result;

		nutritionist = this.nutritionistService.findOneToDisplayEdit(registrationForm.getId());

		if (binding.hasErrors()) {
			result = this.createModelAndView(registrationForm);
			result.addObject("rol", "Nutritionist");
		} else
			try {
				this.nutritionistService.delete(nutritionist);
				session.invalidate();
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.commit.error");
				result.addObject("rol", "Nutritionist");
			}

		return result;
	}

	//Ancillary methods

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

	protected ModelAndView createModelAndView(final Administrator administrator) {

		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.administratorService.createForm(administrator);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Auditor auditor) {

		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.auditorService.createForm(auditor);

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Nutritionist nutritionist) {

		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = this.nutritionistService.createForm(nutritionist);

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

		result = new ModelAndView("actor/edit");
		result.addObject("registrationForm", registrationForm);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
