
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorkingOut;

@Repository
public interface WorkingOutRepository extends JpaRepository<WorkingOut, Integer> {

	@Query("select w.ticker from WorkingOut w where w.ticker = ?1")
	String existTicker(String ticker);

}
