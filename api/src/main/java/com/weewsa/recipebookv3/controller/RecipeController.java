package com.weewsa.recipebookv3.controller;

import com.weewsa.recipebookv3.model.user.Role;
import com.weewsa.recipebookv3.service.authenticate.JWTService;
import com.weewsa.recipebookv3.service.authenticate.exception.InvalidToken;
import com.weewsa.recipebookv3.service.authenticate.exception.NotAuthorized;
import com.weewsa.recipebookv3.service.authenticate.exception.NotEnoughRights;
import com.weewsa.recipebookv3.service.recipe.RecipeResponseCreator;
import com.weewsa.recipebookv3.service.recipe.RecipeService;
import com.weewsa.recipebookv3.service.recipe.dto.*;
import com.weewsa.recipebookv3.service.recipe.exception.RecipeNotFound;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final JWTService jwtService;
    private final RecipeService recipeService;
    private final RecipeResponseCreator recipeResponseCreator;
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            var recipe = recipeService.getRecipeById(id);

            return ResponseEntity.ok(recipeResponseCreator.createResponse(recipe));
        } catch (RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody RecipeRequest recipeRequest, HttpServletRequest request) {
        try {
            var claims = jwtService.getClaimsIfHasUserAccess(request);

            recipeService.createRecipe(recipeRequest, claims.getSubject());
        } catch (NotAuthorized | InvalidToken | NotEnoughRights e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("Created");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> edit(@PathVariable Long id, @RequestBody RecipeRequest recipeRequest, HttpServletRequest request) {
        try {
            var claims = jwtService.getClaimsIfHasUserAccess(request);

            recipeService.editRecipe(id, recipeRequest, claims.getSubject());
        } catch (NotAuthorized | InvalidToken | NotEnoughRights | RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("Saved");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            var claims = jwtService.getClaimsIfHasUserAccess(request);

            recipeService.deleteRecipe(id, claims.getSubject());
        } catch (NotAuthorized | InvalidToken | NotEnoughRights | RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("Saved");
    }

    @PostMapping("get-with-name")
    public ResponseEntity<?> getByName(@RequestBody NameRequest nameRequest) {
        try {
            var recipes = recipeService.getRecipesWithName(nameRequest.getName());

            return ResponseEntity.ok(recipeResponseCreator.createSetOfResponse(recipes));
        } catch (RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("get-with-tags")
    public ResponseEntity<?> getByTags(@RequestBody Set<TagRequest> tags) {
        try {
            var recipes = recipeService.getRecipesWithTags(tags);

            return ResponseEntity.ok(recipeResponseCreator.createSetOfResponse(recipes));
        } catch (RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("get-with-name-and-tags")
    public ResponseEntity<?> getByNameAndTags(@RequestBody NameAndTagsRequest request) {
        try {
            var recipes = recipeService.getRecipesWithTagsAndName(request.getTags(), request.getName());

            return ResponseEntity.ok(recipeResponseCreator.createSetOfResponse(recipes));
        } catch (RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("like")
    public ResponseEntity<String> like(@RequestBody UserReactionToRecipeRequest userReactionToRecipeRequest, HttpServletRequest request) {
        try {
            var claims = jwtService.getClaimsIfHasUserAccess(request);

            recipeService.likeRecipe(userReactionToRecipeRequest, claims.getSubject());

            return ResponseEntity.ok("Saved");
        } catch (RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("favourite")
    public ResponseEntity<String> favourite(@RequestBody UserReactionToRecipeRequest userReactionToRecipeRequest, HttpServletRequest request) {
        try {
            var claims = jwtService.getClaimsIfHasUserAccess(request);

            recipeService.favouriteRecipe(userReactionToRecipeRequest, claims.getSubject());

            return ResponseEntity.ok("Saved");
        } catch (RecipeNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
