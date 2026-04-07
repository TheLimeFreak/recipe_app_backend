package no.thomasbirk.recipe.recipe;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@Valid @RequestBody CreateRecipeRequest request) {
        RecipeResponse response = recipeService.createRecipe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
