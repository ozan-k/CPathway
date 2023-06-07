package com.ozank.cpathway.fluxgraph;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;


public class GraphEdge implements Comparable<GraphEdge> {
    private int sourceNumber;
    private int targetNumber;
    private String sourceStId;
    private String targetStId;
    private int moleculeNumber;
    private String moleculeStId;
    private String moleculeName;
    private int flux;

    private Hyperlink sourceURL;
    private Hyperlink targetURL;
    private Hyperlink moleculeURL;

    public GraphEdge(int sourceNumber,
                     int targetNumber,
                     String sourceStId,
                     String targetStId,
                     int moleculeNumber,
                     String moleculeStId,
                     String moleculeName,
                     int flux,
                     HostServices hostServices) {
        this.sourceNumber = sourceNumber;
        this.targetNumber = targetNumber;
        this.sourceStId = formatStId(sourceStId);
        this.targetStId = formatStId(targetStId);
        this.moleculeNumber = moleculeNumber;
        this.moleculeStId = moleculeStId;
        this.moleculeName = moleculeName;
        this.flux = flux;
        this.sourceURL = new Hyperlink(sourceStId);
        this.targetURL = new Hyperlink(targetStId);
        this.moleculeURL = new Hyperlink(moleculeStId);
        this.sourceURL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (sourceStId.equals("init")) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.contentTextProperty().set("The source is the initial state.");
                    a.show();
                } else {
                    hostServices.showDocument("https://reactome.org/content/detail/" + formatStId(sourceStId));
                }
            }
        });
        this.targetURL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                hostServices.showDocument("https://reactome.org/content/detail/" + formatStId(targetStId));
            }
        });
        this.moleculeURL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                hostServices.showDocument("https://reactome.org/content/detail/" + moleculeStId);
            }
        });

    }

    private String formatStId(String s){
        if (s.contains("_")) {
            return s.substring(0,s.indexOf("_"));
        } else {
            return s;
        }
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public String getSourceStId() {
        return sourceStId;
    }

    public String getTargetStId() {
        return targetStId;
    }

    public int getMoleculeNumber() {
        return moleculeNumber;
    }

    public String getMoleculeName() {
        return moleculeName;
    }

    public int getFlux() {
        return flux;
    }

    public String getMoleculeStId() {
        return moleculeStId;
    }

    public Hyperlink getSourceURL() {
        return sourceURL;
    }

    public Hyperlink getTargetURL() {
        return targetURL;
    }

    public Hyperlink getMoleculeURL() {
        return moleculeURL;
    }

    @Override
    public String toString() {
        return "GraphEdge{" + sourceStId +
                " (" + sourceNumber +
                ") --" +
                "[" + moleculeName + " (" +
                moleculeNumber + ") x " +
                flux + "]--> " +
                targetStId + " (" +
                targetNumber +") }";
    }

    @Override
    public int compareTo(GraphEdge o) {
        int v1 = Math.abs(this.flux);
        int v2 = Math.abs(o.getFlux());
        if (v1>v2){
            return -1;
        } else if (v2> v1) {
            return 1;
        } else {
            return 0;
        }
    }
}
