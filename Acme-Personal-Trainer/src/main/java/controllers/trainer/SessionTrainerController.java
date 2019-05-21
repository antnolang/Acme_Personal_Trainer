
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

import services.SessionService;
import services.TrainerService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Session;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/session/trainer")
public class SessionTrainerController extends AbstractController {

	// Services------------------------------------
	@Autowired
	private SessionService		sessionService;

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private TrainerService		trainerService;


	// Constructors -----------------------------------------------------------

	public SessionTrainerController() {
		super();
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int workingOutId) {
		ModelAndView result;
		Session session;

		try {
			session = this.sessionService.create();

			result = this.createModelAndView(session, workingOutId);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Session session, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		WorkingOut workingOut;
		Integer workingOutId;
		String paramWorkingOutId;

		paramWorkingOutId = request.getParameter("workingOutId");
		workingOutId = paramWorkingOutId.isEmpty() ? null : Integer.parseInt(paramWorkingOutId);

		if (binding.hasErrors())
			result = this.createModelAndView(session, workingOutId);
		else
			try {
				workingOut = this.workingOutService.findOneToCreateSession(workingOutId);

				this.sessionService.save(session, workingOut);
				result = new ModelAndView("redirect:/workingOut/customer,trainer/display.do?workingOutId=" + workingOut.getId());

			} catch (final Throwable oops) {
				result = this.createModelAndView(session, workingOutId, "session.commit.error");
			}
		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createModelAndView(final Session session) {
		ModelAndView result;

		result = this.createModelAndView(session, null, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Session session, final String messageCode) {
		ModelAndView result;

		result = this.createModelAndView(session, null, messageCode);

		return result;

	}

	protected ModelAndView createModelAndView(final Session session, final int workingOutId) {
		ModelAndView result;

		result = this.createModelAndView(session, workingOutId, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Session session, final Integer workingOutId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("session/edit");
		result.addObject("messageCode", messageCode);
		result.addObject("session", session);
		result.addObject("workingOutId", workingOutId);

		return result;

	}

}
