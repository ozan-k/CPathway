package com.ozank.cpathway.dataclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class ToplevelPathwayDataModel {
    private HashMap<String, Species> theMap;
    public ToplevelPathwayDataModel() {

        this.readData();
    }

    public HashMap<String, Species> getTheMap() {
        return theMap;
    }

    private void readData() {
        File theFile = new File("pathwayHierarchyData.json");
        if (theFile.exists()) {
            // System.out.println("File exists");
            try {
                FileReader fileReader = new FileReader(theFile);
                Type type = new TypeToken<HashMap<String, Species>>() {}.getType();
                Gson gson = new Gson();
                this.theMap = gson.fromJson(fileReader, type);
                fileReader.close();
            } catch (FileNotFoundException e) {
                //throw new RuntimeException(e);
                System.err.println("Error in creating a FileReader object.");
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.err.println("Error in closing the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

}
