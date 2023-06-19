package com.weewsa.recipebookv3.service.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String name;
    private String description;
    private String imageUrl;
    private Time cookingTime;
    private Short personsCount;
    private Set<TagRequest> recipeTags;
    private Set<StepRequest> steps;
    private Set<IngredientRequest> ingredients;
}
