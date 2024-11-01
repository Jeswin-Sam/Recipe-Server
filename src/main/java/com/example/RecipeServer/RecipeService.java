package com.example.RecipeServer;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RecipeService {

    static Recipe recipe1 = new Recipe("Fruit Salad", "Mix all the fruits",
            new ArrayList<String>(Arrays.asList(new String[]{"apple", "orange"})));
    static Recipe recipe2 = new Recipe("Fried Rice", "Marinate the chicken and fry",
            new ArrayList<String>(Arrays.asList(new String[]{"rice", "chicken", "egg"})));
    static Recipe recipe3 = new Recipe("French Fries", "Fry the potato and add salt",
            new ArrayList<String>(Arrays.asList(new String[]{"potato"})));
    static Recipe recipe4 = new Recipe("Curd Rice", "Mix the curd and rice and add salt",
            new ArrayList<String>(Arrays.asList(new String[]{"curd", "rice"})));
    static Recipe recipe5 = new Recipe("Mango Yogurt", "Mix the curd, mango and sugar",
            new ArrayList<String>(Arrays.asList(new String[]{"curd", "mango"})));
    static Recipe recipe6 = new Recipe("Omelette", "Beat the egg and add chopped onion. Pour on pan",
            new ArrayList<String>(Arrays.asList(new String[]{"egg", "onion"})));
    static Recipe recipe7 = new Recipe("Apple Juice", "Blend the apple and strain",
            new ArrayList<String>(Arrays.asList(new String[]{"apple"})));


    static ArrayList<Recipe> recipesList = new ArrayList<>(Arrays.asList(recipe1,recipe2,recipe3,recipe4,recipe5,recipe6,recipe7));


    // Get the ingredient list from the python script
    public ArrayList<String> getIngredientsFromPython(String imagePath) {
        ArrayList<String> ingredientList = new ArrayList<>();

        String pythonScriptPath = "src/main/python/image_test.py";

        try {
            // Run Python script
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, imagePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read output from Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine(); // Assumes single-line output

            if (output != null) {
                // Process list by splitting the string, assuming a comma-separated format
                String[] ingredientsArray = output
                        .replace("[", "")
                        .replace("]", "")
                        .replace("'", "")
                        .split(", ");
                ingredientList = new ArrayList<>(Arrays.asList(ingredientsArray));
            }

            // Wait for process to complete
            process.waitFor();

        } catch (Exception e) {
            System.err.println("Error while calling Python script: " + e.getMessage());
        }
        return ingredientList;
    }



    public static ArrayList<Recipe> findRecipesWithIngredients(ArrayList<String> availableIngredients) {
        ArrayList<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipe : recipesList)
            // Check if all recipe's ingredients are in the availableIngredients
            if (availableIngredients.containsAll(recipe.getIngredients()))
                matchingRecipes.add(recipe);

        return matchingRecipes;
    }


    public ArrayList<Recipe> findPossibleRecipes(String imagePath) {
        ArrayList<String> availableIngredients = getIngredientsFromPython(imagePath);
        ArrayList<Recipe> possibleRecipes = findRecipesWithIngredients(availableIngredients);
        return possibleRecipes;
    }
}
