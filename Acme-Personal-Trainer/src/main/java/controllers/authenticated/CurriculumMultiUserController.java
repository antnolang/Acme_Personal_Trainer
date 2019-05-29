
package controllers.authenticated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;

@Controller
@RequestMapping("curriculum/")
public class CurriculumMultiUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public CurriculumMultiUserController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);
		result = this.displayModelAndView(curriculum);

		return result;
	}

	@RequestMapping(value = "/displayFromTrainer", method = RequestMethod.GET)
	public ModelAndView displayFromTrainer(@RequestParam final int trainerId) {
		ModelAndView result;
		Curriculum curriculum;

		try {
			curriculum = this.curriculumService.findByTrainerId(trainerId);
			if (curriculum == null)
				throw new IllegalArgumentException();
			else
				result = this.displayModelAndView(curriculum);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView displayModelAndView(final Curriculum curriculum) {
		ModelAndView result;
		boolean isOwner, isAuditable;

		isOwner = this.curriculumService.checkPrincipalIsOwner(curriculum);
		isAuditable = this.curriculumService.checkIsAuditable(curriculum);

		result = new ModelAndView("curriculum/display");
		result.addObject("requestURI", "curriculum/display.do");
		result.addObject("curriculum", curriculum);
		result.addObject("isOwner", isOwner);
		result.addObject("isAuditable", isAuditable);

		return result;
	}

}
