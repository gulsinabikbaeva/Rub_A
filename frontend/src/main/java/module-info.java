module com.mycompany.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.mycompany.backend;

    opens com.mycompany.frontend to javafx.fxml;
    exports com.mycompany.frontend;
}
