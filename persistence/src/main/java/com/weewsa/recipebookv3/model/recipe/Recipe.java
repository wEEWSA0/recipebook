package com.weewsa.recipebookv3.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weewsa.recipebookv3.model.ingredient.Ingredient;
import com.weewsa.recipebookv3.model.step.Step;
import com.weewsa.recipebookv3.model.tag.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"ingredients", "steps"})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Time cookingTime;
    private Short personsCount;
    private Long creatorId;
    private Date createDate;
    @JsonIgnore
    @ManyToMany
    @Lazy
    @JoinTable(name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"recipe_id", "tag_id"})})
    private Set<Tag> recipeTags;
    @JsonIgnore
    @OneToMany(mappedBy = "recipe")
    @Lazy
    private Set<Step> steps;
    @JsonIgnore
    @OneToMany(mappedBy = "recipe")
    @Lazy
    private Set<Ingredient> ingredients;

}
