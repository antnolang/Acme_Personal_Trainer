
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {

	// Constructor

	public Customisation() {
		super();
	}


	// Attributes

	private String	name;
	private String	banner;
	private String	welcomeMessageEn;
	private String	welcomeMessageEs;
	private String	countryCode;
	private int		timeCachedResults;
	private int		maxNumberResults;
	private String	spamWords;
	private int		VATtax;


	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@NotBlank
	@Pattern(regexp = "\\+\\d+")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Range(min = 1, max = 24)
	public int getTimeCachedResults() {
		return this.timeCachedResults;
	}

	public void setTimeCachedResults(final int timeCachedResults) {
		this.timeCachedResults = timeCachedResults;
	}

	@Range(min = 1, max = 100)
	public int getMaxNumberResults() {
		return this.maxNumberResults;
	}

	public void setMaxNumberResults(final int maxNumberResults) {
		this.maxNumberResults = maxNumberResults;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	@Range(min = 0, max = 100)
	public int getVATtax() {
		return this.VATtax;
	}

	public void setVATtax(final int vATtax) {
		this.VATtax = vATtax;
	}

}
