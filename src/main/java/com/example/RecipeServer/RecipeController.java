package com.example.RecipeServer;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    // this function is for endpoint testing purpose only
    @GetMapping("/")
    public ResponseEntity<String> getIngredients(){
        return new ResponseEntity<>(recipeService.getJSONResponse("uploads/image.jpg"), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String uploadDir = "uploads/";
        File uploadDirectory = new File(uploadDir);

        // Create directory if it does not exist
        if (!uploadDirectory.exists())
            uploadDirectory.mkdir();

        // Save the file locally
        File filePath = new File(uploadDir + file.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(file.getBytes());
            return new ResponseEntity<>(recipeService.getJSONResponse("uploads/image.jpg"), HttpStatus.OK);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
