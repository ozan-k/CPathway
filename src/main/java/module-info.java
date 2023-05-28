module com.ozank.cpathway {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.ozank.cpathway.dataclasses to com.google.gson, javafx.base;
    opens com.ozank.cpathway to javafx.fxml;
    exports com.ozank.cpathway;
}