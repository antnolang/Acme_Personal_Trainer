
package controllers.trainer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SessionService;
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
			this.workingOutService.findToCreateSession(workingOutId);
			session = this.sessionService.create();

			result = this.createModelAndView(session, workingOutId);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sessionId) {
		ModelAndView result;
		Session session;
		int workingOutId;

		try {
			session = this.sessionService.findOneToEdit(sessionId);
			workingOutId = this.workingOutService.findBySession(sessionId).getId();

			result = this.createModelAndView(session, workingOutId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Session session, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		WorkingOut workingOut;
		Integer workingOutId;
		String paramWorkingOutId;
		Session sessionRec;
		String messageError;

		paramWorkingOutId = request.getParameter("workingOutId");
		workingOutId = paramWorkingOutId.isEmpty() ? null : Integer.parseInt(paramWorkingOutId);
		sessionRec = this.sessionService.reconstruct(session, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(session, workingOutId);
		else
			try {
				workingOut = this.workingOutService.findOneToCreateSession(workingOutId);

				this.sessionService.save(sessionRec, workingOut);
				result = new ModelAndView("redirect:/workingOut/customer,trainer/display.do?workingOutId=" + workingOut.getId());

			} catch (final Throwable oops) {
				if (oops.getMessage().contains("End moment last session before star moment"))
					messageError = "session.save.error";
				else if (oops.getMessage().contains("Start moment before end moment"))
					messageError = "session.save.error1";
				else if (oops.getMessage().contains("Start moment in the future"))
					messageError = "session.save.error2";
				else
					messageError = "session.commit.error";
				result = this.createModelAndView(session, workingOutId, messageError);

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Session session, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Session sessionBbdd;
		Integer workingOutId;
		String paramWorkingOutId;

		paramWorkingOutId = request.getParameter("workingOutId");
		workingOutId = paramWorkingOutId.isEmpty() ? null : Integer.parseInt(paramWorkingOutId);
		try {

			sessionBbdd = this.sessionService.findOneToEdit(session.getId());
			this.sessionService.delete(sessionBbdd);
			result = new ModelAndView("redirect:../../workingOut/trainer/list.do");
		} catch (final Throwable oops) {
			result = this.createModelAndView(session, workingOutId, "session.delete.error");
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
