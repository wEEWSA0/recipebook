package com.weewsa.recipebookv3.service.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReactionToRecipeRequest {
    @JsonProperty
    private Long recipeId;
    @JsonProperty
    private boolean isActive;
}
