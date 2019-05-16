//
// package controllers;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView;
//
// import services.CompanyService;
// import services.ProviderService;
// import services.RookieService;
// import domain.Company;
// import domain.Provider;
// import domain.Rookie;
// import forms.RegistrationForm;
//
// @Controller
// @RequestMapping(value = "/actor")
// public class ActorController extends ActorAbstractController {
//
// // Services
//
// @Autowired
// private RookieService rookieService;
//
// @Autowired
// private CompanyService companyService;
//
// @Autowired
// private ProviderService providerService;
//
//
// // Constructor
//
// public ActorController() {
// super();
// }
//
// // Display
//
// @Override
// @RequestMapping(value = "/display", method = RequestMethod.GET)
// public ModelAndView display(@RequestParam(required = false) final Integer actorId) {
// ModelAndView result;
//
// try {
// result = super.display(actorId);
// } catch (final Throwable oops) {
// result = new ModelAndView("redirect:/error.do");
// }
//
// return result;
// }
//
// // Register Company
//
// @RequestMapping(value = "/registerCompany", method = RequestMethod.GET)
// public ModelAndView createCompany() {
// ModelAndView result;
// String rol;
// Company company;
//
// rol = "Company";
// company = new Company();
// result = this.createModelAndView(company);
// result.addObject("rol", rol);
// result.addObject("urlAdmin", "");
//
// return result;
// }
//
// @RequestMapping(value = "/registerCompany", method = RequestMethod.POST, params = "save")
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
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// if (oops.getMessage().equals("Expired credit card"))
// result = this.createModelAndView(registrationForm, "expired.creditCard");
// else
// result = this.createModelAndView(registrationForm, "actor.registration.error");
// result.addObject("rol", "Company");
// }
//
// return result;
// }
//
// // Register Rookie
//
// @RequestMapping(value = "/registerRookie", method = RequestMethod.GET)
// public ModelAndView createRookie() {
// ModelAndView result;
// String rol;
// Rookie rookie;
//
// rol = "Rookie";
// rookie = new Rookie();
// result = this.createModelAndView(rookie);
// result.addObject("rol", rol);
// result.addObject("urlAdmin", "");
//
// return result;
// }
//
// @RequestMapping(value = "/registerRookie", method = RequestMethod.POST, params = "save")
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
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// if (oops.getMessage().equals("Expired credit card"))
// result = this.createModelAndView(registrationForm, "expired.creditCard");
// else
// result = this.createModelAndView(registrationForm, "actor.registration.error");
// result.addObject("rol", "Rookie");
// }
//
// return result;
// }
//
// // Register Provider
//
// @RequestMapping(value = "/registerProvider", method = RequestMethod.GET)
// public ModelAndView createProvider() {
// ModelAndView result;
// String rol;
// Provider provider;
//
// rol = "Provider";
// provider = new Provider();
// result = this.createModelAndView(provider);
// result.addObject("rol", rol);
// result.addObject("urlAdmin", "");
//
// return result;
// }
//
// @RequestMapping(value = "/registerProvider", method = RequestMethod.POST, params = "save")
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
// result = new ModelAndView("redirect:/welcome/index.do");
// } catch (final Throwable oops) {
// if (oops.getMessage().equals("Expired credit card"))
// result = this.createModelAndView(registrationForm, "expired.creditCard");
// else
// result = this.createModelAndView(registrationForm, "actor.registration.error");
// result.addObject("rol", "Provider");
// }
//
// return result;
// }
//
// // Ancillary methods ------------------------------------------------------
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
// result = new ModelAndView("actor/singup");
// result.addObject("registrationForm", registrationForm);
// result.addObject("messageCode", messageCode);
//
// return result;
// }
//
//}
