package user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import login.Login_UI;
import operate.UIOperate;

import java.util.Optional;

public class ExitController {
    @FXML
    private Button logout, exitSystem;


    @FXML
    public void onLogoutClick(ActionEvent event) throws Exception {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定退出登陆？");
        confirmation.setHeaderText("退出");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        Login_UI login = new Login_UI();
        login.start(new Stage());
    }

    @FXML
    public void onExitClick(ActionEvent event){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定退出系统？");
        confirmation.setHeaderText("退出");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        UIOperate.showAlert(Alert.AlertType.INFORMATION, "再见", "感谢你的使用! []~(￣▽￣)~*");
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }
}
