module com.ozank.cpathway {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires smartgraph;

    opens com.ozank.cpathway.dataclasses to com.google.gson, javafx.base;
    opens com.ozank.cpathway to javafx.fxml;
    opens com.ozank.cpathway.fluxgraph;
    exports com.ozank.cpathway.fluxgraph to smartgraph;
    exports com.ozank.cpathway;

}