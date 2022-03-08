module com.example.daytoursearchengine_team2d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.daytoursearchengine_team2d to javafx.fxml;
    exports com.example.daytoursearchengine_team2d;
}