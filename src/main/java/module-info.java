module com.example.template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens com.example.template to javafx.fxml;
    exports com.example.template;
}