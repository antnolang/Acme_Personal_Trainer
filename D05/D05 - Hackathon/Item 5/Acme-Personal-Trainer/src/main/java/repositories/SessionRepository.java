
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

}
