package no.thomasbirk.recipe.recipe;

import no.thomasbirk.recipe.exceptions.RecipeNotFoundException;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private RecipeService recipeService;

    @Test
    @WithMockUser
    void getRecipeById_shouldReturnRecipe() throws Exception {
        RecipeResponse response = new RecipeResponse(
                1L,
                "Taco",
                "Meat, spice, salad"
        );

        given(recipeService.getRecipeById(1L)).willReturn(response);

        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Taco"))
                .andExpect(jsonPath("$.description").value("Meat, spice, salad"));
    }

    @Test
    @WithMockUser
    void createRecipe_shouldReturnCreatedRecipe() throws Exception {
        CreateRecipeRequest request = new CreateRecipeRequest(
                "Burger",
                "Patty, buns"
        );

        RecipeResponse response = new RecipeResponse(
                2L,
                "Burger",
                "Patty, buns"
        );

        given(recipeService.createRecipe(request)).willReturn(response);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Burger"))
                .andExpect(jsonPath("$.description").value("Patty, buns"));
    }

    @Test
    @WithMockUser
    void createRecipe_shouldReturn400WhenTitleIsBlank() throws Exception {
        String invalidJson = """
                {
                    "title": "",
                    "description": "Some text"
                }
                """;

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createRecipe_shouldReturn400WhenTitleIsTooLong() throws Exception {
        String tooLongTitle = "a".repeat(256);
        String invalidJson = """
                {
                    "title": "%s",
                    "description": "Some text"
                }
                """.formatted(tooLongTitle);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getRecipe_shouldReturn404WhenMissing() throws Exception {
        given(recipeService.getRecipeById(99L))
                .willThrow(new RecipeNotFoundException(99L));

        mockMvc.perform(get("/api/recipes/99"))
                .andExpect(status().isNotFound());
    }
}
