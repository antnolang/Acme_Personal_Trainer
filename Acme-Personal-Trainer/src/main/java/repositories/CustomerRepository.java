
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Query("select c from Customer c where c.isPremium=true")
	Collection<Customer> findPremiumCustomers();

	// Requirement 11.4.8
	@Query("select c from Customer c where (select count(a) from Application a where a.customer.id = c.id and a.status = 'ACCEPTED') > (select (1.1 * avg(1.0 * (select count(a) from Application a where a.customer.id = c.id and a.status = 'ACCEPTED'))) from Customer c) order by c.name")
	Collection<Customer> findUsualCustomers();

	// Requirement 37.5.3
	@Query("select e.customer from Endorsement e where e.trainerToCustomer = false group by e.customer order by count(e) desc")
	Page<Customer> findCustomerWriteMostEndorsement(Pageable page);

	@Query("select sum(a.workingOut.price) from Application a where a.customer.id = ?1 and a.status = 'ACCEPTED'")
	Double spendCustomer(int customerId);
}
