
package controllers.nutritionist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ArticleService;
import services.NutritionistService;
import controllers.AbstractController;
import domain.Article;
import domain.Nutritionist;

@Controller
@RequestMapping("/article/nutritionist")
public class ArticleNutritionistController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private NutritionistService	nutritionistService;


	// Constructors -----------------------------------------------------------

	public ArticleNutritionistController() {
		super();
	}

	// Controller methods -----------------------------------------------------		
	// Working-out list by principal nutritionist -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Article> articles;
		Nutritionist principal;

		try {
			principal = this.nutritionistService.findByPrincipal();
			result = new ModelAndView("article/list");
			articles = this.articleService.findArticlesByPrincipal();

			result.addObject("principal", principal);
			result.addObject("articles", articles);
			result.addObject("requestURI", "article/nutritionist/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/makeFinal", method = RequestMethod.GET)
	public ModelAndView makeFinal(@RequestParam final int articleId, final RedirectAttributes redir) {
		ModelAndView result;
		Article article;

		try {
			article = this.articleService.findOne(articleId);
			this.articleService.makeFinal(article);
		} catch (final Throwable oops) {
			redir.addFlashAttribute("messageCode", "article.make.final.error");
		}

		result = new ModelAndView("redirect:/article/nutritionist/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Article article;

		try {
			article = this.articleService.create();
			result = this.createEditModelAndView(article);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId) {
		ModelAndView result;
		Article article;

		try {
			article = this.articleService.findOneToNutritionistEdit(articleId);
			result = this.createEditModelAndView(article);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Article article, final BindingResult binding) {
		ModelAndView result;
		Article articleRec;

		try {
			articleRec = this.articleService.reconstruct(article, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(article);
			else
				try {
					this.articleService.save(articleRec);
					result = new ModelAndView("redirect:../list.do?nutritionistId=" + articleRec.getNutritionist().getId());
				} catch (final DataIntegrityViolationException e1) {
					result = this.createEditModelAndView(articleRec, "article.commit.url");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(articleRec, "article.commit.error");

				}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Article article) {
		ModelAndView result;
		Article articleBd;
		int nutritionistId;

		try {
			articleBd = this.articleService.findOneToNutritionistEdit(article.getId());
			nutritionistId = articleBd.getNutritionist().getId();
			this.articleService.delete(articleBd);
			result = new ModelAndView("redirect:../list.do?nutritionistId=" + nutritionistId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Article article) {
		ModelAndView result;

		result = this.createEditModelAndView(article, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article, final String messageCode) {
		ModelAndView result;
		int nutritionistId;

		nutritionistId = this.nutritionistService.findByPrincipal().getId();
		result = new ModelAndView("article/edit");

		result.addObject("article", article);
		result.addObject("nutritionistId", nutritionistId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
