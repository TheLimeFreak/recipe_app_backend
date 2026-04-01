package no.thomasbirk.recipe.services;

import no.thomasbirk.recipe.dto.RecipeResponse;
import no.thomasbirk.recipe.exceptions.RecipeNotFoundException;
import no.thomasbirk.recipe.models.Recipe;
import no.thomasbirk.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeResponse getRecipeById(long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getDescription());
    }
}
