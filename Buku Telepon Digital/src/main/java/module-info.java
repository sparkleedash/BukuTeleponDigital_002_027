module com.example.bukutelepondigital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.bukutelepondigital to javafx.fxml;
    exports com.example.bukutelepondigital;
}
