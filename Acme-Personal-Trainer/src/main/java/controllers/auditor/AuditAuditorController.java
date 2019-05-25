
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.UtilityService;
import controllers.AbstractController;
import domain.Audit;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private UtilityService	utilityService;


	// Constructors -----------------------------------------------------------

	public AuditAuditorController() {
		super();
	}

	// Controller methods -----------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits;

		try {
			audits = this.auditService.findAllByPrincipal();

			result = new ModelAndView("audit/list");
			result.addObject("audits", audits);
			result.addObject("requestURI", "audit/auditor/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		Audit audit;

		try {
			audit = this.auditService.create(curriculumId);
			result = this.createEditModelAndView(audit);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;
		Collection<String> attachments;

		try {
			audit = this.auditService.findOneToEditDisplay(auditId);
			attachments = this.utilityService.getSplittedString(audit.getAttachments());

			result = new ModelAndView("audit/display");
			result.addObject("audit", audit);
			result.addObject("attachments", attachments);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;

		try {
			audit = this.auditService.findOneToEditDisplay(auditId);
			result = this.createEditModelAndView(audit);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		Audit auditRec, saved;

		auditRec = this.auditService.reconstruct(audit, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				saved = this.auditService.save(auditRec);
				result = new ModelAndView("redirect:display.do?auditId=" + saved.getId());
			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(auditRec, "audit.url.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditRec, "audit.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit) {
		ModelAndView result;

		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/error.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
