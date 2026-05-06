package no.thomasbirk.recipe.recipe;

import no.thomasbirk.recipe.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
