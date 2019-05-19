
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.CreditCard;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.workingOut.id = ?1 and a.status='ACCEPTED'")
	Collection<Application> findAcceptedApplicationsByWorkingOut(int workingOutId);

	@Query("select a from Application a where a.workingOut.id = ?1 and a.status='PENDING'")
	Collection<Application> findPendingApplicationsByWorkingOut(int workingOutId);

	@Query("select a from Application a where a.workingOut.id = ?1 and a.customer.id = ?2")
	Collection<CreditCard> findApplicationsByWorkingOutByCustomer(int workingOutId, int customerId);

	@Query("select a from Application a where a.workingOut.trainer.id=?1")
	Collection<Application> findApplicationsByTrainer(int id);

	@Query("select a from Application a where a.customer.id=?1")
	Collection<Application> findApplicationsByCustomer(int id);
}
