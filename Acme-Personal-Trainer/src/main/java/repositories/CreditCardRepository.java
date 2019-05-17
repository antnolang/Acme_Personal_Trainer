
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

}
