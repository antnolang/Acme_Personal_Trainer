
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.FinderService;
import controllers.AbstractController;
import domain.Customisation;
import domain.Finder;

@Controller
@RequestMapping("finder/customer/")
public class FinderCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService			finderService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------

	public FinderCustomerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Customisation customisation;
		int numberOfResults;
		Finder finder;

		customisation = this.customisationService.find();
		numberOfResults = customisation.getNumberResults();
		finder = this.finderService.findByCustomerPrincipal();
		finder = this.finderService.evaluateSearch(finder);

		result = new ModelAndView("workingOut/finder");
		result.addObject("requestURI", "finder/customer/display.do");
		result.addObject("finder", finder);
		result.addObject("numberOfResults", numberOfResults);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findByCustomerPrincipal();
		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public ModelAndView clear() {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findByCustomerPrincipal();
		this.finderService.clear(finder);

		result = new ModelAndView("redirect:display.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder finderRec;

		finderRec = this.finderService.reconstruct(finder, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finderRec);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finderRec, "finder.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
