package no.thomasbirk.recipe.recipe;

import no.thomasbirk.recipe.exceptions.RecipeNotFoundException;
import no.thomasbirk.recipe.models.Recipe;
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
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription()
        );
    }

    public RecipeResponse createRecipe(CreateRecipeRequest request) {
        Recipe recipe = new Recipe(
                request.title(),
                request.description()
        );

        Recipe saved = recipeRepository.save(recipe);

        return new RecipeResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription()
        );
    }
}
