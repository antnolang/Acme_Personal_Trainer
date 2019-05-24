
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Session;
import domain.WorkingOut;

@Repository
public interface WorkingOutRepository extends JpaRepository<WorkingOut, Integer> {

	@Query("select w.ticker from WorkingOut w where w.ticker = ?1")
	String existTicker(String ticker);

	@Query("select w from WorkingOut w where w.trainer.id = ?1")
	Collection<WorkingOut> findAllWorkingOutsByTrainer(int id);

	@Query("select w from WorkingOut w where w.trainer.id = ?1 and w.isFinalMode = true")
	Collection<WorkingOut> findFinalWorkingOutsByTrainer(int id);

	@Query("select w from WorkingOut w where w.isFinalMode = true and w.startMoment >= ?1")
	Collection<WorkingOut> findAllVisible(Date now);

	@Query("select w.sessions from WorkingOut w where w.id=?1")
	Collection<Session> getSessionsByWorkingOut(int id);

	@Query("select w.categories from WorkingOut w where w.id=?1")
	Collection<Category> getCategoriesByWorkingOut(int id);

	// Requirement 4.2: The average, the minimum, the maximum, and the standard deviation of the number of applications per working-out.
	@Query("select avg(1.0*(select count(a) from Application a where a.workingOut.id=w.id)), min(1.0*(select count(a) from Application a where a.workingOut.id=w.id)), max(1.0*(select count(a) from Application a where a.workingOut.id=w.id)), stddev(1.0*(select count(a) from Application a where a.workingOut.id=w.id)) from WorkingOut w")
	Double[] findDataNumberApplicationPerWorkingOut();

	// Requirement 4.3: The average, the minimum, the maximum, and the standard deviation of the maximum price of the working-outs.
	@Query("select avg(1.0*w.price), min(1.0*w.price), max(1.0*w.price), stddev(1.0*w.price) from WorkingOut w")
	Double[] findDataPricePerWorkingOut();

	// Requirement 11.4.1
	@Query("select avg(1.0 * (select count(w) from WorkingOut w where w.trainer.id = t.id)), min(1.0 * (select count(w) from WorkingOut w where w.trainer.id = t.id)), max(1.0 * (select count(w) from WorkingOut w where w.trainer.id = t.id)),stddev(1.0 * (select count(w) from WorkingOut w where w.trainer.id = t.id)) from Trainer t)")
	Double[] findDataNumberWorkingOutPerTrainer();

	@Query("select w from WorkingOut w join w.sessions s where s.id=?1")
	WorkingOut findBySession(int sessionId);

	@Query("select s from WorkingOut w join w.sessions s where w.id=?1 order by s.startMoment ASC")
	List<Session> getSssionsOrdered(int id);
}
