package operate;

import javafx.scene.control.Alert;

public class UIOperate {
    static public void showAlert(Alert.AlertType type, String headerText, String content){
        Alert show = new Alert(type);
        show.setHeaderText(headerText);
        show.setContentText(content);
        show.show();
    }
}
