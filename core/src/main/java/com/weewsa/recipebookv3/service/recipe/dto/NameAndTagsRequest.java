package com.weewsa.recipebookv3.service.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameAndTagsRequest {
    private Set<TagRequest> tags;
    private String name;
}
