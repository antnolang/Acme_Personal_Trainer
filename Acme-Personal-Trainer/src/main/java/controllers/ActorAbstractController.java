
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Trainer;

@Controller
public class ActorAbstractController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ApplicationService	applicationService;


	// Main methods -----------------------------------------------------------

	// Display --------------------------------------------------------------------

	public ModelAndView display(final Integer actorId) {
		ModelAndView result;
		Actor actor, principal;

		actor = null;
		principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final Throwable oops) {

		}
		result = new ModelAndView("actor/display");

		if (actorId == null) {
			actor = this.actorService.findPrincipal();
			result.addObject("isAuthorized", true);
			result.addObject("isActorLogged", true);
		} else {
			actor = this.actorService.findOne(actorId);
			result.addObject("isAuthorized", false);
			if (actor instanceof Administrator && actorId == principal.getId())
				actor = this.actorService.findOneToDisplayEdit(actorId);
			else if (actor instanceof Administrator && actorId != principal.getId())
				throw new IllegalArgumentException();
			if (actor instanceof Trainer && principal instanceof Customer && this.applicationService.existApplicationAcceptedBetweenCustomerTrainer(principal.getId(), actor.getId()) == true)
				result.addObject("trainerAttended", true);
		}

		if (principal != null && actor != null && principal == actor) {
			result.addObject("isActorLogged", true);
			result.addObject("isAuthorized", true);
		}

		result.addObject("actor", actor);

		return result;
	}
	// Ancillary methods ------------------------------------------------------

}
