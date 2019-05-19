
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Constructor

	public Finder() {
		super();
	}


	// Attributes

	private String	keyword;
	private String	category;
	private Double	startPrice;
	private Double	endPrice;
	private Date	startDate;
	private Date	endDate;
	private Date	updatedMoment;


	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public Double getStartPrice() {
		return this.startPrice;
	}

	public void setStartPrice(final Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getEndPrice() {
		return this.endPrice;
	}

	public void setEndPrice(final Double endPrice) {
		this.endPrice = endPrice;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getUpdatedMoment() {
		return this.updatedMoment;
	}

	public void setUpdatedMoment(final Date updatedMoment) {
		this.updatedMoment = updatedMoment;
	}


	// Relationships

	private Customer				customer;
	private Collection<WorkingOut>	workingOuts;


	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@NotNull
	@ManyToMany
	public Collection<WorkingOut> getWorkingOuts() {
		return this.workingOuts;
	}

	public void setWorkingOuts(final Collection<WorkingOut> workingOuts) {
		this.workingOuts = workingOuts;
	}

}
