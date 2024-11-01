package com.example.RecipeServer;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/")
    public ArrayList<Recipe> getIngredients(){
        return recipeService.findPossibleRecipes("src/main/resources/static/test_image.jpeg");
    }
}
