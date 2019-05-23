
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.ProfessionalRecordService;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("professionalRecord/")
public class ProfessionalRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculumService			curriculumService;


	// Constructors -----------------------------------------------------------

	public ProfessionalRecordController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);

		result = new ModelAndView("professionalRecord/display");
		result.addObject("professionalRecord", professionalRecord);

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByProfessionalRecordId(professionalRecordId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

}
