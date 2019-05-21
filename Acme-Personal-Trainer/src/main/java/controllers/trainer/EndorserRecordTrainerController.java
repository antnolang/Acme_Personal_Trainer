
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

import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("endorserRecord/trainer/")
public class EndorserRecordTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Constructors -----------------------------------------------------------

	public EndorserRecordTrainerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		try {
			endorserRecord = this.endorserRecordService.findOneToEdit(endorserRecordId);
			result = this.createEditModelAndView(endorserRecord);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramCurriculumId;
		Integer curriculumId;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord, curriculumId);
		else
			try {
				if (curriculumId == null)
					this.endorserRecordService.save(endorserRecord);
				else
					this.endorserRecordService.save(endorserRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, curriculumId, "endorserRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			this.endorserRecordService.delete(endorserRecord);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, null, "endorserRecord.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
