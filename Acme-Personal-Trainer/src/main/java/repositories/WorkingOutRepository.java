
package repositories;

import java.util.Collection;

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
	Collection<WorkingOut> findWorkingOutsByTrainer(int id);

	@Query("select w from WorkingOut w where w.isFinalMode = true")
	Collection<WorkingOut> findAllVisible();

	@Query("select w.sessions from WorkingOut w where w.id=?1")
	Collection<Session> getSessionsByWorkingOut(int id);

	@Query("select w.categories from WorkingOut w where w.id=?1")
	Collection<Category> getCategoriesByWorkingOut(int id);

}
