package com.ozank.cpathway;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class CPathway extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CPathway.class.getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        Controller fXMLDocumentController = fxmlLoader.getController();
        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("CPathway");
        stage.setScene(scene);
        stage.show();
        fXMLDocumentController.setHostServices(getHostServices());
    }

    public static void main(String[] args) {
        launch();
    }

}