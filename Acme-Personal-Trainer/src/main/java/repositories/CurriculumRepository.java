
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select c from Curriculum c where c.trainer.id = ?1")
	Curriculum findByPrincipal(int trainerId);

	@Query("select c.id from Curriculum c where c.personalRecord.id = ?1")
	Integer findIdByPersonalRecordId(int personalRecordId);

	@Query("select c.id from Curriculum c join c.endorserRecords d where d.id = ?1")
	Integer findIdByEndorserRecordId(int endorserRecordId);

	@Query("select c.id from Curriculum c join c.educationRecords d where d.id = ?1")
	Integer findIdByEducationRecordId(int educationRecordId);

	@Query("select c.id from Curriculum c join c.miscellaneousRecords d where d.id = ?1")
	Integer findIdByMiscellaneousRecordId(int miscellaneousRecordId);

	@Query("select c.id from Curriculum c join c.professionalRecords d where d.id = ?1")
	Integer findIdByProfessionalRecordId(int professionalRecordId);

	@Query("select c from Curriculum c where c.trainer.id = ?1")
	Curriculum findByTrainerId(final int trainerId);

}
