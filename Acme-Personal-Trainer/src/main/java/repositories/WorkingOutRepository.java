
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingOutRepository extends JpaRepository<WorkingOut, Integer> {

}
