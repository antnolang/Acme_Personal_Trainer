
package controllers.customerNutritionist;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.CommentService;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping(value = "/comment/customer,nutritionist")
public class CommentCustomerNutritionistController extends AbstractController {

	// Services------------------------------------

	@Autowired
	private ArticleService	articleService;

	@Autowired
	private CommentService	commentService;


	// Constructors -----------------------------------------------------------

	public CommentCustomerNutritionistController() {
		super();
	}

	// Request create -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int articleId) {
		ModelAndView result;

		try {
			final Comment comment;

			comment = this.commentService.create(articleId);

			result = this.createEditModelAndView(comment, articleId);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:../../error.do");
		}
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;

		try {

			comment = this.commentService.findOne(commentId);
			result = this.createEditModelAndView(comment);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../error.do");
		}

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Comment comment, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Integer articleId;
		String paramArticleId;
		Comment commentRec;

		paramArticleId = request.getParameter("articleId");
		articleId = paramArticleId.isEmpty() ? null : Integer.parseInt(paramArticleId);

		commentRec = this.commentService.reconstruct(comment, binding, articleId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(comment, articleId);
		else
			try {
				this.commentService.save(commentRec);
				result = new ModelAndView("redirect:../customer,nutritionist/list.do?articleId=" + comment.getArticle());
			}

			catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, comment.getArticle().getId(), "comment.commit.error");
			}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Comment comment, final int articleId) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, articleId, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Comment comment, final Integer articleId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("messageCode", messageCode);
		result.addObject("comment", comment);
		result.addObject("articleId", articleId);
		return result;

	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;

		try {
			result = new ModelAndView("comment/display");
			comment = this.commentService.findOneToDisplay(commentId);

			result.addObject("comment", comment);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int articleId) {
		ModelAndView result;
		Collection<Comment> comments;

		try {
			comments = this.commentService.findCommentsByArticle(articleId);

			result = new ModelAndView("comment/list");
			result.addObject("comments", comments);
			result.addObject("articleId", articleId);
			result.addObject("requestURI", "comment/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

}
