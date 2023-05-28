package com.ozank.cpathway;

import com.ozank.cpathway.dataclasses.Species;
import com.ozank.cpathway.dataclasses.ToplevelPathwayDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public ComboBox<String> speciesComboBox;
    @FXML
    public Button runExperimentButton;
    @FXML
    public Button submitPathwaysButton;
    @FXML
    public Button resetButton;
    @FXML
    public Slider perturbationSlider;
    @FXML
    public Spinner moleculeNumberSpinner;
    @FXML
    public Spinner simulationNumberSpinner;
    @FXML
    public MenuItem saveGraphTab;
    @FXML
    public VBox centerPaneVBox;
    private static HashMap<String, Species> theMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    public void start(){
        ObservableList<String> speciesList = FXCollections.observableArrayList();
        ToplevelPathwayDataModel species = new ToplevelPathwayDataModel();
//        theMap = species.getTheMap();
//        speciesList.add("Select species");
//        for (String key : theMap.keySet()) {
//            speciesList.add(key.replace("_"," "));
//        }
//        speciesComboBox.setItems(speciesList);
//        speciesComboBox.setValue("Select species");
//        runExperimentButton.setDisable(true);
//        submitPathwaysButton.setDisable(true);
//        resetButton.setDisable(true);
//        perturbationSlider.setDisable(true);
//        moleculeNumberSpinner.setDisable(true);
//        simulationNumberSpinner.setDisable(true);
//        saveGraphTab.setDisable(true);
//        centerPaneVBox.visibleProperty().set(false);
    };

}