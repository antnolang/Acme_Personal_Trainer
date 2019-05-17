
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
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
	private int		VAT;
	private String	countryCode;
	private String	priorities;
	private String	creditCardMakes;
	private int		timeResults;
	private int		numberResults;
	private double	threshold;
	private String	spamWords;
	private String	positiveWords;
	private String	negativeWords;
	private double	premiumAmount;


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

	@Range(min = 0, max = 100)
	public int getVAT() {
		return this.VAT;
	}

	public void setVAT(final int vAT) {
		this.VAT = vAT;
	}

	@NotBlank
	@Pattern(regexp = "\\+\\d+")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPriorities() {
		return this.priorities;
	}

	public void setPriorities(final String priorities) {
		this.priorities = priorities;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final String creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@Range(min = 1, max = 24)
	public int getTimeResults() {
		return this.timeResults;
	}

	public void setTimeResults(final int timeResults) {
		this.timeResults = timeResults;
	}

	@Range(min = 1, max = 100)
	public int getNumberResults() {
		return this.numberResults;
	}

	public void setNumberResults(final int numberResults) {
		this.numberResults = numberResults;
	}

	@Range(min = -1, max = 0)
	public double getThreshold() {
		return this.threshold;
	}

	public void setThreshold(final double threshold) {
		this.threshold = threshold;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWords = positiveWords;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWords = negativeWords;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getPremiumAmount() {
		return this.premiumAmount;
	}

	public void setPremiumAmount(final double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

}
