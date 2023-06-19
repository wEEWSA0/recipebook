package com.weewsa.recipebookv3.model.step;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepId implements Serializable {
    private Long recipeId;
    private Short stepNumber;
}
