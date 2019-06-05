
package controllers.trainer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PersonalRecordService;
import controllers.AbstractController;
import domain.PersonalRecord;

@Controller
@RequestMapping("personalRecord/trainer/")
public class PersonalRecordTrainerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Constructors -----------------------------------------------------------

	public PersonalRecordTrainerController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord personalRecord;

		try {
			personalRecord = this.personalRecordService.findOneToEdit(personalRecordId);
			result = this.editModelAndView(personalRecord);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/trainer/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Fullname does not match"))
					binding.rejectValue("fullName", "personalRecord.fullname.error", "Must match with the full name of your profile.");
				else if (oops.getMessage().contains("Invalid email format"))
					binding.rejectValue("email", "personalRecord.email.error", "The email has an invalid format.");

				result = this.editModelAndView(personalRecord, "personalRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView editModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.editModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView editModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("personalRecord/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
