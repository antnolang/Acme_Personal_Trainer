
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

	@Query("select t from Trainer t where t.userAccount.id=?1")
	Trainer findByUserAccount(int userAccountId);

	@Query("select a.workingOut.trainer from Application a where a.customer.id = ?1 and a.status = 'ACCEPTED'")
	Collection<Trainer> findTrainersWithAcceptedApplicationsByCustomer(int customerId);

	@Query("select c.trainer from Curriculum c where c.personalRecord.id = ?1")
	Trainer findByPersonalRecordId(int personalRecordId);

	@Query("select c.trainer from Curriculum c join c.endorserRecords d where d.id = ?1")
	Trainer findByEndorserRecordId(int endorserRecordId);

	@Query("select c.trainer from Curriculum c join c.educationRecords d where d.id = ?1")
	Trainer findByEducationRecordId(int educationRecordId);

	@Query("select c.trainer from Curriculum c join c.miscellaneousRecords d where d.id = ?1")
	Trainer findByMiscellaneousRecordId(int miscellaneousRecordId);

	@Query("select c.trainer from Curriculum c join c.professionalRecords d where d.id = ?1")
	Trainer findByProfessionalRecordId(int professionalRecordId);

	// Query dashboard 11.4.7
	@Query("select w.trainer from WorkingOut w where w.isFinalMode = true group by w.trainer having count(w)>= 1.1*((select count(w) from WorkingOut w where w.isFinalMode = true)/(select count(t) from Trainer t)) order by w.trainer.name")
	Collection<Trainer> findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName();

	@Query("select count(t)*1.0/(select count(t1) from Trainer t1) from Trainer t where t in (select e.trainer from Endorsement e where e.trainerToCustomer = false)")
	Double ratioTrainerWithEndorsement();
}
