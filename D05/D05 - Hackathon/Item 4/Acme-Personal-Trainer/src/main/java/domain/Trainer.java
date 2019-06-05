
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Trainer extends Actor {

	// Constructor

	public Trainer() {
		super();
	}


	// Attributes

	private Double	mark;
	private Double	score;


	@Range(min = 0, max = 10)
	@Digits(integer = 4, fraction = 2)
	public Double getMark() {
		return this.mark;
	}

	public void setMark(final Double mark) {
		this.mark = mark;
	}

	@Range(min = -1, max = 1)
	@Digits(integer = 3, fraction = 2)
	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

}
