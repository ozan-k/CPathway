package com.ozank.cpathway.dataclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;

public class AllReactionData {
    private HashMap<String, SpeciesReactions> allReactionData;

    public AllReactionData() {
        this.readData();
    }

    public HashMap<String, SpeciesReactions> getAllReactionData() {
        return allReactionData;
    }

    private void readData(){
        URL url = this.getClass().getClassLoader().getResource("reactionData.json");
        if (url != null) {
            // System.out.println("File exists");
            try {
                Reader fileReader = new InputStreamReader(url.openStream(), "UTF-8");
                Type type = new TypeToken<HashMap<String, SpeciesReactions>>() {
                }.getType();
                Gson gson = new Gson();
                this.allReactionData = gson.fromJson(fileReader, type);
                fileReader.close();
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.err.println("Error in reading the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }


}
