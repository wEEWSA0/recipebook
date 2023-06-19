package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByName(String name);
//    Set<Tag> findAllByTagRecipesIsIn(Collection<Set<Recipe>> tagRecipes);
}
