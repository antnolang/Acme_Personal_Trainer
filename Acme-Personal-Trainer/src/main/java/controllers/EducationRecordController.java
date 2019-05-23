
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.EducationRecordService;
import domain.EducationRecord;

@Controller
@RequestMapping("educationRecord/")
public class EducationRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors -----------------------------------------------------------

	public EducationRecordController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.findOne(educationRecordId);

		result = new ModelAndView("educationRecord/display");
		result.addObject("educationRecord", educationRecord);

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int educationRecordId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByEducationRecordId(educationRecordId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

}
