//
// package controllers.authenticated;
//
// import java.io.IOException;
// import java.util.Collection;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
//
// import services.ActorService;
// import services.AuditService;
// import services.AuditorService;
// import services.CurriculumService;
// import services.ItemService;
// import services.MessageService;
// import services.PositionService;
// import services.ProblemService;
// import services.ProviderService;
// import services.SocialProfileService;
// import services.SponsorshipService;
// import controllers.AbstractController;
// import domain.Actor;
// import domain.Audit;
// import domain.Auditor;
// import domain.Curriculum;
// import domain.EducationData;
// import domain.Item;
// import domain.Message;
// import domain.MiscellaneousData;
// import domain.PersonalData;
// import domain.Position;
// import domain.PositionData;
// import domain.Problem;
// import domain.Provider;
// import domain.SocialProfile;
// import domain.Sponsorship;
//
// @Controller
// @RequestMapping(value = "/exportData/administrator,company,rookie")
// public class ExportDataMultiUserController extends AbstractController {
//
// @Autowired
// private ActorService actorService;
//
// @Autowired
// private SocialProfileService socialProfileService;
//
// @Autowired
// private MessageService messageService;
//
// @Autowired
// private ProblemService problemService;
//
// @Autowired
// private PositionService positionService;
//
// @Autowired
// private CurriculumService curriculumService;
//
// @Autowired
// private AuditService auditService;
//
// @Autowired
// private AuditorService auditorService;
//
// @Autowired
// private ProviderService providerService;
//
// @Autowired
// private SponsorshipService sponsorshipService;
//
// @Autowired
// private ItemService itemService;
//
//
// // Constructors -----------------------------------------------------------
// public ExportDataMultiUserController() {
// super();
// }
//
// @RequestMapping(value = "/export", method = RequestMethod.GET)
// public void export(final HttpSession session, final HttpServletResponse response) throws IOException {
// Actor actor;
// actor = this.actorService.findPrincipal();
//
// final Collection<SocialProfile> socialProfiles = this.socialProfileService.findSocialProfilesByActor(actor.getId());
// final Collection<Message> messagesReceived = this.messageService.findReceivedMessagesOrderByTags(actor.getId());
// final Collection<Message> messagesSent = this.messageService.findSentMessagesOrderByTags(actor.getId());
// String data = "Data of your user account:\r\n";
// data += "\r\n";
//
// data += "Name: " + actor.getName() + " \r\n" + "Surname: " + actor.getSurname() + " \r\n" + "VAT number: " + actor.getVATnumber() + " \r\n" + "Photo: " + actor.getPhoto() + " \r\n" + "Email: " + actor.getEmail() + " \r\n" + "Phone Number: "
// + actor.getPhoneNumber() + " \r\n" + "Address: " + actor.getAddress() + " \r\n";
//
// data += "\r\n\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Data of your credit card:\r\n";
// data += "\r\n";
//
// data += "Holder name: " + actor.getCreditCard().getHolder() + " \r\n" + "Make: " + actor.getCreditCard().getMake() + " \r\n" + "Number: " + actor.getCreditCard().getNumber() + " \r\n" + "Expiration month: "
// + actor.getCreditCard().getExpirationMonth() + " \r\n" + "Expiration year: " + actor.getCreditCard().getExpirationYear() + " \r\n" + "CVV Code: " + actor.getCreditCard().getCvvCode() + " \r\n";
//
// data += "\r\n\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Social Profiles:\r\n";
// data += "\r\n";
//
// for (final SocialProfile socialProfile : socialProfiles)
// data += "Nick: " + socialProfile.getNick() + " \r\n" + "Link profile: " + socialProfile.getLinkProfile() + " \r\n" + "Social Network: " + socialProfile.getSocialNetwork() + "\r\n";
//
// data += "\r\n\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Sent Messages:\r\n\r\n";
// Integer m = 0;
// for (final Message message : messagesSent) {
// final Collection<Actor> recipients = message.getRecipients();
// data += "Sender: " + message.getSender().getFullname() + " \r\n";
// for (final Actor recipient : recipients)
// data += "Recipients: " + recipient.getFullname() + " \r\n";
// data += "Sent Moment: " + message.getSentMoment() + " \r\n" + "Subject: " + message.getSubject() + " \r\n" + "Body: " + message.getBody() + " \r\n" + "Tags: " + message.getTags() + " \r\n";
// m++;
// if (m < messagesSent.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Received Messages:\r\n\r\n";
// Integer n = 0;
// for (final Message message : messagesReceived) {
// final Collection<Actor> recipients = message.getRecipients();
// data += "Sender: " + message.getSender().getFullname() + " \r\n";
// for (final Actor recipient : recipients)
// data += "Recipients: " + recipient.getFullname() + " \r\n";
// data += "Sent Moment: " + message.getSentMoment() + " \r\n" + "Subject: " + message.getSubject() + " \r\n" + "Body: " + message.getBody() + " \r\n" + "Tags: " + message.getTags() + " \r\n";
// n++;
// if (n < messagesReceived.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// if (actor.getUserAccount().getAuthorities().toString().equals("[COMPANY]")) {
// Collection<Position> positions;
// Collection<Problem> problems;
//
// positions = this.positionService.findPositionByPrincipal();
// problems = this.problemService.findProblemsByPrincipal();
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Positions:\r\n\r\n";
// Integer ss = 0;
// for (final Position position : positions) {
// data += "Ticker: " + position.getTicker() + " \r\n" + "Title: " + position.getTitle() + " \r\n" + "Description: " + position.getDescription() + " \r\n" + "Deadline : " + position.getDeadline() + "\r\n" + "Profile: " + position.getProfile()
// + " \r\n" + "Skills: " + position.getSkills() + " \r\n" + "Technologies: " + position.getTechnologies() + " \r\n" + "Salary: " + position.getSalary() + " \r\n";
// ss++;
// if (ss < positions.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Problems:\r\n\r\n";
// Integer ss1 = 0;
// for (final Problem problem : problems) {
// data += "Title: " + problem.getTitle() + " \r\n" + "Statement: " + problem.getAttachments() + " \r\n" + "Hint: " + problem.getHint() + " \r\n" + "Attachment : " + problem.getAttachments() + "\r\n";
// ss1++;
// if (ss1 < problems.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
//
// }
//
// }
//
// if (actor.getUserAccount().getAuthorities().toString().equals("[ROOKIE]")) {
// Collection<Curriculum> curricula;
//
// curricula = this.curriculumService.originalCurriculaByPrincipal();
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Curricula:\r\n\r\n";
// Integer ss = 0;
// for (final Curriculum curriculum : curricula) {
// PersonalData personalData;
// Collection<MiscellaneousData> miscellaneousDatas;
// Collection<EducationData> educationDatas;
// Collection<PositionData> positionDatas;
//
// personalData = curriculum.getPersonalData();
// miscellaneousDatas = curriculum.getMiscellaneousDatas();
// educationDatas = curriculum.getEducationDatas();
// positionDatas = curriculum.getPositionDatas();
//
// data += "Title: " + curriculum.getTitle() + " \r\n" + "Full name: " + personalData.getFullname() + " \r\n" + "LinkedIn profile: " + personalData.getLinkedInProfile() + " \r\n" + "Phone number: " + personalData.getPhoneNumber() + " \r\n"
// + "Statement: " + personalData.getStatement() + " \r\n" + "Github profile: " + personalData.getGithubProfile() + " \r\n" + "\r\n" + "\r\n";
// data += "Full name: " + personalData.getFullname() + " \r\n";
//
// data += "Miscelleneous datas:\r\n\r\n";
// for (final MiscellaneousData miscellaneousData : miscellaneousDatas)
// data += "Text: " + miscellaneousData.getText() + " \r\n" + "Attachments: " + miscellaneousData.getAttachments() + "\r\n" + "\r\n" + "\r\n";
//
// data += "Education datas:\r\n\r\n";
// for (final EducationData educationData : educationDatas)
// data += "Degree: " + educationData.getDegree() + " \r\n" + "Institution: " + educationData.getInstitution() + "\r\n" + "Mark: " + educationData.getMark() + "\r\n" + "Start date: " + educationData.getStartDate() + "\r\n" + "End date: "
// + educationData.getEndDate() + "\r\n" + "\r\n" + "\r\n";
//
// data += "Position datas:\r\n\r\n";
// for (final PositionData positionData : positionDatas)
// data += "Tite: " + positionData.getTitle() + " \r\n" + "Description: " + positionData.getDescription() + "\r\n" + "End date: " + positionData.getStartDate() + "\r\n" + "End date: " + positionData.getEndDate() + "\r\n" + "\r\n" + "\r\n";
//
// ss++;
// if (ss < curricula.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// }
// if (actor.getUserAccount().getAuthorities().toString().equals("[AUDITOR]")) {
// final Collection<Audit> audits;
// Auditor principal;
//
// principal = this.auditorService.findByPrincipal();
// audits = this.auditService.findAuditsByAuditor(principal);
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Audits:\r\n\r\n";
// Integer ss = 0;
// for (final Audit audit : audits) {
// data += "Score: " + audit.getScore() + " \r\n" + "Text: " + audit.getText() + " \r\n" + "Final mode: " + audit.getFinalMode() + " \r\n" + "Written moment : " + audit.getWrittenMoment() + "\r\n" + "Position: "
// + audit.getPosition().getTitle() + " \r\n";
// ss++;
// if (ss < audits.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// }
//
// if (actor.getUserAccount().getAuthorities().toString().equals("[PROVIDER]")) {
// final Collection<Sponsorship> sponsorships;
// final Collection<Item> items;
// Provider principal;
//
// principal = this.providerService.findByPrincipal();
// sponsorships = this.sponsorshipService.findAllByPrincipal();
// items = this.itemService.findItemsByProvider(principal.getId());
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Sponsorships:\r\n\r\n";
// Integer ss = 0;
// for (final Sponsorship sponsorship : sponsorships) {
// data += "Banner: " + sponsorship.getBanner() + " \r\n" + "Targer page: " + sponsorship.getTargetPage() + " \r\n" + "Position: " + sponsorship.getPosition().getTitle() + " \r\n";
// data += "\r\n";
// data += "Holder name: " + sponsorship.getCreditCard().getHolder() + " \r\n" + "Make: " + sponsorship.getCreditCard().getMake() + " \r\n" + "Number: " + sponsorship.getCreditCard().getNumber() + " \r\n" + "Expiration month: "
// + sponsorship.getCreditCard().getExpirationMonth() + " \r\n" + "Expiration year: " + sponsorship.getCreditCard().getExpirationYear() + " \r\n" + "CVV Code: " + sponsorship.getCreditCard().getCvvCode() + " \r\n";
//
// ss++;
// if (ss < sponsorships.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// data += "\r\n";
// data += "-------------------------------------------------------------";
// data += "\r\n\r\n";
//
// data += "Items:\r\n\r\n";
// Integer ss1 = 0;
// for (final Item item : items) {
// data += "Name: " + item.getName() + " \r\n" + "Description: " + item.getDescription() + " \r\n" + "Link: " + item.getLink() + " \r\n" + "Picture:" + item.getPicture() + " \r\n";
//
// ss1++;
// if (ss1 < items.size())
// data += "\r\n" + "......................." + "\r\n\r\n";
// }
//
// }
//
// response.setContentType("text/plain");
// response.setHeader("Content-Disposition", "attachment;filename=data_user_account.txt");
// final ServletOutputStream out = response.getOutputStream();
// out.println(data);
// out.flush();
// out.close();
// }
//}
