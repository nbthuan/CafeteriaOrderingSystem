module CafeteriaOrderingSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens edu.sundevil.cafeteria to javafx.fxml;
    exports edu.sundevil.cafeteria;
}
