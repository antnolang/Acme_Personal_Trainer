
package controllers.customerNutritionist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping(value = "/article/customer,nutritionist")
public class ArticleCustomerNutritionistController extends AbstractController {

	// Services

	@Autowired
	private ArticleService	articleService;


	// Constructor

	public ArticleCustomerNutritionistController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		ModelAndView result;
		final Article article;

		try {
			result = new ModelAndView("article/display");
			article = this.articleService.findOne(articleId);

			result.addObject("article", article);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

}
