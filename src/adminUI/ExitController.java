package adminUI;

import database.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.Login_UI;
import operate.UIOperate;
import properties.Property;

import java.sql.ResultSet;
import java.util.Optional;

public class ExitController {
    @FXML
    private Button logout, exitSystem;
    @FXML
    private TextField name, ID, state, age, sex, limit, tel, mail;
    @FXML
    private void initialize(){
        init();
    }

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

    private void init(){
        ResultSet rSet = ConnectDB.search("SELECT admin_name,admin_id,admin_state,admin_age,admin_sex,admin_limits," +
                        "admin_tel,admin_email FROM admin_info_table WHERE admin_id = '"+Property.getKeyValue("ID")+"'");
        try{
            if(rSet.next()){
                name.setText(rSet.getString(1));
                ID.setText(rSet.getString(2));
                if(rSet.getString(3).equals("N"))
                    state.setText("正常");
                if(rSet.getString(3).equals("F"))
                    state.setText("冻结");
                age.setText(rSet.getString(4));
                if(rSet.getString(5).equals("M"))
                    sex.setText("男");
                else sex.setText("女");
                if(rSet.getString(6).equals("4"))
                    limit.setText("超级管理员");
                else limit.setText("管理员");
                tel.setText(rSet.getString(7));
                mail.setText(rSet.getString(8));
            }
            else name.setText("没有查询到相关用户信息");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
