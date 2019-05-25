
package controllers.customer;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
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
	private CategoryService			categoryService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------

	public FinderCustomerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final Locale locale) {
		ModelAndView result;
		Customisation customisation;
		Map<Integer, String> categoryMap;
		String category = null;
		int numberOfResults;
		Finder finder;

		customisation = this.customisationService.find();
		numberOfResults = customisation.getNumberResults();
		finder = this.finderService.findByCustomerPrincipal();
		finder = this.finderService.evaluateSearch(finder);

		if (finder.getCategory() != null) {
			categoryMap = this.categoryService.categoriesByLanguage(Arrays.asList(finder.getCategory()), locale.getLanguage());
			category = categoryMap.get(finder.getCategory().getId());
		}

		result = new ModelAndView("workingOut/finder");
		result.addObject("requestURI", "finder/customer/display.do");
		result.addObject("finder", finder);
		result.addObject("category", category);
		result.addObject("numberOfResults", numberOfResults);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final Locale locale) {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findByCustomerPrincipal();
		result = this.createEditModelAndView(finder, locale);

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
	public ModelAndView save(final Finder finder, final BindingResult binding, final Locale locale) {
		ModelAndView result;
		Finder finderRec;

		finderRec = this.finderService.reconstruct(finder, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder, locale);
		else
			try {
				this.finderService.save(finderRec);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finderRec, locale, "finder.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder, final Locale locale) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, locale, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final Locale locale, final String messageCode) {
		ModelAndView result;
		Map<Integer, String> categories;

		categories = this.categoryService.categoriesByLanguage(locale.getLanguage());

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("categories", categories);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
