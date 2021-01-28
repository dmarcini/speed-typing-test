module app {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens com.dmarcini.app.controllers to javafx.fxml;
    opens com.dmarcini.app to javafx.graphics;
}
