
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

}
