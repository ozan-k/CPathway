package com.ozank.cpathway.dataclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
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

    private void readData() {
        Path path = Path.of("src","main","resources","com","ozank","cpathway","reactionData.json");
        File theFile = new File(path.toString());
        if (theFile.exists()) {
            try {
                FileReader fileReader = new FileReader(theFile);
                Type type = new TypeToken<HashMap<String, SpeciesReactions>>() {
                }.getType();
                Gson gson = new Gson();
                this.allReactionData = gson.fromJson(fileReader, type);
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
