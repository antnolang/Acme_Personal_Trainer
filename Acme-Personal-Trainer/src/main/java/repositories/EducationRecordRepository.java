
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

}
