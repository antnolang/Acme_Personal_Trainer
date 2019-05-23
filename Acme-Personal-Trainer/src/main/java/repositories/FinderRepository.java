
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	// Requirement 37.5.4
	@Query("select count(f)/(select count(ff) from Finder ff where (ff.keyword is not null AND ff.keyword != '') OR (ff.category is not null AND ff.category != '') OR (ff.startPrice is not null) OR (ff.endPrice is not null) OR (ff.startDate is not null) OR (ff.endDate is not null))*1.0 from Finder f where (f.keyword is null OR f.keyword != '') AND (f.category is null OR f.category != '') AND (f.startPrice is null) AND (f.endPrice is null) AND (f.startDate is null) AND (f.endDate is null)")
	Double findRatioEmptyVsNonEmpty();

}
