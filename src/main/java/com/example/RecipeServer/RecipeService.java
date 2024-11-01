package com.example.RecipeServer;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RecipeService {

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
        System.out.println(ingredientList);
        return ingredientList;
    }







}
