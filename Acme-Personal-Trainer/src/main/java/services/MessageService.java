
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Application;
import domain.Article;
import domain.Box;
import domain.Customisation;
import domain.Message;
import domain.WorkingOut;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------------------------------
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services -------------------------------------------
	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private CustomerService			customerService;


	//Constructor ----------------------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ------------------------
	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}

	public Message findOneToDisplay(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);

		Assert.notNull(result);
		this.checkSenderOrRecipient(result);

		return result;
	}

	public Message findOneToMove(final int messageId, final int originBoxId) {
		Message result;
		Box origin;

		result = this.messageRepository.findOne(messageId);
		origin = this.boxService.findOne(originBoxId);

		Assert.notNull(result);
		this.checkSenderOrRecipient(result);
		this.boxService.checkByPrincipal(origin);

		return result;
	}

	public Collection<Message> findSentMessagesByActor(final int actorId) {
		Collection<Message> results;

		results = this.messageRepository.findSentMessagesByActor(actorId);

		return results;
	}

	public Collection<Message> findReceivedMessagesByActor(final int actorId) {
		Collection<Message> results;

		results = this.messageRepository.findReceivedMessagesByActor(actorId);

		return results;
	}

	public Message create() {
		Message result;
		Actor principal;
		Date current_moment;

		principal = this.actorService.findPrincipal();
		current_moment = this.utilityService.current_moment();

		result = new Message();
		result.setSender(principal);
		result.setRecipients(Collections.<Actor> emptySet());
		result.setSentMoment(current_moment);

		return result;
	}

	public Message createBroadcast() {
		Message result;
		Actor principal;
		Collection<Actor> recipients;

		principal = this.actorService.findPrincipal();
		recipients = this.actorService.findAll();
		recipients.remove(principal);

		result = this.create();
		result.setRecipients(recipients);

		return result;
	}

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

	public Message send(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		this.checkByPrincipal(message);
		this.checkPriority(message);

		Message result;
		Box inBoxRecipient, outBoxSender, spamBoxRecipient;
		boolean isSpam;

		result = this.messageRepository.save(message);

		outBoxSender = this.boxService.findOutBoxFromActor(result.getSender().getId());

		this.boxService.addMessage(outBoxSender, result);

		isSpam = this.messageIsSpam(result);

		if (isSpam) {
			result.setIsSpam(true);
			for (final Actor recipient : result.getRecipients()) {
				spamBoxRecipient = this.boxService.findSpamBoxFromActor(recipient.getId());

				this.boxService.addMessage(spamBoxRecipient, result);
			}
		} else
			for (final Actor recipient : result.getRecipients()) {
				inBoxRecipient = this.boxService.findInBoxFromActor(recipient.getId());

				this.boxService.addMessage(inBoxRecipient, result);
			}

		return result;
	}

	public void delete(final Message message, final Box box) {
		Assert.notNull(message);
		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0 && this.messageRepository.exists(message.getId()));
		Assert.isTrue(box.getMessages().contains(message));
		this.checkSenderOrRecipient(message);
		this.boxService.checkByPrincipal(box);

		Actor principal;
		final Box trashBox;
		List<Box> boxes;
		Integer numberBoxesWithMessage;

		principal = this.actorService.findPrincipal();
		trashBox = this.boxService.findTrashBoxFromActor(principal.getId());

		if (trashBox.equals(box)) {
			boxes = new ArrayList<Box>(this.boxService.findBoxesFromActorThatContaintsAMessage(principal.getId(), message.getId()));
			for (final Box b : boxes)
				this.boxService.removeMessage(b, message);

			//this.boxService.removeMessage(trashBox, message);

		} else {
			this.boxService.removeMessage(box, message);
			this.boxService.addMessage(trashBox, message);
		}

		numberBoxesWithMessage = this.boxService.numberOfBoxesThatContaintAMessage(message.getId());
		if (numberBoxesWithMessage == 0)
			this.messageRepository.delete(message);
	}

	// Other business methods ---------------------
	public void moveMessage(final Message message, final Box origin, final Box destination) {
		Assert.notNull(message);
		Assert.notNull(origin);
		Assert.notNull(destination);
		Assert.isTrue(origin.getId() != 0 && destination.getId() != 0 && this.messageRepository.exists(message.getId()));
		Assert.isTrue(origin.getMessages().contains(message) && !destination.getMessages().contains(message));
		this.checkSenderOrRecipient(message);
		this.boxService.checkByPrincipal(origin);
		this.boxService.checkByPrincipal(destination);

		this.boxService.addMessage(destination, message);
		this.boxService.removeMessage(origin, message);
	}

	public Message sendBroadcast(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		this.checkByPrincipal(message);

		Message result;
		boolean isSpam;
		Actor principal;
		Collection<Actor> recipients;
		Box outBoxSender, notificationBoxRecipient, spamBoxRecipient;

		principal = this.actorService.findPrincipal();

		recipients = this.actorService.findAll();
		recipients.remove(principal);

		message.setRecipients(recipients);

		result = this.messageRepository.save(message);

		outBoxSender = this.boxService.findOutBoxFromActor(result.getSender().getId());

		this.boxService.addMessage(outBoxSender, result);

		recipients = result.getRecipients();
		isSpam = this.messageIsSpam(result);
		if (isSpam) {
			result.setIsSpam(true);
			for (final Actor a : recipients) {
				spamBoxRecipient = this.boxService.findSpamBoxFromActor(a.getId());

				this.boxService.addMessage(spamBoxRecipient, result);
			}
		} else
			for (final Actor a : recipients) {
				notificationBoxRecipient = this.boxService.findNotificationBoxFromActor(a.getId());

				this.boxService.addMessage(notificationBoxRecipient, result);
			}

		return result;
	}


	@Autowired
	private Validator	validator;


	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		Collection<Actor> recipients;

		result = this.create();
		result.setSubject(message.getSubject());
		result.setBody(message.getBody());
		result.setPriority(message.getPriority());
		result.setTags(message.getTags());

		recipients = new ArrayList<>();
		if (message.getRecipients() != null)
			recipients.addAll(message.getRecipients());

		result.setRecipients(recipients);

		this.validator.validate(result, binding);

		return result;
	}

	// This method id used when an actor want to delete all his or her data.
	public void deleteMessagesFromActor(final Actor actor) {
		Collection<Message> sentMessages, receivedMessages;
		Collection<Box> boxes;

		sentMessages = this.findSentMessagesByActor(actor.getId());
		for (final Message m1 : sentMessages) {
			boxes = this.boxService.findBoxesByMessage(m1.getId());
			for (final Box b : boxes)
				this.boxService.removeMessage(b, m1);

			this.messageRepository.delete(m1);
		}

		receivedMessages = this.findReceivedMessagesByActor(actor.getId());
		for (final Message m2 : receivedMessages)
			// If the message has as unique recipient the actor, then the message
			// is deleted
			if (m2.getRecipients().size() == 1) {
				boxes = this.boxService.findBoxesByMessage(m2.getId());
				for (final Box b : boxes)
					this.boxService.removeMessage(b, m2);

				this.messageRepository.delete(m2);
			} else
				m2.getRecipients().remove(actor);
	}

	public Message breachNotification() {
		Message message, result;
		List<Actor> recipients;
		Box notificationBox, outBox;
		String subject, body;

		recipients = new ArrayList<Actor>();
		recipients.addAll(this.actorService.findAll());

		subject = "Breach notification / Notificación de brecha de seguridad";
		body = "Dear valued user, we regret to inform you that your data has been exposed. Urge you to remain vigilant /" + "Apreciado usuario, lamentamos informarle de que sus datos han sido expuestos. Le instamos a estar alerta.";

		message = this.create();
		message.setSubject(subject);
		message.setBody(body);
		message.setPriority("HIGH");
		message.setRecipients(recipients);

		result = this.messageRepository.save(message);

		outBox = this.boxService.findOutBoxFromActor(result.getSender().getId());
		this.boxService.addMessage(outBox, result);

		for (final Actor a : recipients) {
			notificationBox = this.boxService.findNotificationBoxFromActor(a.getId());

			this.boxService.addMessage(notificationBox, result);
		}

		return result;
	}

	protected Message notification_applicationStatusChanges(final Application application) {
		Message notification, result;
		Actor customer, trainer;
		List<Actor> recipients;
		String subject, body, ticker, status;
		Box outBoxSender, notificationBoxRecipient;

		customer = application.getCustomer();
		trainer = application.getWorkingOut().getTrainer();

		recipients = new ArrayList<Actor>();
		recipients.add(customer);
		recipients.add(trainer);

		ticker = application.getWorkingOut().getTicker();
		status = application.getStatus();

		subject = "Application notification / Notificación de solicitud";
		body = "The application related to working-out whose ticker is " + ticker + " has been " + status + ". / La solicitud relacionada con el programa de entrenamiento cuyo ticker es " + ticker + " has sido " + status + ".";

		notification = this.createNotification(recipients, subject, body);

		result = this.messageRepository.save(notification);

		outBoxSender = this.boxService.findOutBoxFromActor(result.getSender().getId());
		this.boxService.addMessage(outBoxSender, result);

		for (final Actor recipient : recipients) {
			notificationBoxRecipient = this.boxService.findNotificationBoxFromActor(recipient.getId());
			this.boxService.addMessage(notificationBoxRecipient, result);
		}

		return result;
	}

	protected Message notification_publishedWorkingOut(final WorkingOut workingOut) {
		Message notification;
		final Message result;
		List<Actor> recipients;
		String subject, body, ticker;
		Box outBoxSender, notificationBoxRecipient;

		recipients = new ArrayList<Actor>(this.customerService.findAll());

		ticker = workingOut.getTicker();

		subject = "Working-out notification. / Notificación de programa de entrenamiento.";
		body = "A working-out whose ticker is " + ticker + " has been published. / Un programa de entrenamiento cuyo ticker es " + " ha sido publicado.";

		notification = this.createNotification(recipients, subject, body);

		result = this.messageRepository.save(notification);

		outBoxSender = this.boxService.findOutBoxFromActor(result.getSender().getId());
		this.boxService.addMessage(outBoxSender, result);

		for (final Actor recipient : recipients) {
			notificationBoxRecipient = this.boxService.findNotificationBoxFromActor(recipient.getId());
			this.boxService.addMessage(notificationBoxRecipient, result);
		}

		return result;
	}

	protected Message notification_newArticle(final Article article) {
		Message notification;
		final Message result;
		List<Actor> recipients;
		String subject, body, fullname, title;
		Box outBoxSender, notificationBoxRecipient;

		recipients = new ArrayList<Actor>(this.customerService.findPremiumCustomers());

		fullname = article.getNutritionist().getFullname();
		title = article.getTitle();

		subject = "New article. / Nuevo artículo.";
		body = "There is a new article written by " + fullname + " and whose title is " + title + " / Un nuevo artículo ha sido escrito por " + fullname + " y cuyo título es " + title;

		notification = this.createNotification(recipients, subject, body);

		result = this.messageRepository.save(notification);

		outBoxSender = this.boxService.findOutBoxFromActor(result.getSender().getId());
		this.boxService.addMessage(outBoxSender, result);

		for (final Actor recipient : recipients) {
			notificationBoxRecipient = this.boxService.findNotificationBoxFromActor(recipient.getId());
			this.boxService.addMessage(notificationBoxRecipient, result);
		}

		return result;

	}

	protected Double numberSpamMessagesSentByActor(final int actorId) {
		Double result;

		result = this.messageRepository.numberSpamMessagesSentByActor(actorId);

		return result;
	}

	protected Double numberMessagesSentByActor(final int actorId) {
		Double result;

		result = this.messageRepository.numberMessagesSentByActor(actorId);

		return result;
	}

	// Private methods --------------------------------------
	private void checkByPrincipal(final Message message) {
		Actor principal;

		principal = this.actorService.findPrincipal();

		Assert.isTrue(message.getSender().equals(principal));
	}

	private void checkPriority(final Message message) {
		Customisation customisation;

		customisation = this.customisationService.find();

		Assert.isTrue(customisation.getPriorities().contains(message.getPriority()));
	}

	private void checkSenderOrRecipient(final Message message) {
		Actor principal;

		principal = this.actorService.findPrincipal();

		Assert.isTrue(message.getSender().equals(principal) || message.getRecipients().contains(principal));
	}

	private boolean messageIsSpam(final Message message) {
		String spam_words;
		List<String> spamWords;
		Customisation customisation;
		String text;
		boolean result;

		customisation = this.customisationService.find();

		spam_words = customisation.getSpamWords();
		spamWords = this.utilityService.ListByString(spam_words);

		text = message.getSubject() + " " + message.getBody();

		result = false;
		for (final String s : spamWords)
			if (text.toLowerCase().contains(s.toLowerCase())) {
				result = true;
				break;
			}

		return result;
	}

	public Integer validateDestinationBox(final MessageForm messageForm, final String language, final BindingResult binding) {
		Integer result;

		result = messageForm.getDestinationBoxId();

		if (result == null || result == 0)
			if (language.equals("en"))
				binding.rejectValue("destinationBoxId", "message.error.null", "Must not be null");
			else
				binding.rejectValue("destinationBoxId", "message.error.null", "No debe ser nulo");

		return result;
	}

}
