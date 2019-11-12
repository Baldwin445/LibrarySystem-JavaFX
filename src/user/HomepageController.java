package user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import properties.Property;

import java.sql.SQLException;


public class HomepageController {

    @FXML
    private Label username;
    @FXML
    private void initialize() throws SQLException {
        UserData user = new UserData(Property.getKeyValue("ID"));
        String name = user.getName();
        Property.updateProperties("name", name);
        username.setText(name);
    }
}
