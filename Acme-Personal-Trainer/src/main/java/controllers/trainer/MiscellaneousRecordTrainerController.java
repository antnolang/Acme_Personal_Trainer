
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

import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("miscellaneousRecord/trainer/")
public class MiscellaneousRecordTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordTrainerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		try {
			miscellaneousRecord = this.miscellaneousRecordService.findOneToEdit(miscellaneousRecordId);
			result = this.createEditModelAndView(miscellaneousRecord);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramCurriculumId;
		Integer curriculumId;

		paramCurriculumId = request.getParameter("curriculumId");
		curriculumId = paramCurriculumId.isEmpty() ? null : Integer.parseInt(paramCurriculumId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord, curriculumId);
		else
			try {
				if (curriculumId == null)
					this.miscellaneousRecordService.save(miscellaneousRecord);
				else
					this.miscellaneousRecordService.save(miscellaneousRecord, curriculumId);
				result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, curriculumId, "miscellaneousRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			this.miscellaneousRecordService.delete(miscellaneousRecord);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, null, "miscellaneousRecord.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final Integer curriculumId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
