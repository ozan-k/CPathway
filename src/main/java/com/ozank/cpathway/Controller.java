package com.ozank.cpathway;

import com.ozank.cpathway.dataclasses.Pathway;
import com.ozank.cpathway.dataclasses.Species;
import com.ozank.cpathway.dataclasses.ToplevelPathwayDataModel;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

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
    @FXML
    public Button okButton;
    @FXML
    public VBox centerControlCheckboxes;
    private static HashMap<String, Species> theMap;
    private HashMap<String,String> pathwayNames = new HashMap<>();
    private Set<String> chosenPathways = new HashSet<>();
    private HashMap<String,HashSet<CheckBox>> checkBoxHashMap = new HashMap<>();

    HostServices hostServices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    public void start(){
        initiateSpeciesSelection();
        disableControls();

    }

    @FXML
    protected void onChooseSpecies() {
        //        if (hostServices==null){
        //            setGetHostController(dummyMolecule.getHostServices());
        //        } else {
        //            dummyMolecule.setHostServices(hostServices);
        //        }
        if (!(speciesComboBox.getSelectionModel().isEmpty()) && (speciesComboBox.getValue() != "Select species")){
            String choiceKey= speciesComboBox.getValue().replace(" ","_");
            for (Pathway x : theMap.get(choiceKey).getTopLevelPathways()) {
                addCheckBox(x.getDisplayName(), x.getStId());
            }
            speciesComboBox.setDisable(true);
            okButton.setDisable(true);
            resetButton.setDisable(false);
            submitPathwaysButton.setDisable(false);
            centerPaneVBox.visibleProperty().set(true);
        }
    }

    private void addCheckBox(String label,String stId){
        pathwayNames.put(stId,label);
        String choiceKey= speciesComboBox.getValue().replace(" ","_");
        theMap.get(choiceKey).getNames().put(stId,label);
        VBox root = new VBox();
        HBox hbox = new HBox();
        VBox innerVBox = new VBox();
        CheckBox checkbox = new CheckBox(label+"\t\t");
        CheckBox allCheckBox = new CheckBox("all");
        hbox.getChildren().add(checkbox);
        hbox.getChildren().add(allCheckBox);
        root.getChildren().add(hbox);
        root.getChildren().add(innerVBox);
        centerControlCheckboxes.getChildren().add(root);
        allCheckBox.setDisable(true);
        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (checkbox.isSelected()) {
                    chosenPathways.add(stId);
                    //,label);
                    allCheckBox.setDisable(false);
                    addChildrenToInnerVBox(innerVBox,new ArrayList<String>(Collections.singleton(stId)),1);
                    // ~~~~~~~~~~~~~~~~
                } else {
                    chosenPathways.remove(stId);
                    allCheckBox.setSelected(false);
                    allCheckBox.setDisable(true);
                    innerVBox.getChildren().clear();
                    removeChildrenFromSelectedPathways(new ArrayList<String>(Collections.singleton(stId)),1);
                }
            }
        });
        allCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                ArrayList<String> parentList = new ArrayList<>(Collections.singleton(stId));
                if (allCheckBox.isSelected()){
                    addChildrenToSelectedPathways(parentList, 1);
                } else {
                    removeChildrenFromSelectedPathways(parentList,1);
                }
            }
        });
    }

    private void addChildrenToSelectedPathways(ArrayList<String> stIdList, int k){
        String choiceKey= speciesComboBox.getValue().replace(" ","_");
        for (String stId: stIdList){
            if (k!=1) {
                chosenPathways.add(stId);
                checkAllCheckBoxesFor(stId);
            }
            ArrayList<String> children = theMap.get(choiceKey).getHierarchy().get(stId);
            if (children!=null) {
                addChildrenToSelectedPathways(children,k+1);
            }
        }
    }

    private void removeChildrenFromSelectedPathways(ArrayList<String> stIdList,int k){
        String choiceKey= speciesComboBox.getValue().replace(" ","_");
        for (String stId: stIdList){
            if (k!=1 && chosenPathways.contains(stId)) {
                chosenPathways.remove(stId);
                uncheckAllCheckBoxesFor(stId);
            }
            ArrayList<String> children = theMap.get(choiceKey).getHierarchy().get(stId);
            if (children!=null) {
                removeChildrenFromSelectedPathways(children,k+1);
            }
        }
    }

    private void addChildrenToInnerVBox(VBox vbox,ArrayList<String> stIdList,int k){
        String choiceKey= speciesComboBox.getValue().replace(" ","_");
        for (String stId: stIdList) {
            if (k!=1){
                HBox subPathwayHBox = new HBox(new Label(nTab(k)));
                String displayName = theMap.get(choiceKey).getNames().get(stId);
                pathwayNames.put(stId,displayName);
                CheckBox subPathwayCheckBox = new CheckBox(displayName);
                subPathwayHBox.getChildren().add(subPathwayCheckBox);
                addCheckBoxToCheckBoxHashSet(stId,subPathwayCheckBox);
                vbox.getChildren().add(subPathwayHBox);
                subPathwayCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (subPathwayCheckBox.isSelected()){
                            chosenPathways.add(stId);
                            checkAllCheckBoxesFor(stId);
                        } else {
                            if (chosenPathways.contains(stId)){
                                chosenPathways.remove(stId);
                                uncheckAllCheckBoxesFor(stId);
                            }
                        }
                    }
                });
            }
            ArrayList<String> children = theMap.get(choiceKey).getHierarchy().get(stId);
            if (children!=null) {
                addChildrenToInnerVBox(vbox, children, k + 1);
            }
        }
    }



    private void checkAllCheckBoxesFor(String stId){
        if (checkBoxHashMap.containsKey(stId)) {
            for (CheckBox cb : checkBoxHashMap.get(stId)) {
                if (!cb.selectedProperty().get()) {
                    cb.selectedProperty().set(true);
                }
            }
        }
    }
    private void uncheckAllCheckBoxesFor(String stId){
        if (checkBoxHashMap.containsKey(stId)) {
            for (CheckBox cb : checkBoxHashMap.get(stId)) {
                if (cb.selectedProperty().get()) {
                    cb.selectedProperty().set(false);
                }
            }
        }
    }
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private void addCheckBoxToCheckBoxHashSet(String stId,CheckBox checkbox){
        if (!checkBoxHashMap.containsKey(stId)){ checkBoxHashMap.put(stId,new HashSet<>()); }
        checkBoxHashMap.get(stId).add(checkbox);
    }

    private String nTab(int n){
        String result = "";
        for (int i=1;i<n;i++){
            result +="\t";
        }
        return result;
    }


    private void disableControls(){
        runExperimentButton.setDisable(true);
        submitPathwaysButton.setDisable(true);
        resetButton.setDisable(true);
        perturbationSlider.setDisable(true);
        moleculeNumberSpinner.setDisable(true);
        simulationNumberSpinner.setDisable(true);
        saveGraphTab.setDisable(true);
        centerPaneVBox.visibleProperty().set(false);
    }

    private void initiateSpeciesSelection(){
        ObservableList<String> speciesList = FXCollections.observableArrayList();
        ToplevelPathwayDataModel species = new ToplevelPathwayDataModel();
        theMap = species.getTheMap();
        speciesList.add("Select species");
        for (String key : theMap.keySet()) {
            speciesList.add(key.replace("_"," "));
        }
        speciesComboBox.setItems(speciesList);
        speciesComboBox.setValue("Select species");
    }
}