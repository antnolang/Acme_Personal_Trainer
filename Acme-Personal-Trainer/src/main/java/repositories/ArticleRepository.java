
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from Article a where a.nutritionist.id = ?1")
	Collection<Article> findArticlesByNutritionist(int id);

	@Query("select a from Article a where a.isFinalMode = true")
	Collection<Article> findFinalArticle();

}
