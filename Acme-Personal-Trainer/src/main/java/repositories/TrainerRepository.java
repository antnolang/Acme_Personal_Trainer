
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

	@Query("select t from Trainer t where t.userAccount.id=?1")
	Trainer findByUserAccount(int userAccountId);

	@Query("select a.workingOut.trainer from Application a where a.customer.id = ?1 and a.status = 'ACCEPTED'")
	Collection<Trainer> findTrainersWithAcceptedApplicationsByCustomer(int customerId);

}
