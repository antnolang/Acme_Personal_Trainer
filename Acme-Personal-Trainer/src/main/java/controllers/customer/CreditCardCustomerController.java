
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CreditCardService;
import services.CustomerService;
import services.CustomisationService;
import services.UtilityService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Customer;
import domain.Customisation;

@Controller
@RequestMapping(value = "/creditCard/customer")
public class CreditCardCustomerController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private UtilityService			utilityService;


	// Constructors -----------------------------------------------------------

	public CreditCardCustomerController() {
		super();
	}

	// Creditcard list -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CreditCard> creditCards;

		try {
			result = new ModelAndView("creditCard/list");
			creditCards = this.creditCardService.findAllByCustomer();

			result.addObject("creditCards", creditCards);
			result.addObject("requestURI", "creditCard/customer/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	// Creditcard display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int creditCardId) {
		ModelAndView result;
		CreditCard creditCard;

		try {
			result = new ModelAndView("creditCard/display");
			creditCard = this.creditCardService.findOneByPrincipal(creditCardId);

			result.addObject("creditCard", creditCard);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Create -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.create();

		result = this.createModelAndView(creditCard);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		CreditCard creditCardRec;
		creditCardRec = this.creditCardService.reconstruct(creditCard, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(creditCard);
		else
			try {
				this.creditCardService.save(creditCardRec);
				result = new ModelAndView("redirect:list.do");
			} catch (final TransactionSystemException oops) {
				result = new ModelAndView("redirect:../../error.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(creditCard, "creditCard.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int creditCardId, final RedirectAttributes redir) {
		ModelAndView result;
		CreditCard creditCardBbdd;

		try {

			creditCardBbdd = this.creditCardService.findOneByPrincipal(creditCardId);

			this.creditCardService.delete(creditCardBbdd);

		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "creditCard.delete.error");
		}
		result = new ModelAndView("redirect:list.do");
		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createModelAndView(final CreditCard creditCard, final String messageCode) {
		ModelAndView result;
		Customer principal;
		Collection<String> makes;
		Customisation customisation;

		principal = this.customerService.findByPrincipal();
		customisation = this.customisationService.find();
		makes = this.utilityService.ListByString(customisation.getCreditCardMakes());

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("principal", principal);
		result.addObject("messageCode", messageCode);
		result.addObject("makes", makes);

		return result;

	}

}
