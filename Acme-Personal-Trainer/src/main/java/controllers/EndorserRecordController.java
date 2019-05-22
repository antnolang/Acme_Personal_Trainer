
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.EndorserRecordService;
import domain.EndorserRecord;

@Controller
@RequestMapping("endorserRecord/")
public class EndorserRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors -----------------------------------------------------------

	public EndorserRecordController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);

		result = new ModelAndView("endorserRecord/display");
		result.addObject("endorserRecord", endorserRecord);

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int endorserDataId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByEndorserRecordId(endorserDataId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}
}
