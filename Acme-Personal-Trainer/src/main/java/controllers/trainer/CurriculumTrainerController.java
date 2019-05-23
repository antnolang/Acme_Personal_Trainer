
package controllers.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;

@Controller
@RequestMapping("curriculum/trainer/")
public class CurriculumTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors -----------------------------------------------------------

	public CurriculumTrainerController() {
		super();
	}

	// Controller methods -----------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.create();
		result = this.createModelAndView(curriculum);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		if (curriculum == null)
			result = new ModelAndView("redirect:create.do");
		else {
			result = new ModelAndView("curriculum/display");
			result.addObject("requestURI", "curriculum/trainer/display.do");
			result.addObject("curriculum", curriculum);
			result.addObject("isOwner", true);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		Curriculum curriculumRec;

		curriculumRec = this.curriculumService.reconstruct(curriculum, binding);
		if (binding.hasErrors())
			result = this.createModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculumRec);
				result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Fullname does not match"))
					binding.rejectValue("personalRecord.fullName", "curriculum.fullname.error", "Must match with the full name of your profile.");
				else if (oops.getMessage().contains("Invalid email format"))
					binding.rejectValue("personalRecord.email", "curriculum.email.error", "The email has an invalid format.");

				result = this.createModelAndView(curriculum, "curriculum.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/create");
		result.addObject("curriculum", curriculum);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
