
package controllers.customerNutritionist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ArticleService;
import services.UtilityService;
import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping(value = "/article/customer,nutritionist")
public class ArticleCustomerNutritionistController extends AbstractController {

	// Services

	@Autowired
	private ArticleService	articleService;

	@Autowired
	private UtilityService	utilityService;


	// Constructor

	public ArticleCustomerNutritionistController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		ModelAndView result;
		final Article article;
		Collection<String> tags;

		try {
			result = new ModelAndView("article/display");
			if (LoginService.getPrincipal().getAuthorities().toString().equals("[CUSTOMER]"))
				article = this.articleService.findOneToDisplayCustomer(articleId);
			else
				article = this.articleService.findOneToDisplayNutritionist(articleId);
			tags = this.utilityService.getSplittedString(article.getTags());
			result.addObject("article", article);
			result.addObject("tags", tags);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

}
