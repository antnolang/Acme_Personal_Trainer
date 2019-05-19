
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

	@Query("select a from Application a where a.workingOut.id = ?1 and a.status='REJECTED'")
	Collection<Application> findRejectedApplicationsByWorkingOut(int workingOutId);

	@Query("select a from Application a where a.workingOut.id = ?1 and a.customer.id = ?2")
	Collection<CreditCard> findApplicationsByWorkingOutByCustomer(int workingOutId, int customerId);

	@Query("select a from Application a where a.workingOut.trainer.id=?1")
	Collection<Application> findApplicationsByTrainer(int id);

	@Query("select a from Application a where a.customer.id=?1")
	Collection<Application> findApplicationsByCustomer(int id);

	@Query("select a from Application a where a.customer.id = ?1 and a.status='ACCEPTED'")
	Collection<Application> findAcceptedApplicationsByCustomer(int id);

	@Query("select a from Application a where a.customer.id = ?1 and a.status='REJECTED'")
	Collection<Application> findRejectedApplicationsByCustomer(int id);

	@Query("select a from Application a where a.customer.id = ?1 and a.status='PENDING'")
	Collection<Application> findPendingApplicationsByCustomer(int id);

	@Query("select (sum(case when a.status='REJECTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioRejectedApplications();

	@Query("select (sum(case when a.status='ACCEPTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioAcceptedApplications();

	@Query("select (sum(case when a.status='PENDING' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioPendingApplications();

	@Query("select case when (count(a) > 0) then true else false end from Application a where a.customer.id = ?1 and a.workingOut.trainer.id = ?2 and a.status = 'ACCEPTED'")
	boolean existApplicationAcceptedBetweenCustomerTrainer(int customerId, int trainerId);
}
