
package controllers.authenticated;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.ActorService;
import services.ArticleService;
import services.AuditService;
import services.CreditCardService;
import services.CurriculumService;
import services.MessageService;
import services.SocialProfileService;
import services.WorkingOutService;
import controllers.AbstractController;
import domain.Actor;
import domain.Article;
import domain.Audit;
import domain.Category;
import domain.CreditCard;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.Message;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Session;
import domain.SocialProfile;
import domain.WorkingOut;

@Controller
@RequestMapping(value = "/exportData/administrator,auditor,customer,nutritionist,trainer")
public class ExportDataMultiUserController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private WorkingOutService		workingOutService;

	@Autowired
	private AuditService			auditService;


	// Constructors -----------------------------------------------------------
	public ExportDataMultiUserController() {
		super();
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(final HttpSession session, final HttpServletResponse response) throws IOException {
		Actor actor;
		actor = this.actorService.findPrincipal();

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findSocialProfilesByActor(actor.getId());
		final Collection<Message> messagesReceived = this.messageService.findReceivedMessagesByActor(actor.getId());
		final Collection<Message> messagesSent = this.messageService.findSentMessagesByActor(actor.getId());

		String data = "Data of your user account:\r\n";
		data += "\r\n";

		data += "Name: " + actor.getName() + " \r\n" + "Surname: " + actor.getSurname() + " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: " + actor.getPhoneNumber() + " \r\n" + "Address: "
			+ actor.getAddress() + " \r\n";

		data += "\r\n\r\n";
		data += "-------------------------------------------------------------";
		data += "\r\n\r\n";

		data += "Social Profiles:\r\n";
		data += "\r\n";

		for (final SocialProfile socialProfile : socialProfiles)
			data += "Nick: " + socialProfile.getNick() + " \r\n" + "Link profile: " + socialProfile.getLinkProfile() + " \r\n" + "Social Network: " + socialProfile.getSocialNetwork() + "\r\n";

		data += "\r\n\r\n";
		data += "-------------------------------------------------------------";
		data += "\r\n\r\n";

		data += "Sent Messages:\r\n\r\n";
		Integer m = 0;
		for (final Message message : messagesSent) {
			final Collection<Actor> recipients = message.getRecipients();
			data += "Sender: " + message.getSender().getFullname() + " \r\n";
			for (final Actor recipient : recipients)
				data += "Recipients: " + recipient.getFullname() + " \r\n";
			data += "Sent Moment: " + message.getSentMoment() + " \r\n" + "Subject: " + message.getSubject() + " \r\n" + "Body: " + message.getBody() + " \r\n" + "Tags: " + message.getTags() + " \r\n";
			m++;
			if (m < messagesSent.size())
				data += "\r\n" + "......................." + "\r\n\r\n";
		}

		data += "\r\n";
		data += "-------------------------------------------------------------";
		data += "\r\n\r\n";

		data += "Received Messages:\r\n\r\n";
		Integer n = 0;
		for (final Message message : messagesReceived) {
			final Collection<Actor> recipients = message.getRecipients();
			data += "Sender: " + message.getSender().getFullname() + " \r\n";
			for (final Actor recipient : recipients)
				data += "Recipients: " + recipient.getFullname() + " \r\n";
			data += "Sent Moment: " + message.getSentMoment() + " \r\n" + "Subject: " + message.getSubject() + " \r\n" + "Body: " + message.getBody() + " \r\n" + "Tags: " + message.getTags() + " \r\n";
			n++;
			if (n < messagesReceived.size())
				data += "\r\n" + "......................." + "\r\n\r\n";
		}

		/*
		 * USER IS AUTHENTICATED AS NUTRITIONIST
		 */

		if (actor.getUserAccount().getAuthorities().toString().equals("[NUTRITIONIST]")) {
			Collection<Article> articles;

			articles = this.articleService.findArticlesByPrincipal();

			data += "\r\n";
			data += "-------------------------------------------------------------";
			data += "\r\n\r\n";

			data += "Articles:\r\n\r\n";
			Integer ss = 0;
			for (final Article article : articles) {
				data += "Published moment: " + article.getPublishedMoment() + " \r\n" + "Title: " + article.getTitle() + " \r\n" + "Description: " + article.getDescription() + " \r\n" + "Tags : " + article.getTags() + "\r\n" + "Is final mode: "
					+ article.getIsFinalMode() + " \r\n";
				ss++;
				if (ss < articles.size())
					data += "\r\n" + "......................." + "\r\n\r\n";
			}
		}

		/*
		 * USER IS AUTHENTICATED AS CUSTOMER
		 */

		if (actor.getUserAccount().getAuthorities().toString().equals("[CUSTOMER]")) {
			Collection<CreditCard> creditCards;

			creditCards = this.creditCardService.findAllByCustomer();

			data += "\r\n";
			data += "-------------------------------------------------------------";
			data += "\r\n\r\n";

			data += "Credit card:\r\n\r\n";
			Integer ss1 = 0;
			for (final CreditCard creditCard : creditCards) {
				data += "Holder name: " + creditCard.getHolderName() + " \r\n" + "Brand name: " + creditCard.getBrandName() + " \r\n" + "Number: " + creditCard.getNumber() + " \r\n" + "Expiration month: " + creditCard.getExpirationMonth() + " \r\n"
					+ "Expiration year: " + creditCard.getExpirationYear() + " \r\n" + "CVV Code: " + creditCard.getCvvCode() + " \r\n";

				ss1++;
				if (ss1 < creditCards.size())
					data += "\r\n" + "......................." + "\r\n\r\n";
			}
		}
		/*
		 * USER IS AUTHENTICATED AS TRAINER
		 */

		if (actor.getUserAccount().getAuthorities().toString().equals("[TRAINER]")) {
			Collection<WorkingOut> workingOuts;

			workingOuts = this.workingOutService.findWorkingOutsByPrincipal();

			data += "\r\n";
			data += "-------------------------------------------------------------";
			data += "\r\n\r\n";

			data += "Working-outs:\r\n\r\n";
			Integer ss2 = 0;
			for (final WorkingOut workingOut : workingOuts) {
				Collection<Category> categories;
				Collection<Session> sessions;

				categories = this.workingOutService.getCategoriesByWorkingOut(workingOut);
				sessions = this.workingOutService.getSessionsByWorkingOut(workingOut);

				data += "Ticker: " + workingOut.getTicker() + " \r\n" + "Published moment: " + workingOut.getPublishedMoment() + " \r\n" + "Description: " + workingOut.getDescription() + " \r\n" + "Price: " + workingOut.getPrice() + " \r\n"
					+ "Start moment: " + workingOut.getStartMoment() + " \r\n" + "End moment: " + workingOut.getEndMoment() + " \r\n" + "Final mode: " + workingOut.getIsFinalMode();

				data += "Categories:";
				for (final Category category : categories)
					data += " ;" + category.getName();

				data += "\r\n\r\n";
				data += "Sessions:\r\n\r\n";
				for (final Session session1 : sessions)
					data += "Title: " + session1.getTitle() + " \r\n" + "Address: " + session1.getAddress() + " \r\n" + "Description" + session1.getDescription() + " \r\n" + "Start/End moment" + session1.getStartMoment() + "/" + session1.getEndMoment()
						+ "\r\n" + "\r\n" + "\r\n";
				ss2++;
				if (ss2 < workingOuts.size())
					data += "\r\n" + "......................." + "\r\n\r\n";
			}
			data += "\r\n";
			data += "-------------------------------------------------------------";
			data += "\r\n\r\n";

			data += "Curriculum:\r\n\r\n";

			Curriculum curriculum;
			PersonalRecord personalRecord;
			Collection<EndorserRecord> endorserRecords;
			Collection<ProfessionalRecord> professionalRecords;
			Collection<EducationRecord> educationRecords;
			Collection<MiscellaneousRecord> miscellaneousRecords;

			curriculum = this.curriculumService.findByPrincipal();
			endorserRecords = curriculum.getEndorserRecords();
			professionalRecords = curriculum.getProfessionalRecords();
			educationRecords = curriculum.getEducationRecords();
			miscellaneousRecords = curriculum.getMiscellaneousRecords();
			personalRecord = curriculum.getPersonalRecord();

			data += "Ticker: " + curriculum.getTicker() + " \r\n" + "Email: " + personalRecord.getEmail() + " \r\n" + "Full name: " + personalRecord.getFullName() + " \r\n" + "LinkedLink profile: " + personalRecord.getLinkedInProfile() + " \r\n"
				+ "Phone nu,ber: " + personalRecord.getPhoneNumber() + " \r\n" + "Photo: " + personalRecord.getPhoto() + " \r\n" + "\r\n" + "\r\n";

			data += "Miscelleneous records:\r\n\r\n";
			for (final MiscellaneousRecord miscellaneousRecord : miscellaneousRecords)
				data += "Title: " + miscellaneousRecord.getTitle() + " \r\n" + "Attachments: " + miscellaneousRecord.getAttachment() + "\r\n" + "\r\n" + "\r\n";

			data += "Education records:\r\n\r\n";
			for (final EducationRecord educationRecord : educationRecords)
				data += "Diploma: " + educationRecord.getDiplomaTitle() + " \r\n" + "Attachment: " + educationRecord.getAttachment() + "\r\n" + "Institution: " + educationRecord.getInstitution() + "\r\n" + "Start/End date: "
					+ educationRecord.getStartDate() + "/" + educationRecord.getEndDate() + "\r\n" + "\r\n" + "\r\n";

			data += "Professional record:\r\n\r\n";
			for (final ProfessionalRecord professionalRecord : professionalRecords)
				data += "Attachment: " + professionalRecord.getAttachment() + " \r\n" + "Comments: " + professionalRecord.getComments() + "\r\n" + "Role: " + professionalRecord.getRole() + "\r\n" + "End date: " + professionalRecord.getStartDate() + "/"
					+ professionalRecord.getEndDate() + "\r\n" + "\r\n" + "\r\n";

			data += "Endorser record:\r\n\r\n";
			for (final EndorserRecord endorserRecord : endorserRecords)
				data += "Email: " + endorserRecord.getEmail() + " \r\n" + "Full name: " + endorserRecord.getFullname() + "\r\n" + "LinkedIn profile: " + endorserRecord.getLinkedInProfile() + "\r\n" + "Phone number: " + endorserRecord.getPhoneNumber()
					+ "\r\n" + "\r\n" + "\r\n";

			data += "\r\n" + "......................." + "\r\n\r\n";
		}

		/*
		 * USER IS AUTHENTICATED AS AUDITOR
		 */

		if (actor.getUserAccount().getAuthorities().toString().equals("[AUDITOR]")) {
			final Collection<Audit> audits;

			audits = this.auditService.findAllByPrincipal();

			data += "\r\n";
			data += "-------------------------------------------------------------";
			data += "\r\n\r\n";

			data += "Audits:\r\n\r\n";
			Integer ss5 = 0;
			for (final Audit audit : audits) {
				data += "Title: " + audit.getTitle() + " \r\n" + "Moment: " + audit.getMoment() + " \r\n" + "Attachment: " + audit.getAttachments() + " \r\n" + "Description: " + audit.getDescription() + " \r\n" + "Curriculum name personal"
					+ audit.getCurriculum().getPersonalRecord().getFullName() + " \r\n";

				ss5++;
				if (ss5 < audits.size())
					data += "\r\n" + "......................." + "\r\n\r\n";
			}

		}
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=data_user_account.txt");
		final ServletOutputStream out = response.getOutputStream();
		out.println(data);
		out.flush();
		out.close();

	}
}
