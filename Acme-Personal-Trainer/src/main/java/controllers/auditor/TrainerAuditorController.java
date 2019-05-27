
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TrainerService;
import controllers.AbstractController;
import domain.Trainer;

@Controller
@RequestMapping("/trainer/auditor")
public class TrainerAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TrainerService	trainerService;


	// Constructors -----------------------------------------------------------

	public TrainerAuditorController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Trainer> actors;

		try {
			actors = this.trainerService.findAllNotBanned();

			result = new ModelAndView("actor/list");
			result.addObject("actors", actors);
			result.addObject("requestURI", "trainer/auditor/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

}
