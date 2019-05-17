
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Nutritionist;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Integer> {

}
