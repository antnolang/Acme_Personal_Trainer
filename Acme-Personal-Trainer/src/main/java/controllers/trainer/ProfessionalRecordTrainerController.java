
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

import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("professionalRecord/trainer/")
public class ProfessionalRecordTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	// Constructors -----------------------------------------------------------

	public ProfessionalRecordTrainerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.create();
		result = this.createEditModelAndView(professionalRecord, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		try {
			professionalRecord = this.professionalRecordService.findOneToEdit(professionalRecordId);
			result = this.createEditModelAndView(professionalRecord);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramCurriculumId;
		Integer curriculumId;
		String messageError;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord, curriculumId);
		else
			try {
				if (curriculumId == null)
					this.professionalRecordService.save(professionalRecord);
				else
					this.professionalRecordService.save(professionalRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Incorrect dates"))
					messageError = "professionalRecord.date.error";
				else
					messageError = "professionalRecord.commit.error";
				result = this.createEditModelAndView(professionalRecord, curriculumId, messageError);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			this.professionalRecordService.delete(professionalRecord);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, null, "professionalRecord.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
