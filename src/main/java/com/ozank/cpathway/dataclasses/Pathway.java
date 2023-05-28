package com.ozank.cpathway.dataclasses;

public class Pathway {
    private String schemaClass;
    private boolean isInDisease;
    private String displayName;
    private String stId;
    private String speciesName;
    private String[] name;

    public Pathway(String schemaClass, boolean isInDisease, String displayName, String stId, String speciesName, String[] name) {
        this.schemaClass = schemaClass;
        this.isInDisease = isInDisease;
        this.displayName = displayName;
        this.stId = stId;
        this.speciesName = speciesName;
        this.name = name;
    }

    public String getSchemaClass() {
        return schemaClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isInDisease() {
        return isInDisease;
    }

    public String getStId() {
        return stId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public String[] getName() {
        return name;
    }
}
