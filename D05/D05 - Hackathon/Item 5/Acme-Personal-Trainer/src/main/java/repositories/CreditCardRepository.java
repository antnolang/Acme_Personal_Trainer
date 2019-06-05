
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Query("select c from CreditCard c where c.customer.id = ?1")
	Collection<CreditCard> findAllByCustomer(int customerId);

}
