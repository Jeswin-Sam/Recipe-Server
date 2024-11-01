package com.example.RecipeServer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
public class Recipe {
    String recipeName;
    String procedure;
    ArrayList<String> ingredients;

}
