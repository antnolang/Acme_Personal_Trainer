
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Application;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------------------------------
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services -------------------------------------------
	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private AdministratorService	administratorService;


	//Constructor ----------------------------------------------------

	// Reconstruct ----------------------------------------------

	// Other business methods ---------------------

	protected Message notification_applicationStatusChanges(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);

		Message notification, result;
		Actor customer, trainer;
		List<Actor> recipients;
		String subject, body, ticker, status;

		customer = application.getCustomer();
		trainer = application.getWorkingOut().getTrainer();

		recipients = new ArrayList<Actor>();
		recipients.add(customer);
		recipients.add(trainer);

		ticker = application.getWorkingOut().getTicker();
		status = application.getStatus();

		subject = "Application notification / Notificación de solicitud";
		body = "The application related to workingOut whose ticker is " + ticker + " has been " + status + ". / La solicitud relacionada con el entrenamiento cuyo ticker es " + ticker + " has sido " + status + ".";

		notification = this.createNotification(recipients, subject, body);

		result = this.messageRepository.save(notification);

		return result;
	}

	// Private methods --------------------------------------
	private Message createNotification(final Collection<Actor> recipients, final String subject, final String body) {
		Message result;
		Date current_moment;
		Actor system;

		current_moment = this.utilityService.current_moment();
		system = this.administratorService.findSystem();

		result = new Message();
		result.setSender(system);
		result.setRecipients(recipients);
		result.setSentMoment(current_moment);
		result.setBody(body);
		result.setSubject(subject);
		result.setTags("SYSTEM");

		return result;
	}
}
