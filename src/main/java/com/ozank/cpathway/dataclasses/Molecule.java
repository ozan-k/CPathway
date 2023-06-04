package com.ozank.cpathway.dataclasses;

import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;

import java.util.HashSet;
import java.util.Set;

public class Molecule implements Comparable<Molecule>{
    private String stId;
    private String displayName;
    private ComboBox<String> choiceComboBox;
    private Hyperlink moleculeHyperLink;
    private static Set<String> perturbedUpMolecules= new HashSet<>();
    private static Set<String> perturbedDownMolecules = new HashSet<>();
    private static HostServices hostServices = null;

    public Molecule(String stId, String displayName) {
        if (this.perturbedUpMolecules ==null){
            perturbedUpMolecules = new HashSet();
            perturbedDownMolecules = new HashSet();
        }
        this.stId = stId;
        this.displayName = displayName;
        this.choiceComboBox = new ComboBox<>(FXCollections.observableArrayList("None","Up","Down"));
        this.choiceComboBox.setValue("None");
        choiceComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String value = choiceComboBox.getValue();
                if (value.equals("Up")){
                    perturbedUpMolecules.add(stId);
                    if (perturbedDownMolecules.contains(stId)){ perturbedDownMolecules.remove(stId);}
                } else if (value.equals("Down")){
                    perturbedDownMolecules.add(stId);
                    if (perturbedUpMolecules.contains(stId)){ perturbedUpMolecules.remove(stId);}
                } else {
                    if (perturbedUpMolecules.contains(stId)){ perturbedUpMolecules.remove(stId);}
                    if (perturbedDownMolecules.contains(stId)){ perturbedDownMolecules.remove(stId);}
                }
            }
        });
        this.moleculeHyperLink = new Hyperlink(stId);
        this.moleculeHyperLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Molecule.hostServices.showDocument("https://reactome.org/content/detail/" + stId);
            }
        });
    }

    public static Set getPerturbedUpMolecules() { return perturbedUpMolecules; }

    public static Set getPerturbedDownMolecules() { return perturbedDownMolecules; }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ComboBox<String> getChoiceComboBox() {
        return choiceComboBox;
    }

    public void setChoiceComboBox(ComboBox<String> choiceComboBox) {
        this.choiceComboBox = choiceComboBox;
    }

    public Hyperlink getMoleculeHyperLink() {
        return moleculeHyperLink;
    }

    public static void setHostServices(HostServices hostServices){
        Molecule.hostServices = hostServices;
    }

    public static HostServices getHostServices(){
        return Molecule.hostServices;
    }


    @Override
    public int compareTo(Molecule otherMolecule) {
        return CharSequence.compare(getDisplayName(), otherMolecule.getDisplayName());
    }
}
