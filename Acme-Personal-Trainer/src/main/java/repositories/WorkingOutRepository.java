
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.WorkingOut;

@Repository
public interface WorkingOutRepository extends JpaRepository<WorkingOut, Integer> {

}
