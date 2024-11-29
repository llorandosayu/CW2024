module com.example.retro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.retro to javafx.fxml;
	opens com.example.retro.controller to javafx.fxml;
	opens com.example.retro.level to javafx.fxml;
    opens com.example.retro.plane to javafx.fxml;
    opens com.example.retro.projectile to javafx.fxml;
	exports com.example.retro;
    exports com.example.retro.controller;
	exports com.example.retro.level;
	exports com.example.retro.plane;
	exports com.example.retro.projectile;
    
}