
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class WorkingOut extends DomainEntity {

	// Constructor

	public WorkingOut() {
		super();
	}


	// Attributes

	private String	ticker;
	private Date	publishedMoment;
	private String	description;
	private double	price;
	private Date	startMoment;
	private Date	endMoment;
	private boolean	isFinalMode;


	@NotBlank
	@Pattern(regexp = "\\d{6}-[A-Z0-9]{6}")
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	public boolean isFinalMode() {
		return this.isFinalMode;
	}

	public void setFinalMode(final boolean isFinalMode) {
		this.isFinalMode = isFinalMode;
	}


	// Relationships

	private Trainer						trainer;
	private Collection<Category>		categories;
	private Collection<domain.Session>	sessions;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trainer getTrainer() {
		return this.trainer;
	}

	public void setTrainer(final Trainer trainer) {
		this.trainer = trainer;
	}

	@NotNull
	@ManyToMany
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<domain.Session> getSessions() {
		return this.sessions;
	}

	public void setSessions(final Collection<domain.Session> sessions) {
		this.sessions = sessions;
	}

}
