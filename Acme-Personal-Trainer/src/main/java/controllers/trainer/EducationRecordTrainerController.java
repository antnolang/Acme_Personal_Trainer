
package controllers.trainer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("educationRecord/trainer/")
public class EducationRecordTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	// Constructors -----------------------------------------------------------

	public EducationRecordTrainerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;

		try {
			educationRecord = this.educationRecordService.findOneToEdit(educationRecordId);
			result = this.createEditModelAndView(educationRecord);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramCurriculumId;
		Integer curriculumId;
		String messageError;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord, curriculumId);
		else
			try {
				if (curriculumId == null)
					this.educationRecordService.save(educationRecord);
				else
					this.educationRecordService.save(educationRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Incorrect dates"))
					messageError = "educationRecord.date.error";
				else
					messageError = "educationRecord.commit.error";
				result = this.createEditModelAndView(educationRecord, curriculumId, messageError);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			this.educationRecordService.delete(educationRecord);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, null, "educationRecord.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
