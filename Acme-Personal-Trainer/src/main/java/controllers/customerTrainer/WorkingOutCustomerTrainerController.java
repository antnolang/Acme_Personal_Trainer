
package controllers.customerTrainer;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CategoryService;
import services.CustomerService;
import services.CustomisationService;
import services.TrainerService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Category;
import domain.Customer;
import domain.Session;
import domain.Trainer;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/workingOut/customer,trainer")
public class WorkingOutCustomerTrainerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private WorkingOutService		workingOutService;

	@Autowired
	private TrainerService			trainerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CategoryService			categoryService;


	// Constructors -----------------------------------------------------------

	public WorkingOutCustomerTrainerController() {
		super();
	}

	// Working-out Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int workingOutId) {
		ModelAndView result;
		WorkingOut workingOut;
		Customer customerPrincipal;
		Trainer trainerPrincipal;
		final Boolean isApplied;
		Map<Integer, String> categories;
		Collection<Category> categoriesWorkingOut;
		Collection<Session> sessions;
		double VAT;
		String language;

		try {
			language = LocaleContextHolder.getLocale().getLanguage();
			VAT = this.customisationService.find().getVAT();
			result = new ModelAndView("workingOut/display");
			workingOut = this.workingOutService.findOne(workingOutId);
			categoriesWorkingOut = this.workingOutService.getCategoriesByWorkingOut(workingOut);
			categories = this.categoryService.categoriesByLanguage(categoriesWorkingOut, language);
			sessions = this.workingOutService.getSessionsByWorkingOut(workingOut);

			try {
				customerPrincipal = this.customerService.findByPrincipal();
			} catch (final Exception e1) {
				customerPrincipal = null;
			}

			try {
				trainerPrincipal = this.trainerService.findByPrincipal();
			} catch (final Exception e1) {
				trainerPrincipal = null;
			}

			if (trainerPrincipal != null) {
				workingOut = this.workingOutService.findOne(workingOutId);

				result.addObject("workingOut", workingOut);
				result.addObject("principal", trainerPrincipal);

			} else {
				workingOut = this.workingOutService.findOneToDisplay(workingOutId);
				isApplied = this.applicationService.isApplied(workingOut, customerPrincipal);

				result.addObject("workingOut", workingOut);
				result.addObject("principal", null);
				result.addObject("isApplied", isApplied);

			}
			result.addObject("VAT", VAT);
			result.addObject("categories", categories);
			result.addObject("sessions", sessions);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}
}
