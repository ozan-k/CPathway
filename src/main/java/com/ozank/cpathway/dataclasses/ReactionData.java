package com.ozank.cpathway.dataclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class ReactionData {
    private HashMap<String, SpeciesReactions> theReactionMap;

    public ReactionData() {
        this.readData();
    }

    public HashMap<String, SpeciesReactions> getTheMap() {
        return theReactionMap;
    }

    private void readData() {
        File theFile = new File("reactionData.json");
        if (theFile.exists()) {
            try {
                FileReader fileReader = new FileReader(theFile);
                Type type = new TypeToken<HashMap<String, SpeciesReactions>>() {
                }.getType();
                Gson gson = new Gson();
                this.theReactionMap = gson.fromJson(fileReader, type);
                fileReader.close();
            } catch (FileNotFoundException e) {
                //throw new RuntimeException(e);
                System.err.println("Error in creating a FileReader object.");
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.err.println("Error in closing the file.");
            }
        }
    }
}
