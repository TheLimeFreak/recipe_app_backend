package no.thomasbirk.recipe.dto;

public record RecipeResponse(
        Long id,
        String title,
        String description
) {
}
