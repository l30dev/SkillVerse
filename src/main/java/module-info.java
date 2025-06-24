module com.example.skillverse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.skillverse to javafx.fxml;
    exports com.example.skillverse;
}