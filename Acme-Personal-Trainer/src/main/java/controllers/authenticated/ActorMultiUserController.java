//
// package controllers.authenticated;
//
// import javax.servlet.http.HttpSession;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView;
//
// import services.ActorService;
// import services.AdministratorService;
// import services.AuditorService;
// import services.CompanyService;
// import services.ProviderService;
// import services.RookieService;
// import controllers.ActorAbstractController;
// import domain.Actor;
// import domain.Administrator;
// import domain.Auditor;
// import domain.Company;
// import domain.Provider;
// import domain.Rookie;
// import forms.RegistrationForm;
//
// @Controller
// @RequestMapping(value = "/actor/administrator,auditor,company,provider,rookie")
// public class ActorMultiUserController extends ActorAbstractController {
//
// // Services
//
// @Autowired
// private AdministratorService administratorService;
//
// @Autowired
// private CompanyService companyService;
//
// @Autowired
// private RookieService rookieService;
//
// @Autowired
// private ActorService actorService;
//
// @Autowired
// private AuditorService auditorService;
//
// @Autowired
// private ProviderService providerService;
//
//
// // Constructor
//
// public ActorMultiUserController() {
// super();
// }
//
// // Edit
//
// @RequestMapping(value = "/edit", method = RequestMethod.GET)
// public ModelAndView edit(@RequestParam final int actorId) {
// ModelAndView result;
// Administrator administrator;
// Company company;
// final Rookie rookie;
// Actor actor;
// Auditor auditor;
// Provider provider;
//
// result = new ModelAndView();
//
// try {
// actor = this.actorService.findOneToDisplayEdit(actorId);
// if (actor instanceof Administrator) {
// administrator = this.administratorService.findOneToDisplayEdit(actorId);
// result = this.createModelAndView(administrator);
// result.addObject("rol", "Administrator");
// } else if (actor instanceof Company) {
// company = this.companyService.findOneToDisplayEdit(actorId);
// result = this.createModelAndView(company);
// result.addObject("rol", "Company");
// } else if (actor instanceof Rookie) {
// rookie = this.rookieService.findOneToDisplayEdit(actorId);
// result = this.createModelAndView(rookie);
// result.addObject("rol", "Rookie");
// } else if (actor instanceof Auditor) {
// auditor = this.auditorService.findOneToDisplayEdit(actorId);
// result = this.createModelAndView(auditor);
// result.addObject("rol", "Auditor");
// } else if (actor instanceof Provider) {
// provider = this.providerService.findOneToDisplayEdit(actorId);
// result = this.createModelAndView(provider);
// result.addObject("rol", "Provider");
// }
//
// } catch (final Throwable oops) {
// result = new ModelAndView("redirect:/error.do");
// }
//
// return result;
//
// }
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdmin")
// public ModelAndView saveAdministrator(final RegistrationForm registrationForm, final BindingResult binding) {
// ModelAndView result;
// Administrator administrator;
//
// administrator = this.administratorService.reconstruct(registrationForm, binding);
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Administrator");
// } else
// try {
// this.administratorService.save(administrator);
// result = new ModelAndView("redirect:/actor/display.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Administrator");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteAdmin")
// public ModelAndView deleteAdministrator(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
// Administrator administrator;
// ModelAndView result;
//
// administrator = this.administratorService.findOneToDisplayEdit(registrationForm.getId());
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Administrator");
// } else
// try {
// this.administratorService.delete(administrator);
// session.invalidate();
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Administrator");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCompany")
// public ModelAndView saveCompany(final RegistrationForm registrationForm, final BindingResult binding) {
// ModelAndView result;
// Company company;
//
// company = this.companyService.reconstruct(registrationForm, binding);
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Company");
// } else
// try {
// this.companyService.save(company);
// result = new ModelAndView("redirect:/actor/display.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Company");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteCompany")
// public ModelAndView deleteCompany(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
// ModelAndView result;
// Company company;
//
// company = this.companyService.findOneToDisplayEdit(registrationForm.getId());
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Company");
// } else
// try {
// this.companyService.delete(company);
// session.invalidate();
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Company");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveRookie")
// public ModelAndView saveRookie(final RegistrationForm registrationForm, final BindingResult binding) {
// ModelAndView result;
// Rookie rookie;
//
// rookie = this.rookieService.reconstruct(registrationForm, binding);
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Rookie");
// } else
// try {
// this.rookieService.save(rookie);
// result = new ModelAndView("redirect:/actor/display.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Rookie");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteRookie")
// public ModelAndView deleteRookie(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
// ModelAndView result;
// Rookie rookie;
//
// rookie = this.rookieService.findOneToDisplayEdit(registrationForm.getId());
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Rookie");
// } else
// try {
// this.rookieService.delete(rookie);
// session.invalidate();
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Rookie");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAuditor")
// public ModelAndView saveAuditor(final RegistrationForm registrationForm, final BindingResult binding) {
// ModelAndView result;
// Auditor auditor;
//
// auditor = this.auditorService.reconstruct(registrationForm, binding);
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Auditor");
// } else
// try {
// this.auditorService.save(auditor);
// result = new ModelAndView("redirect:/actor/display.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Auditor");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteAuditor")
// public ModelAndView deleteAuditor(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
// ModelAndView result;
// Auditor auditor;
//
// auditor = this.auditorService.findOneToDisplayEdit(registrationForm.getId());
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Auditor");
// } else
// try {
// this.auditorService.delete(auditor);
// session.invalidate();
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Auditor");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveProvider")
// public ModelAndView saveProvider(final RegistrationForm registrationForm, final BindingResult binding) {
// ModelAndView result;
// Provider provider;
//
// provider = this.providerService.reconstruct(registrationForm, binding);
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Provider");
// } else
// try {
// this.providerService.save(provider);
// result = new ModelAndView("redirect:/actor/display.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Provider");
// }
//
// return result;
// }
//
// @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteProvider")
// public ModelAndView deleteProvider(final RegistrationForm registrationForm, final BindingResult binding, final HttpSession session) {
// ModelAndView result;
// Provider provider;
//
// provider = this.providerService.findOneToDisplayEdit(registrationForm.getId());
//
// if (binding.hasErrors()) {
// result = this.createModelAndView(registrationForm);
// result.addObject("rol", "Provider");
// } else
// try {
// this.providerService.delete(provider);
// session.invalidate();
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// result = this.createModelAndView(registrationForm, "actor.commit.error");
// result.addObject("rol", "Provider");
// }
//
// return result;
// }
//
// // Ancillary methods
//
// protected ModelAndView createModelAndView(final Provider provider) {
// ModelAndView result;
// RegistrationForm registrationForm;
//
// registrationForm = this.providerService.createForm(provider);
//
// result = this.createModelAndView(registrationForm, null);
//
// return result;
// }
//
// protected ModelAndView createModelAndView(final Auditor auditor) {
// ModelAndView result;
// RegistrationForm registrationForm;
//
// registrationForm = this.auditorService.createForm(auditor);
//
// result = this.createModelAndView(registrationForm, null);
//
// return result;
// }
//
// protected ModelAndView createModelAndView(final Rookie rookie) {
// ModelAndView result;
// RegistrationForm registrationForm;
//
// registrationForm = this.rookieService.createForm(rookie);
//
// result = this.createModelAndView(registrationForm, null);
//
// return result;
// }
//
// protected ModelAndView createModelAndView(final Company company) {
// ModelAndView result;
// RegistrationForm registrationForm;
//
// registrationForm = this.companyService.createForm(company);
//
// result = this.createModelAndView(registrationForm, null);
//
// return result;
// }
//
// protected ModelAndView createModelAndView(final Administrator administrator) {
// ModelAndView result;
// RegistrationForm registrationForm;
//
// registrationForm = this.administratorService.createForm(administrator);
//
// result = this.createModelAndView(registrationForm, null);
//
// return result;
// }
//
// protected ModelAndView createModelAndView(final RegistrationForm registrationForm) {
// ModelAndView result;
//
// result = this.createModelAndView(registrationForm, null);
//
// return result;
// }
//
// protected ModelAndView createModelAndView(final RegistrationForm registrationForm, final String messageCode) {
// ModelAndView result;
//
// result = new ModelAndView("actor/edit");
// result.addObject("registrationForm", registrationForm);
// result.addObject("messageCode", messageCode);
//
// return result;
// }
//
//}
