//
// package controllers.administrator;
//
// import java.util.Collection;
// import java.util.List;
// import java.util.Locale;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.servlet.ModelAndView;
//
// import services.ApplicationService;
// import services.AuditService;
// import services.CompanyService;
// import services.CurriculumService;
// import services.FinderService;
// import services.ItemService;
// import services.PositionService;
// import services.ProviderService;
// import services.RookieService;
// import services.SponsorshipService;
// import controllers.AbstractController;
// import domain.Company;
// import domain.Position;
// import domain.Provider;
// import domain.Rookie;
//
// @Controller
// @RequestMapping("/dashboard/administrator")
// public class DashboardAdministratorController extends AbstractController {
//
// // Services ------------------
//
// @Autowired
// private PositionService positionService;
//
// @Autowired
// private CompanyService companyService;
//
// @Autowired
// private ApplicationService applicationService;
//
// @Autowired
// private RookieService rookieService;
//
// @Autowired
// private CurriculumService curriculumService;
//
// @Autowired
// private FinderService finderService;
//
// @Autowired
// private ItemService itemService;
//
// @Autowired
// private ProviderService providerService;
//
// @Autowired
// private SponsorshipService sponsorshipService;
//
// @Autowired
// private AuditService auditService;
//
//
// // Constructors --------------
// public DashboardAdministratorController() {
// super();
// }
//
// // methods --------------
// @RequestMapping(value = "/display", method = RequestMethod.GET)
// public ModelAndView display(final Locale locale) {
// final ModelAndView result;
//
// result = new ModelAndView("dashboard/display");
//
// // LEVEL C -----------------------------------------
//
// // Req 11.2.1
// Double[] findDataNumberPositionsPerCompany;
// findDataNumberPositionsPerCompany = this.positionService.findDataNumberPositionsPerCompany();
// result.addObject("findDataNumberPositionsPerCompany", findDataNumberPositionsPerCompany);
//
// // Req 11.2.2
// Double[] findDataNumberApplicationPerRookie;
// findDataNumberApplicationPerRookie = this.applicationService.findDataNumberApplicationPerRookie();
// result.addObject("findDataNumberApplicationPerRookie", findDataNumberApplicationPerRookie);
//
// // Req 11.2.3
// Collection<Company> findCompaniesOfferedMorePositions;
// findCompaniesOfferedMorePositions = this.companyService.findCompaniesOfferedMorePositions();
// result.addObject("findCompaniesOfferedMorePositions", findCompaniesOfferedMorePositions);
//
// // Req 11.2.4
// Collection<Rookie> findRookiesWithMoreApplications;
// findRookiesWithMoreApplications = this.rookieService.findRookiesWithMoreApplications();
// result.addObject("findRookiesWithMoreApplications", findRookiesWithMoreApplications);
//
// // Req 11.2.5
// Double[] dataSalaryOffered;
// dataSalaryOffered = this.positionService.findDataSalaryOffered();
// result.addObject("dataSalaryOffered", dataSalaryOffered);
//
// // Req 11.2.6
// final List<Position> dataPositionsBestWorstSalary;
// dataPositionsBestWorstSalary = this.positionService.findPositionsBestWorstSalary();
// result.addObject("dataPositionsBestWorstSalary", dataPositionsBestWorstSalary);
//
// // LEVEL B --------------------------------------
//
// // Req 18.1.1
// Double[] findDataNumberCurriculumPerRookie;
// findDataNumberCurriculumPerRookie = this.curriculumService.findDataNumberCurriculumPerRookie();
// result.addObject("findDataNumberCurriculumPerRookie", findDataNumberCurriculumPerRookie);
//
// // Req 18.1.2
// Double[] findDataNumberResultsFinder;
// findDataNumberResultsFinder = this.finderService.findDataNumberResultsFinder();
// result.addObject("findDataNumberResultsFinder", findDataNumberResultsFinder);
//
// // Req 18.1.3
// Double findRatioEmptyVsNonEmpty;
// findRatioEmptyVsNonEmpty = this.finderService.findRatioEmptyVsNonEmpty();
// result.addObject("findRatioEmptyVsNonEmpty", findRatioEmptyVsNonEmpty);
//
// // -------------------- ACME ROOKIES -----------------------------------------------
//
// // LEVEL C --------------------------------------
//
// // Req 4.4.1
// Double[] findDataNumberAuditScore;
// findDataNumberAuditScore = this.auditService.findDataNumberAuditScore();
// result.addObject("findDataNumberAuditScore", findDataNumberAuditScore);
//
// // Req 4.4.2
// Double[] findDataNumberAuditScoreOfCompany;
// findDataNumberAuditScoreOfCompany = this.auditService.findDataNumberAuditScoreOfCompany();
// result.addObject("findDataNumberAuditScoreOfCompany", findDataNumberAuditScoreOfCompany);
//
// // Req 4.4.3
// Collection<Company> findCompaniesHighestScore;
// findCompaniesHighestScore = this.companyService.findCompaniesHighestScore();
// result.addObject("findCompaniesHighestScore", findCompaniesHighestScore);
//
// // Req 4.4.4
// Collection<Double> findAvgSalaryByHighestPosition;
// findAvgSalaryByHighestPosition = this.auditService.findAvgSalaryByHighestPosition();
// result.addObject("findAvgSalaryByHighestPosition", findAvgSalaryByHighestPosition);
//
// // LEVEL B --------------------------------------
//
// // Req 11.1.1
// Double[] dataItemsPerProvider;
// dataItemsPerProvider = this.itemService.dataItemsPerProvider();
// result.addObject("dataItemsPerProvider", dataItemsPerProvider);
//
// // Req 11.1.2
// Collection<Provider> topFiveProviders;
// topFiveProviders = this.providerService.topFiveProviders();
// result.addObject("topFiveProviders", topFiveProviders);
//
// // LEVEL A --------------------------------------
//
// // Req 14.1.1
// Double[] dataOfSponsorshipPerProvider;
// dataOfSponsorshipPerProvider = this.sponsorshipService.dataOfSponsorshipPerProvider();
// result.addObject("dataOfSponsorshipPerProvider", dataOfSponsorshipPerProvider);
//
// // Req 14.1.2
// Double[] dataOfSponsorshipPerPosition;
// dataOfSponsorshipPerPosition = this.sponsorshipService.dataOfSponsorshipPerPosition();
// result.addObject("dataOfSponsorshipPerPosition", dataOfSponsorshipPerPosition);
//
// // Req 14.1.3
// Collection<Provider> findProvidersWithMoreSponsorships;
// findProvidersWithMoreSponsorships = this.providerService.findProvidersWithMoreSponsorships();
// result.addObject("findProvidersWithMoreSponsorships", findProvidersWithMoreSponsorships);
//
// return result;
// }
//}
