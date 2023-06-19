package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    void deleteAllByRecipeId(Long recipeId);
}
