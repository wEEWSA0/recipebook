package com.weewsa.recipebookv3;

import com.weewsa.recipebookv3.model.ingredient.Ingredient;
import com.weewsa.recipebookv3.model.step.Step;
import com.weewsa.recipebookv3.model.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Time cookingTime;
    private Short personsCount;
    private String creatorLogin;
    private Set<Tag> recipeTags;
    private Set<Step> steps;
    private Set<Ingredient> ingredients;
    private Integer likedUsersCount;
    private Integer favouriteUsersCount;
}
