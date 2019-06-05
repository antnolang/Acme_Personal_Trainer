
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Audit a where a.curriculum.id = ?1")
	Collection<Audit> findAllByCurriculumId(int curriculumId);

	@Query("select a from Audit a where a.auditor.id = ?1")
	Collection<Audit> findAllByAuditorId(int auditorId);

	@Query("select a from Audit a where a.auditor.id = ?1 and a.curriculum.id = ?2")
	Audit findByAuditorIdCurriculumId(int auditorId, int curriculumId);
}
