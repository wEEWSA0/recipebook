package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.step.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
    void deleteAllByRecipeId(Long recipeId);
}
