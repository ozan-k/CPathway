package com.ozank.cpathway.fluxgraph;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import com.ozank.cpathway.simulation.Matrix;
import com.ozank.cpathway.simulation.SimulationModel;
import com.ozank.cpathway.simulation.TripleIndex;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class FluxGraph {
        private static HostServices hostServices;
        private ArrayList<GraphEdge> graph = new ArrayList<>();
        public FluxGraph(Matrix<TripleIndex> fluxes,
                         SimulationModel model,
                         HashMap<String, String> moleculeNames,
                         HostServices hostServices){
            this.hostServices= hostServices;
            int source;
            int target;
            int molecule;
            String sourceId;
            String targetId;
            String moleculeStId;
            String moleculeDisplayname;
            int flux;
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            for (TripleIndex t : fluxes.keySet()){
                source = t.getA();
                sourceId = model.getReactionIds()[source];
                target = t.getB();
                targetId = model.getReactionIds()[target];
                molecule = t.getC();
                moleculeStId = model.getMoleculesList().get(molecule);
                moleculeDisplayname = moleculeNames.get(moleculeStId);
                flux = fluxes.get(t);
                graph.add(new GraphEdge(source, target,
                                sourceId, targetId,
                                molecule, moleculeStId,
                                moleculeDisplayname,
                                flux,
                                hostServices));
                }
        }

    public HashSet<String> getFluxMolecules(){
        HashSet<String> result = new HashSet<>();
        String molecule;
        for (GraphEdge e : graph){
            molecule = e.getMoleculeName();
            if (!result.contains(molecule)){
                result.add(molecule);
            }
        }
        return result;
    }

        public ArrayList<GraphEdge> getGraph() {
            return graph;
        }

        public ArrayList<GraphEdge> getFilteredGraph(HashSet<String> deselectedMolecules, int cutOffValue, boolean includeInit){
            ArrayList<GraphEdge> filteredGraph = new ArrayList<>();
            for (GraphEdge e : this.graph){
                if (!deselectedMolecules.contains(e.getMoleculeName()) &&
                        Math.abs(e.getFlux()) >= cutOffValue) {
                    if (includeInit || !(e.getSourceNumber() == 0)) {
                        filteredGraph.add(e);
                    }
                }
            }
            return filteredGraph;
        }

        public void generateFluxGraphList(VBox centerControlCheckboxes, BorderPane mainBorderPane, HashSet<String> deselectedMolecules, int cuTOffValue, boolean includeInitSelected){
            centerControlCheckboxes.getChildren().clear();
            ObservableList<GraphEdge> graphData = FXCollections.observableArrayList(this.getFilteredGraph(deselectedMolecules,cuTOffValue,includeInitSelected));
            FXCollections.sort(graphData);
            // for (GraphEdge e : graphData){
            //    System.out.println(e);
            // }

            TableView tableView = new TableView<>();
            TableColumn source = new TableColumn("Source");
            TableColumn target = new TableColumn("Target");
            TableColumn moleculeStId = new TableColumn("Molecule StId");
            TableColumn weight = new TableColumn("Weight");
            TableColumn moleculeName = new TableColumn("Molecule Name");

            tableView.getColumns().addAll(source,target,moleculeStId,weight,moleculeName);
            source.setCellValueFactory(new PropertyValueFactory<GraphEdge, Hyperlink>("sourceURL"));
            target.setCellValueFactory(new PropertyValueFactory<GraphEdge,Hyperlink>("targetURL"));
            moleculeStId.setCellValueFactory(new PropertyValueFactory<GraphEdge,Hyperlink>("moleculeURL"));
            weight.setCellValueFactory(new PropertyValueFactory<GraphEdge,String>("flux"));
            moleculeName.setCellValueFactory(new PropertyValueFactory<GraphEdge,String>("moleculeName"));

            tableView.setItems(graphData);
            tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            tableView.prefHeightProperty().bind(mainBorderPane.heightProperty());
            tableView.prefWidthProperty().bind(mainBorderPane.heightProperty());
            VBox tableViewVbox = new VBox();
            Label moleculesTitle = new Label("Edges in Flux-Difference Graph");
            centerControlCheckboxes.getChildren().add(tableViewVbox);
            tableViewVbox.setAlignment(Pos.CENTER);
            tableViewVbox.getChildren().addAll(moleculesTitle,tableView);
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

        public void draw(HashSet<String> deselectedMolecules,int cuTOffValue, boolean includeInit){
            // https://github.com/brunomnsilva/JavaFXSmartGraph
            Digraph<GReaction, GEdge> edges = new DigraphEdgeList<>();
            HashMap<String, Vertex<GReaction>> reactionsHashMap = new HashMap<>();
            Vertex vertex;
            String source;
            String target;
            GEdge gEdge;
            HashMap<String,GEdge> graphEdges = new HashMap<>();
            for (GraphEdge edge : this.getFilteredGraph(deselectedMolecules,cuTOffValue,includeInit)){
                source = edge.getSourceStId();
                target = edge.getTargetStId();
                if (!reactionsHashMap.containsKey(source)){
                    vertex = edges.insertVertex(new GReaction(source, ""));
                    reactionsHashMap.put(source,vertex);
                }
                if (!reactionsHashMap.containsKey(target)){
                    vertex = edges.insertVertex(new GReaction(target, ""));
                    reactionsHashMap.put(target,vertex);
                }
                gEdge = new GEdge(source, target, edge.getFlux(), edge.getMoleculeStId());
                graphEdges.put(gEdge.toString(),gEdge);
                edges.insertEdge(reactionsHashMap.get(edge.getSourceStId()),
                        reactionsHashMap.get(edge.getTargetStId()),
                        gEdge);
            }

            String customProps = "edge.label = true" + "\n" + "edge.arrow = true";
            SmartGraphProperties properties = new SmartGraphProperties(customProps);
            SmartGraphPanel<GReaction, GEdge> graphView = new SmartGraphPanel<>(edges,
                    properties,
                    new SmartCircularSortedPlacementStrategy());

            int k= 1;
            int edgeWeight;
            for (String s : graphEdges.keySet()) {
                edgeWeight = graphEdges.get(s).getWeight();
                graphView.getStylableEdge(graphEdges.get(s)).setStyle("-fx-stroke-width: "
                        + mapFluxesToWidths(edgeWeight) +";" +
                        ( edgeWeight>0 ? "-fx-stroke: #9BD087;" : "-fx-stroke: #FF6D66;"));
                // #58ABAE;"
                k++;
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            graphView.setVertexDoubleClickAction(graphVertex -> {
                hostServices.showDocument("https://reactome.org/content/detail/" +
                        graphVertex.getUnderlyingVertex().element().getStId()
                );
            });

            graphView.setEdgeDoubleClickAction(graphEdge -> {
                hostServices.showDocument("https://reactome.org/content/detail/" +
                        graphEdge.getUnderlyingEdge().element().getStId());
            });

            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            Stage fluxStage = new Stage(StageStyle.DECORATED);
            fluxStage.setTitle("Flux-Difference Graph");
            fluxStage.setMinHeight(500);
            fluxStage.setMinWidth(800);
            Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);
            fluxStage.setScene(scene);
            fluxStage.show();
            graphView.init();
        }

        private Integer mapFluxesToWidths(Integer weight){
            int absWeight = Math.abs(weight);
            if (absWeight <4){
                return 1;
            } else if (absWeight <10){
                return 2;
            } else if (absWeight <40){
                return 3;
            } else if (absWeight <100){
                return 4;
            } else if (absWeight <400){
                return 5;
            } else if (absWeight <1000){
                return 6;
            } else if (absWeight <4000){
                return 7;
            } else {
                return 8;
            }
        }

        public String buildGraphFile(HashSet<String> deselectedMolecules,int cuTOffValue,boolean includeInitSelected){
            ArrayList<GraphEdge> graph = this.getFilteredGraph(deselectedMolecules,cuTOffValue,includeInitSelected);
            Collections.sort(graph);
            String result = "Source\tTarget\tFluxMolecule\tFluxWeight\n";
            for (GraphEdge edge :  graph){
                result += edge.getSourceStId() + "\t" + edge.getTargetStId() + "\t";
                result += edge.getMoleculeStId() + "\t" + edge.getFlux() + "\n";
            }
            return result;
        }

    }

//        Vertex<GReaction> prague = weights.insertVertex(new GReaction("Prague", ""));
//        weights.insertEdge(tokyo, newYork, new Weight(10838));
//        graphView.setVertexPosition(beijing, 100, 100);
//        graphView.getStylableLabel(tokyo).setStyle("-fx-stroke: red; -fx-fill: red;");
