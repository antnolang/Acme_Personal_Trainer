
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.CustomerService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Article;
import domain.Customer;

@Controller
@RequestMapping(value = "/article/customer")
public class ArticleCustomerController extends AbstractController {

	// Services

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructor

	public ArticleCustomerController() {
		super();
	}

	// List
	@RequestMapping(value = "/allArticlesList", method = RequestMethod.GET)
	public ModelAndView allArticlesList() {
		ModelAndView result;
		Collection<Article> articles;
		Customer principal;
		double price = 0;

		try {
			principal = this.customerService.findByPrincipal();
			if (principal.getIsPremium()) {
				result = new ModelAndView("article/list");

				articles = this.articleService.findAll();

				result.addObject("owner", null);
				result.addObject("articles", articles);
				result.addObject("requestURI", "article/customer/allArticlesList.do");
			} else {
				price = this.customisationService.find().getPremiumAmount();
				result = new ModelAndView("article/noAccess");
				result.addObject("price", price);
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

}
