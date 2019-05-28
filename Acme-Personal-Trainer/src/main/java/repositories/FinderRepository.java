
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	// Requirement 37.5.4
	@Query("select count(ff) from Finder ff where (ff.keyword is not null AND ff.keyword != '') OR (ff.category is not null) OR (ff.startPrice is not null) OR (ff.endPrice is not null) OR (ff.startDate is not null) OR (ff.endDate is not null)")
	Double findNumberNonEmptyFinder();

	// Requirement 37.5.4
	@Query("select count(f) from Finder f where (f.keyword is null OR f.keyword = '') AND (f.category is null) AND (f.startPrice is null) AND (f.endPrice is null) AND (f.startDate is null) AND (f.endDate is null)")
	Double findNumberEmptyFinder();

	@Query("select f from Finder f where f.customer.id = ?1")
	Finder findByCustomerId(int customerId);

	@Query("select f from Finder f join f.workingOuts w where w.id = ?1")
	Collection<Finder> findAllByWorkingOutId(int workingOutId);

}
