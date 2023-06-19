package com.weewsa.recipebookv3.model.ingredient;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientId implements Serializable {
    private Long recipeId;
    private Short ingredientNumber;
}