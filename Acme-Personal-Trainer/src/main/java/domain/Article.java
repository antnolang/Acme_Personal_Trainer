
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Article extends DomainEntity {

	// Constructor

	public Article() {
		super();
	}


	// Attributes

	private Date	publishedMoment;
	private String	title;
	private String	description;
	private String	tags;
	private boolean	isFinalMode;


	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublishedMoment() {
		return this.publishedMoment;
	}

	public void setPublishedMoment(final Date publishedMoment) {
		this.publishedMoment = publishedMoment;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTags() {
		return this.tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public boolean isFinalMode() {
		return this.isFinalMode;
	}

	public void setFinalMode(final boolean isFinalMode) {
		this.isFinalMode = isFinalMode;
	}


	// Relationships

	private Nutritionist	nutritionist;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Nutritionist getNutritionist() {
		return this.nutritionist;
	}

	public void setNutritionist(final Nutritionist nutritionist) {
		this.nutritionist = nutritionist;
	}

}
