
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Application;
import domain.Article;
import domain.Box;
import domain.Message;
import domain.WorkingOut;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under testing -----------------------------------
	@Autowired
	private MessageService		messageService;

	// Other services ------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private ArticleService		articleService;


	// Suite test

	/*
	 * A: Requirement 8.3 (An authenticated user can display his or her messages).
	 * B: An user try to display a message that he or she hasn't sent or hasn't received.
	 * C: Analysis of sentence coverage: 16/17 -> 94.12% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void findOneToDisplay_negativeTest_dos() {
		super.authenticate("admin1");

		int messageId;
		Message message;

		// sender: customer1.
		// recipients: trainer 1,2,3,4 and 5.
		messageId = super.getEntityId("message1");
		message = this.messageService.findOneToDisplay(messageId);

		Assert.isNull(message);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can display his or her messages).
	 * C: Analysis of sentence coverage: 17/17 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findOneToDisplay_positiveTest() {
		super.authenticate("customer1");

		int messageId;
		Message message;

		messageId = super.getEntityId("message1");
		message = this.messageService.findOneToDisplay(messageId);

		Assert.notNull(message);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can list his or her messages).
	 * C: Analysis of sentence coverage: 6/6 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findMessageByActor_positiveTest() {
		super.authenticate("trainer1");

		int actorId;
		Collection<Message> sentMessages, receivedMessages;

		actorId = super.getEntityId("trainer1");

		sentMessages = this.messageService.findSentMessagesByActor(actorId);
		receivedMessages = this.messageService.findReceivedMessagesByActor(actorId);

		Assert.isTrue(sentMessages.size() == 0);
		Assert.isTrue(receivedMessages.size() == 6);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can create a message).
	 * C: Analysis of sentence coverage: 22/22 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void create_positiveTest() {
		super.authenticate("admin1");

		Message message;

		message = this.messageService.create();

		Assert.notNull(message);
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipients());
		Assert.notNull(message.getSentMoment());
		Assert.isNull(message.getBody());
		Assert.isNull(message.getSubject());
		Assert.isNull(message.getTags());
		Assert.isNull(message.getPriority());
		Assert.isTrue(!message.getIsSpam());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can send a message).
	 * B: An user try to edit a message.
	 * C: Analysis of sentence coverage: 2/83 -> 2.41% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void send_negativeTest_uno() {
		super.authenticate("customer1");

		final int messageId = super.getEntityId("message1");
		Message message = null, sent;

		message = this.messageService.findOne(messageId);
		message.setBody("Body edited");
		message.setSubject("Subject edited");

		sent = this.messageService.send(message);

		Assert.isNull(sent);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can send a message).
	 * B: An user try to send a message with an invalid priority.
	 * C: Analysis of sentence coverage: 25/83 -> 30.12% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void send_negativeTest_dos() {
		super.authenticate("customer1");

		int actorId;
		Message message, sent;
		List<Actor> recipients;
		Actor actor;

		actorId = super.getEntityId("trainer1");
		actor = this.actorService.findOne(actorId);

		recipients = new ArrayList<Actor>();
		recipients.add(actor);

		message = this.messageService.create();
		message.setRecipients(recipients);
		message.setBody("Test body");
		message.setSubject("Test subject");
		message.setPriority("MEDIUM");
		message.setTags("TEST");

		sent = this.messageService.send(message);

		Assert.isNull(sent);

		super.unauthenticate();
	}

	@Test
	public void driverSend() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::subject is null => 1/12 -> 8.33%.
			 */
			{
				null, "¿Que tal?", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::subject is a empty string => 1/12 -> 8.33%.
			 */
			{
				"", "¿Que tal?", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::subject is a malicious script => 1/12 -> 8.33%.
			 */
			{
				"<script> Alert('HACKED'); </script>", "¿Que tal?", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::body.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::body is null => 1/12 -> 8.33%.
			 */
			{
				"Saludos", null, "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::body.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::body is a empty string => 1/12 -> 8.33%.
			 */
			{
				"Saludos", "", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::body.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::body is a malicious script => 1/12 -> 8.33%.
			 */
			{
				"saludos", "<script> Alert('HACKED'); </script>", "", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * B: Invalid data in Message::subject.
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Message::tags is a malicious script => 1/12 -> 8.33%.
			 */
			{
				"saludos", "¿Que tal?", "<script> Alert('HACKED'); </script>", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
			 */
			{
				"Saludos", "¿Que tal?", "", null
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
			 */
			{
				"Saludos", "viagra, Sex, etc", "", null
			},
			/*
			 * A: Requirement 8.3 (An authenticated user can send a message).
			 * C: Analysis of sentence coverage: 77/83 -> 92.77% of executed lines codes .
			 * D: Analysis of data coverage: Every attribute has a valid value => 12/12 -> 100.00%.
			 */
			{
				"Saludos", "¿Que tal?", "PRIMERIZO", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSend((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void templateSend(final String subject, final String body, final String tags, final Class<?> expected) {
		Class<?> caught;
		Message message, saved;
		List<Actor> recipients;
		Actor actor_one, actor_two, actor_three;
		int actor_uno, actor_dos, actor_tres;
		actor_uno = super.getEntityId("trainer1");
		actor_dos = super.getEntityId("trainer2");
		actor_tres = super.getEntityId("trainer3");

		actor_one = this.actorService.findOne(actor_uno);
		actor_two = this.actorService.findOne(actor_dos);
		actor_three = this.actorService.findOne(actor_tres);

		recipients = new ArrayList<Actor>();
		recipients.add(actor_one);
		recipients.add(actor_two);
		recipients.add(actor_three);

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("admin1");

			message = this.messageService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setTags(tags);
			message.setPriority("LOW");
			message.setRecipients(recipients);

			saved = this.messageService.send(message);
			this.messageService.flush();

			Assert.notNull(saved);
			Assert.isTrue(saved.getId() != 0);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can delete a message).
	 * B: An user try to delete a message from a box that doens't contain this message.
	 * C: Analysis of sentence coverage: 4/64 -> 6.25% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_deleteTest_uno() {
		super.authenticate("customer1");

		final int boxId = super.getEntityId("box80");
		final Box box = this.boxService.findOne(boxId);

		final int messageId = super.getEntityId("message1");
		final Message message = this.messageService.findOne(messageId);

		this.messageService.delete(message, box);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.3 (An authenticated user can delete a message).
	 * B: An user try to delete a message from a box that doens't belong to him/her.
	 * C: Analysis of sentence coverage: 30/64 -> 46.88% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_deleteTest_dos() {
		super.authenticate("trainer1");

		final int boxId = super.getEntityId("box81");
		final Box box = this.boxService.findOne(boxId);

		final int messageId = super.getEntityId("message1");
		final Message message = this.messageService.findOne(messageId);

		this.messageService.delete(message, box);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 11.3 (An administrator can broadcast a message to all the actors of the system).
	 * C: Analysis of sentence coverage: 43/43 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void createBroadcast_positiveTest() {
		super.authenticate("admin1");

		Message message;

		message = this.messageService.createBroadcast();

		Assert.notNull(message);
		Assert.notNull(message.getSender());
		Assert.notEmpty(message.getRecipients());
		Assert.notNull(message.getSentMoment());
		Assert.isNull(message.getBody());
		Assert.isNull(message.getSubject());
		Assert.isNull(message.getTags());
		Assert.isNull(message.getPriority());
		Assert.isTrue(!message.getIsSpam());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 11.3 (An administrator can broadcast a message to all the actors of the system).
	 * B: The message is null
	 * C: Analysis of sentence coverage: 1/71 -> 1.41% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void sendBroadcast_negativeTest_uno() {
		super.authenticate("admin1");

		Message message, saved;

		message = null;

		saved = this.messageService.sendBroadcast(message);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 11.3 (An administrator can broadcast a message to all the actors of the system).
	 * B: An administrator try to edit a broadcast message.
	 * C: Analysis of sentence coverage: 2/71 -> 2.82% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void sendBroadcast_negativeTest_dos() {
		super.authenticate("admin1");

		Message message, saved, edited;

		message = this.messageService.createBroadcast();
		message.setSubject("Test broadcast");
		message.setBody("body broadcast");
		message.setTags("TEST");
		message.setPriority("LOW");

		saved = this.messageService.sendBroadcast(message);
		this.messageService.flush();

		Assert.notNull(saved);

		saved.setBody("Edited body broadcast");
		edited = this.messageService.sendBroadcast(saved);

		Assert.isNull(edited);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 11.3 (An administrator can broadcast a message to all the actors of the system).
	 * C: Analysis of sentence coverage: 67/71 -> 94.37% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void sendBroadcast_positiveTest() {
		super.authenticate("admin1");

		Message message, saved;

		message = this.messageService.createBroadcast();
		message.setSubject("Test broadcast");
		message.setBody("body broadcast");
		message.setTags("TEST");
		message.setPriority("LOW");

		saved = this.messageService.sendBroadcast(message);

		Assert.notNull(saved);
		Assert.isTrue(saved.getId() != 0);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 2 (A message may be stored in several boxes).
	 * B: The principal hasn't right about this message
	 * C: Analysis of sentence coverage: 18/36 -> 50.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void move_negativeTest_uno() {
		super.authenticate("admin1");

		int messageId, originId, destinationId;

		Message message;
		Box origin, destination;

		messageId = super.getEntityId("message1");
		originId = super.getEntityId("box81");
		destinationId = super.getEntityId("box82");

		origin = this.boxService.findOne(originId);
		destination = this.boxService.findOne(destinationId);
		message = this.messageService.findOne(messageId);

		this.messageService.moveMessage(message, origin, destination);

		Assert.isTrue(destination.getMessages().contains(message));
		Assert.isTrue(!origin.getMessages().contains(message));

		super.unauthenticate();
	}

	/*
	 * A: Requirement 2 (A message may be stored in several boxes).
	 * B:
	 * C: Analysis of sentence coverage: 28/36 -> 77.78% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void move_negativeTest_dos() {
		super.authenticate("trainer1");

		int messageId, originId, destinationId;

		Message message;
		Box origin, destination;

		messageId = super.getEntityId("message1");
		originId = super.getEntityId("box81");
		destinationId = super.getEntityId("box82");

		origin = this.boxService.findOne(originId);
		destination = this.boxService.findOne(destinationId);
		message = this.messageService.findOne(messageId);

		this.messageService.moveMessage(message, origin, destination);

		Assert.isTrue(destination.getMessages().contains(message));
		Assert.isTrue(!origin.getMessages().contains(message));

		super.unauthenticate();
	}

	/*
	 * A: Requirement 51 (The system must generate an automatic notification when an application changes its status).
	 * C: Analysis of sentence coverage: 47/47 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void notificationApplication_positiveTest() {
		int applicationId;
		Application application;
		Message notification;

		applicationId = super.getEntityId("application1");
		application = this.applicationService.findOne(applicationId);

		notification = this.messageService.notification_applicationStatusChanges(application);

		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);
	}

	/*
	 * A: Requirement 51 (The system must generate an automatic notification when a working-out is published).
	 * C: Analysis of sentence coverage: 45/45 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void notificationWorkingOut_positiveTest() {
		int workingOutId;
		WorkingOut workingOut;
		Message notification;

		workingOutId = super.getEntityId("workingOut1");
		workingOut = this.workingOutService.findOne(workingOutId);

		notification = this.messageService.notification_publishedWorkingOut(workingOut);

		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);
	}

	/*
	 * A: Requirement 51 (The system must generate an automatic notification when a nutritionist writes an article).
	 * C: Analysis of sentence coverage: 55/55 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void notificationArticle_positiveTest() {
		int articleId;
		Article article;
		Message notification;

		articleId = super.getEntityId("article1");
		article = this.articleService.findOne(articleId);

		notification = this.messageService.notification_newArticle(article);

		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);
	}

}
