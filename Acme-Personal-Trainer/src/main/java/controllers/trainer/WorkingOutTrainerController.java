
package controllers.trainer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ApplicationService;
import services.CategoryService;
import services.CustomerService;
import services.TrainerService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Category;
import domain.Trainer;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/workingOut/trainer")
public class WorkingOutTrainerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private TrainerService		trainerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CategoryService		categoryService;


	// Constructors -----------------------------------------------------------

	public WorkingOutTrainerController() {
		super();
	}

	// Working-out list by principal trainer -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<WorkingOut> workingOuts;
		Trainer principal;

		try {
			principal = this.trainerService.findByPrincipal();
			result = new ModelAndView("workingOut/list");
			workingOuts = this.workingOutService.findWorkingOutsByTrainer(principal);

			result.addObject("principal", principal);
			result.addObject("workingOuts", workingOuts);
			result.addObject("requestURI", "workingOut/trainer/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/makeFinal", method = RequestMethod.GET)
	public ModelAndView makeFinal(@RequestParam final int workingOutId, final RedirectAttributes redir) {
		ModelAndView result;
		WorkingOut workingOut;

		try {
			workingOut = this.workingOutService.findOne(workingOutId);
			this.workingOutService.makeFinal(workingOut);
		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "workingOut.make.final.error");
		}

		result = new ModelAndView("redirect:/workingOut/trainer/list.do");

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		WorkingOut workingOut;

		workingOut = this.workingOutService.create();

		result = this.createEditModelAndView(workingOut);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int workingOutId) {
		ModelAndView result;
		WorkingOut workingOut;

		try {
			workingOut = this.workingOutService.findOneToEditDelete(workingOutId);

			result = this.createEditModelAndView(workingOut);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final WorkingOut workingOut, final BindingResult binding) {
		ModelAndView result;
		WorkingOut workingOutRec;
		workingOutRec = this.workingOutService.reconstruct(workingOut, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(workingOut);
		else
			try {
				this.workingOutService.save(workingOutRec);
				result = new ModelAndView("redirect:list.do");
			} catch (final TransactionSystemException oops) {
				result = new ModelAndView("redirect:../../error.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(workingOut, "workingOut.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final WorkingOut workingOut, final BindingResult binding) {
		ModelAndView result;
		WorkingOut workingOutBbdd;

		try {

			workingOutBbdd = this.workingOutService.findOneToEditDelete(workingOut.getId());

			this.workingOutService.delete(workingOutBbdd);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(workingOut, "workingOut.delete.error");
		}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final WorkingOut workingOut) {
		ModelAndView result;

		result = this.createEditModelAndView(workingOut, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final WorkingOut workingOut, final String messageCode) {
		ModelAndView result;
		Trainer principal;
		Collection<Category> categories;

		principal = this.trainerService.findByPrincipal();
		categories = this.categoryService.findAll();

		result = new ModelAndView("workingOut/edit");
		result.addObject("workingOut", workingOut);
		result.addObject("principal", principal);
		result.addObject("messageCode", messageCode);
		result.addObject("categories", categories);

		return result;

	}
}
