package user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import properties.Property;


public class HomepageController {

    @FXML
    private Label username;
    @FXML
    private void initialize() {
        String name = Property.getKeyValue("name");
        username.setText(name);
    }
}
