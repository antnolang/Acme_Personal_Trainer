
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id=?1")
	Customer findByUserAccount(int userAccountId);

	@Query("select a.customer from Application a where a.workingOut.trainer.id = ?1 and a.status = 'ACCEPTED'")
	Collection<Customer> findCustomersWithAcceptedApplicationsByTrainer(int trainerId);

}
