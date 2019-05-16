
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.UtilityService;
import controllers.AbstractController;
import domain.Customisation;

@Controller
@RequestMapping("/customisation/administrator")
public class CustomisationAdministratorController extends AbstractController {

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private UtilityService			utilityService;


	public CustomisationAdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Customisation customisation;

		customisation = this.customisationService.find();

		result = new ModelAndView("customisation/display");
		result.addObject("customisation", customisation);
		result.addObject("spamWords", this.utilityService.ListByString(customisation.getSpamWords()));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Customisation customisation;

		customisation = this.customisationService.find();

		result = this.editModelAndView(customisation);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Customisation customisation, final BindingResult binding) {
		ModelAndView result;
		Customisation custo;

		custo = this.customisationService.reconstruct(customisation, binding);
		if (binding.hasErrors())
			result = this.editModelAndView(customisation);
		else
			try {
				this.customisationService.save(custo);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(custo, "customisation.commit.error");
			}

		return result;
	}

	// Ancillary methods -----------------------------------
	protected ModelAndView editModelAndView(final Customisation customisation) {
		ModelAndView result;

		result = this.editModelAndView(customisation, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Customisation customisation, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("customisation/edit");
		result.addObject("customisation", customisation);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
