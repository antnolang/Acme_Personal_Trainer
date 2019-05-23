
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomisationRepository;
import domain.Customisation;

@Service
@Transactional
public class CustomisationService {

	// Managed repository -------------------------------
	@Autowired
	private CustomisationRepository	customisationRepository;

	// Other supporting services ------------------------
	@Autowired
	private UtilityService			utilityService;


	// Constructors -------------------------------------
	public CustomisationService() {
		super();
	}

	// Simple CRUD methods -------------------------------
	public Customisation save(final Customisation customisation) {
		Assert.notNull(customisation);
		Assert.isTrue(this.customisationRepository.exists(customisation.getId()) && customisation.equals(this.find()));

		Customisation result;

		result = this.customisationRepository.save(customisation);

		return result;
	}

	// Other business methods ----------------------------
	public Customisation find() {
		Customisation result;
		Customisation[] all;

		all = this.customisationRepository.find();
		Assert.isTrue(all.length == 1);

		result = all[0];

		return result;
	}


	@Autowired
	private Validator	validator;


	public Customisation reconstruct(final Customisation customisation, final BindingResult binding) {
		Customisation custo, result;

		custo = this.find();

		result = new Customisation();
		result.setId(custo.getId());
		result.setVersion(custo.getVersion());
		result.setName(customisation.getName().trim());
		result.setBanner(customisation.getBanner().trim());
		result.setWelcomeMessageEn(customisation.getWelcomeMessageEn().trim());
		result.setWelcomeMessageEs(customisation.getWelcomeMessageEs().trim());
		result.setCountryCode(customisation.getCountryCode().trim());
		result.setSpamWords(customisation.getSpamWords().trim().toLowerCase());
		result.setCreditCardMakes(customisation.getCreditCardMakes().trim());
		result.setNegativeWords(customisation.getNegativeWords().trim().toLowerCase());
		result.setNumberResults(customisation.getNumberResults());
		result.setPositiveWords(customisation.getPositiveWords().trim().toLowerCase());
		result.setPremiumAmount(customisation.getPremiumAmount());
		result.setPriorities(customisation.getPriorities().trim());
		result.setThreshold(customisation.getThreshold());
		result.setTimeResults(customisation.getTimeResults());
		result.setVAT(customisation.getVAT());

		this.validator.validate(result, binding);

		return result;
	}

	public List<String> prioritiesAsList() {
		Customisation customisation;
		String priorities_str;
		List<String> results;

		customisation = this.find();

		priorities_str = customisation.getPriorities();
		results = this.utilityService.ListByString(priorities_str);

		return results;
	}

	// Protected methods ---------------------------------
	protected void flush() {
		this.customisationRepository.flush();
	}

}
