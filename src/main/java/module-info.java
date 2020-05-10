module com.example.severania {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jdk.compiler;


    opens com.example.severania to javafx.fxml;
    exports com.example.severania;
}