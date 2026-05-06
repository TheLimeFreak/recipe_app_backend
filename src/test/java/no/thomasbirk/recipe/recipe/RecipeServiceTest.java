package no.thomasbirk.recipe.recipe;

import no.thomasbirk.recipe.exceptions.RecipeNotFoundException;
import no.thomasbirk.recipe.models.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private RecipeService recipeService;

    @Test
    void getRecipeById_shouldReturnRecipeResponse() {
        Recipe recipe = new Recipe("Taco", "Meat, spice, salad");
        recipe.setId(1L);

        given(recipeRepository.findById(1L)).willReturn(Optional.of(recipe));

        RecipeResponse response = recipeService.getRecipeById(1L);

        assertEquals(1L, response.id());
        assertEquals("Taco", response.title());
        assertEquals("Meat, spice, salad", response.description());
    }

    @Test
    void getRecipeById_shouldReturn400WhenMissing() {
        given(recipeRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.getRecipeById(99L));
    }

    @Test
    void createRecipe_shouldSaveRecipeAndReturnResponse() {
        CreateRecipeRequest request = new CreateRecipeRequest(
                "Taco",
                "Meat, spice, salad"
        );

        Recipe savedRecipe = new Recipe(
                "Taco",
                "Meat, spice, salad"
        );
        savedRecipe.setId(2L);

        given(recipeRepository.save(any(Recipe.class)))
                .willReturn(savedRecipe);

        RecipeResponse response = recipeService.createRecipe(request);

        assertEquals(2L, response.id());
        assertEquals("Taco", response.title());
        assertEquals("Meat, spice, salad", response.description());
    }
}
