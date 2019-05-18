
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select (sum(case when a.status='REJECTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioRejectedApplications();

	@Query("select (sum(case when a.status='ACCEPTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioAcceptedApplications();

	@Query("select (sum(case when a.status='PENDING' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioPendingApplications();

}
