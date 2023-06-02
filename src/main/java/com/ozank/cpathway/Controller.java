package com.ozank.cpathway;

import com.ozank.cpathway.dataclasses.*;
import com.ozank.cpathway.fluxgraph.FluxGraph;
import com.ozank.cpathway.modelbuilder.ModelBuilder;
import com.ozank.cpathway.simulation.Matrix;
import com.ozank.cpathway.simulation.Simulation;
import com.ozank.cpathway.simulation.SimulationModel;
import com.ozank.cpathway.simulation.TripleIndex;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
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
    public Spinner<Integer> moleculeNumberSpinner;
    @FXML
    public Spinner<Integer> simulationNumberSpinner;
    @FXML
    public MenuItem saveGraphTab;
    @FXML
    public VBox centerPaneVBox;
    @FXML
    public Button okButton;
    @FXML
    public VBox centerControlCheckboxes;
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public GridPane topGridPane;

    private static HashMap<String, Species> theMap;
    private final HashMap<String, String> pathwayNames = new HashMap<>();
    private final Set<String> chosenPathways = new HashSet<>();
    private final HashMap<String, HashSet<CheckBox>> checkBoxHashMap = new HashMap<>();
    private HashMap<String, HashMap<String, ReactionData>> modelReactions = new HashMap<>();
    ObservableList<Molecule> moleculeData = FXCollections.observableArrayList();
    private FluxGraph fluxGraph;
    private HashSet<String> deselectedMolecules = new HashSet<>();
    private List<String> modelMolecules;
    private HashMap<String, String> moleculeNames;

    HostServices hostServices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    public void start() {
        initiateSpeciesSelection();
        disableControls();
        tester();
    }

    public void tester(){
        speciesComboBox.setValue("Homo sapiens");
        onChooseSpecies();
    }
    @FXML
    protected void onRunExperiment() {
        runExperimentButton.setDisable(true);
        simulationNumberSpinner.setDisable(true);
        moleculeNumberSpinner.setDisable(true);
        perturbationSlider.setDisable(true);
        centerPaneVBox.getChildren().clear();
        topGridPane.getChildren().clear();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        runExperimentsWithProgressBar();
    }

    public void runExperimentsWithProgressBar(){
        ProgressForm pForm = new ProgressForm();
        Task<Void> task = new Task() {
            @Override
            public Void call() //throws InterruptedException
            {
                long simulationNumber = simulationNumberSpinner.getValue();
                updateProgress(simulationNumber, simulationNumber*2);
                runExperiments();
                Platform.runLater(() -> {
                    //HashSet<String> fluxMolecules = getFluxMolecules(fluxGraph);
                    //fluxGraph.listFluxGraph(centerPaneVBox,mainBorderPane,deselectedMolecules,10,true);
                    //initiateFluxControls(fluxMolecules);
                });
                return null;
            }
        };
        pForm.activateProgressBar(task);
        task.setOnSucceeded(event -> {
            pForm.getDialogStage().close();
        });
        pForm.getDialogStage().show();
        Thread thread = new Thread(task);
        thread.start();
    }

    private Matrix<TripleIndex> runExperiments(){
        int moleculeNumber = moleculeNumberSpinner.getValue();
        int perturbation = (int) Math.round(perturbationSlider.getValue());
        ModelBuilder modelBuilder = new ModelBuilder(modelReactions, modelMolecules, moleculeNumber, perturbation);
        Map<String,Integer> perturbed = modelBuilder.getCaseInitialState();
        Map<String,Integer> control = modelBuilder.getControlInitialState();
        Matrix<TripleIndex> result = new Matrix<>();

        System.out.println(perturbation);
        SimulationModel modelControl = new SimulationModel(modelBuilder.getReactions(),control);
        //  Simulation simulation;
//        for (int i=0;i<2;i++){
//            simulation = new Simulation(model);
//            simulation.simulateWithStepNumber(100*moleculeNumber);
//            simulation.getF();
//            System.out.println("-----");
//        }
        Matrix<TripleIndex> perturbedM = new Matrix<>();
        modelControl.printReactions();
        modelControl.printState();

        System.out.println();
        System.out.println();
        SimulationModel modelCase = new SimulationModel(modelBuilder.getReactions(),perturbed);
        modelCase.printReactions();
        modelCase.printState();

        return perturbedM;
    }


    @FXML
    protected void onChooseSpecies() {
        //        if (hostServices==null){
        //            setGetHostController(dummyMolecule.getHostServices());
        //        } else {
        //            dummyMolecule.setHostServices(hostServices);
        //        }
        boolean check = !speciesComboBox.getSelectionModel().isEmpty() && !speciesComboBox.getValue().equals("Select species");
        if (check) {
            String choiceKey = speciesComboBox.getValue().replace(" ", "_");
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

    @FXML
    public void onSubmitPathways() {
        submitPathwaysButton.setDisable(true);
        centerPaneVBox.getChildren().clear();
        runExperimentButton.setDisable(false);
        perturbationSlider.setDisable(false);
        moleculeNumberSpinner.setDisable(false);
        simulationNumberSpinner.setDisable(false);
        String choiceKey = speciesComboBox.getValue().replace(" ", "_");
        ReactionDataModel reactionDataModel = new ReactionDataModel(choiceKey, chosenPathways);
        modelReactions = reactionDataModel.getReactionData();
        moleculeNames = ModelMolecules.getMoleculeNames(reactionDataModel.getParticipantNames());
        displayModelParticipantsAndPerturb();
    }

    private void displayModelParticipantsAndPerturb() {
        modelMolecules = new ModelMolecules(modelReactions).getModelMolecules();
        for (String stId : moleculeNames.keySet()) {
            if (modelMolecules.contains(stId)) {
                moleculeData.add(new Molecule(stId, moleculeNames.get(stId)));
            }
        }
        FXCollections.sort(moleculeData);
        TableView tableView = new TableView<>();
        TableColumn perturbationCol = new TableColumn("Perturbation");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn stIdCol = new TableColumn("Reactome StId");
        tableView.getColumns().addAll(perturbationCol, nameCol, stIdCol);
        perturbationCol.setCellValueFactory(new PropertyValueFactory<Molecule, ComboBox<String>>("choiceComboBox"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Molecule, String>("displayName"));
        stIdCol.setCellValueFactory(new PropertyValueFactory<Molecule, Hyperlink>("moleculeHyperLink"));
        tableView.setItems(moleculeData);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        VBox tableViewVbox = new VBox();
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search");
        tableViewVbox.getChildren().add(searchTextField);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            tableView.setItems(filterMoleculeData(newValue));
        });
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Label moleculesTitle = new Label("Molecules");
        centerPaneVBox.getChildren().add(tableViewVbox);
        tableViewVbox.setAlignment(Pos.CENTER);
        tableViewVbox.getChildren().addAll(moleculesTitle, tableView);
        tableView.prefHeightProperty().bind(mainBorderPane.heightProperty());
        tableView.prefWidthProperty().bind(mainBorderPane.widthProperty());
        mainBorderPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            tableView.prefHeightProperty().bind(mainBorderPane.heightProperty());
            tableView.prefWidthProperty().bind(mainBorderPane.widthProperty());
        });
        mainBorderPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            tableView.prefHeightProperty().bind(mainBorderPane.heightProperty());
            tableView.prefWidthProperty().bind(mainBorderPane.widthProperty());
        });
    }

    public ObservableList<Molecule> filterMoleculeData(String searchText) {
        ObservableList<Molecule> result = FXCollections.observableArrayList();
        for (Molecule m : moleculeData) {
            if (m.getDisplayName()
                    .substring(0, searchText.length())
                    .equals(searchText)) {
                result.add(m);
            }
        }
        return result;
    }

    private void addCheckBox(String label, String stId) {
        pathwayNames.put(stId, label);
        String choiceKey = speciesComboBox.getValue().replace(" ", "_");
        theMap.get(choiceKey).getNames().put(stId, label);
        VBox root = new VBox();
        HBox hbox = new HBox();
        VBox innerVBox = new VBox();
        CheckBox checkbox = new CheckBox(label + "\t\t");
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
                    addChildrenToInnerVBox(innerVBox, new ArrayList<>(Collections.singleton(stId)), 1);
                    // ~~~~~~~~~~~~~~~~
                } else {
                    chosenPathways.remove(stId);
                    allCheckBox.setSelected(false);
                    allCheckBox.setDisable(true);
                    innerVBox.getChildren().clear();
                    removeChildrenFromSelectedPathways(new ArrayList<>(Collections.singleton(stId)), 1);
                }
            }
        });
        allCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                ArrayList<String> parentList = new ArrayList<>(Collections.singleton(stId));
                if (allCheckBox.isSelected()) {
                    addChildrenToSelectedPathways(parentList, 1);
                } else {
                    removeChildrenFromSelectedPathways(parentList, 1);
                }
            }
        });
    }

    private void addChildrenToSelectedPathways(ArrayList<String> stIdList, int k) {
        String choiceKey = speciesComboBox.getValue().replace(" ", "_");
        for (String stId : stIdList) {
            if (k != 1) {
                chosenPathways.add(stId);
                checkAllCheckBoxesFor(stId);
            }
            ArrayList<String> children = theMap.get(choiceKey).getHierarchy().get(stId);
            if (children != null) {
                addChildrenToSelectedPathways(children, k + 1);
            }
        }
    }

    private void removeChildrenFromSelectedPathways(ArrayList<String> stIdList, int k) {
        String choiceKey = speciesComboBox.getValue().replace(" ", "_");
        for (String stId : stIdList) {
            if (k != 1 && chosenPathways.contains(stId)) {
                chosenPathways.remove(stId);
                uncheckAllCheckBoxesFor(stId);
            }
            ArrayList<String> children = theMap.get(choiceKey).getHierarchy().get(stId);
            if (children != null) {
                removeChildrenFromSelectedPathways(children, k + 1);
            }
        }
    }

    private void addChildrenToInnerVBox(VBox vbox, ArrayList<String> stIdList, int k) {
        String choiceKey = speciesComboBox.getValue().replace(" ", "_");
        for (String stId : stIdList) {
            if (k != 1) {
                HBox subPathwayHBox = new HBox(new Label(nTab(k)));
                String displayName = theMap.get(choiceKey).getNames().get(stId);
                pathwayNames.put(stId, displayName);
                CheckBox subPathwayCheckBox = new CheckBox(displayName);
                subPathwayHBox.getChildren().add(subPathwayCheckBox);
                addCheckBoxToCheckBoxHashSet(stId, subPathwayCheckBox);
                vbox.getChildren().add(subPathwayHBox);
                subPathwayCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (subPathwayCheckBox.isSelected()) {
                            chosenPathways.add(stId);
                            checkAllCheckBoxesFor(stId);
                        } else {
                            if (chosenPathways.contains(stId)) {
                                chosenPathways.remove(stId);
                                uncheckAllCheckBoxesFor(stId);
                            }
                        }
                    }
                });
            }
            ArrayList<String> children = theMap.get(choiceKey).getHierarchy().get(stId);
            if (children != null) {
                addChildrenToInnerVBox(vbox, children, k + 1);
            }
        }
    }


    private void checkAllCheckBoxesFor(String stId) {
        if (checkBoxHashMap.containsKey(stId)) {
            for (CheckBox cb : checkBoxHashMap.get(stId)) {
                if (!cb.selectedProperty().get()) {
                    cb.selectedProperty().set(true);
                }
            }
        }
    }

    private void uncheckAllCheckBoxesFor(String stId) {
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

    private void addCheckBoxToCheckBoxHashSet(String stId, CheckBox checkbox) {
        if (!checkBoxHashMap.containsKey(stId)) {
            checkBoxHashMap.put(stId, new HashSet<>());
        }
        checkBoxHashMap.get(stId).add(checkbox);
    }

    private String nTab(int n) {
        String t ="\t";
        return t.repeat(n-1);
    }


    private void disableControls() {
        runExperimentButton.setDisable(true);
        submitPathwaysButton.setDisable(true);
        resetButton.setDisable(true);
        perturbationSlider.setDisable(true);
        moleculeNumberSpinner.setDisable(true);
        simulationNumberSpinner.setDisable(true);
        saveGraphTab.setDisable(true);
        centerPaneVBox.visibleProperty().set(false);
    }

    private void initiateSpeciesSelection() {
        Molecule.getPerturbedUpMolecules().clear();
        Molecule.getPerturbedDownMolecules().clear();
        ObservableList<String> speciesList = FXCollections.observableArrayList();
        ToplevelPathwayDataModel species = new ToplevelPathwayDataModel();
        theMap = species.getTheMap();
        speciesList.add("Select species");
        for (String key : theMap.keySet()) {
            speciesList.add(key.replace("_", " "));
        }
        speciesComboBox.setItems(speciesList);
        speciesComboBox.setValue("Select species");
    }

    @FXML
    protected void onReset() throws IOException {
        restartApplication();
    }

    @FXML
    public void restartApplication() throws IOException {
        theMap.clear();
        chosenPathways.clear();
        modelReactions.clear();
        moleculeData.clear();
        pathwayNames.clear();
        deselectedMolecules.clear();
        checkBoxHashMap.clear();
        centerControlCheckboxes.getChildren().clear();
        fluxGraph = null;
        moleculeNames = null;
        modelMolecules = null;
        speciesComboBox.setDisable(false);
        okButton.setDisable(false);
        resetButton.setDisable(true);
        speciesComboBox.valueProperty().set(null);
        saveGraphTab.setDisable(true);
        start();
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        mainBorderPane.getChildren().clear();
        mainBorderPane = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 700, 600);
        stage.setScene(scene);
        stage.show();
        //setGetHostController(dummyMolecule.getHostServices());
    }
}