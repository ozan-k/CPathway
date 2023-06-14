package com.ozank.cpathway.dataclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        URL url = this.getClass().getClassLoader().getResource("pathwayHierarchyData.json");
        if (url != null) {
            // System.out.println("File exists");
            try {
                Reader fileReader = new InputStreamReader(url.openStream(), "UTF-8");
                Type type = new TypeToken<HashMap<String, Species>>() {
                }.getType();
                Gson gson = new Gson();
                this.theMap = gson.fromJson(fileReader, type);
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
