
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.MiscellaneousRecordService;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("miscellaneousRecord/")
public class MiscellaneousRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculumService			curriculumService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);

		result = new ModelAndView("miscellaneousRecord/display");
		result.addObject("miscellaneousRecord", miscellaneousRecord);

		return result;
	}

	@RequestMapping(value = "/backCurriculum", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		int curriculumId;

		curriculumId = this.curriculumService.findIdByMiscellaneousRecordId(miscellaneousRecordId);
		result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);

		return result;
	}

}
