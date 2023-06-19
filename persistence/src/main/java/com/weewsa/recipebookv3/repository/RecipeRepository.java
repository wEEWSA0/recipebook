package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.recipe.Recipe;
import com.weewsa.recipebookv3.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Set<Recipe> findAllByNameContaining(String name);
    Set<Recipe> findAllByRecipeTagsIn(Set<Tag> recipeTags);
    Set<Recipe> findAllByRecipeTagsInAndNameContaining(Collection<Tag> recipeTags, String name);
}
