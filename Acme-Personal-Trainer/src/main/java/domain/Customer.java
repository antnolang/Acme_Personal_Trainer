
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Constructor

	public Customer() {
		super();
	}


	// Attributes

	private boolean	isPremium;


	public boolean getIsPremium() {
		return this.isPremium;
	}

	public void setIsPremium(final boolean isPremium) {
		this.isPremium = isPremium;
	}

}
