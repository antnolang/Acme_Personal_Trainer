
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select count(w) from WorkingOut w join w.categories c where c.id=?1")
	Integer numberOfWorkingOutsByCategory(int categoryId);
}
