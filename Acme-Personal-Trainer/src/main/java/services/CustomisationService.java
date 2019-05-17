
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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


	/*
	 * public Customisation reconstruct(final Customisation customisation, final BindingResult binding) {
	 * Customisation custo, result;
	 * 
	 * custo = this.find();
	 * 
	 * result = new Customisation();
	 * result.setId(custo.getId());
	 * result.setVersion(custo.getVersion());
	 * //result.setIsRebrandNotificationSent(custo.getIsRebrandNotificationSent());
	 * result.setName(customisation.getName().trim());
	 * result.setBanner(customisation.getBanner().trim());
	 * result.setWelcomeMessageEn(customisation.getWelcomeMessageEn().trim());
	 * result.setWelcomeMessageEs(customisation.getWelcomeMessageEs().trim());
	 * result.setCountryCode(customisation.getCountryCode().trim());
	 * result.setTimeCachedResults(customisation.getTimeCachedResults());
	 * result.setMaxNumberResults(customisation.getMaxNumberResults());
	 * result.setSpamWords(customisation.getSpamWords().trim().toLowerCase());
	 * //result.setFrate(customisation.getFrate());
	 * result.setVATtax(customisation.getVATtax());
	 * 
	 * this.validator.validate(result, binding);
	 * 
	 * return result;
	 * }
	 */
	// Protected methods ---------------------------------
	protected void flush() {
		this.customisationRepository.flush();
	}

}
