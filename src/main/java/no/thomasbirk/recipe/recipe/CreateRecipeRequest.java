package no.thomasbirk.recipe.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRecipeRequest(
        @NotBlank
        @Size(max = 255)
        String title,
        @Size(max = 4000)
        String description
) {
}
