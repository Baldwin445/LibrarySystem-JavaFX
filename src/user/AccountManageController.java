package user;

import database.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import properties.Property;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;


public class AccountManageController {
    @FXML
    private PasswordField oldPassword, newPassword, againPassword, lossPassword, removeLossPassword;
    @FXML
    private Button changePassword, reportLoss, removeLoss;
    private String userID = Property.getKeyValue("ID");
    private UserData user = new UserData(userID);

    private static Statement stmt = ConnectDB.connect();
    private String queryString;

    public AccountManageController() throws SQLException {
    }

    // 更改密码按钮事件
    @FXML
    public void onChangePassword(ActionEvent event) throws SQLException {
        // 如果密码不匹配，退出程序
        if (!checkPassword(oldPassword.getText())) return;

        // 如果两次输入的密码不同，退出程序
        if (!checkOldNewPassword(newPassword.getText(), againPassword.getText()))  return;

        // 如果输入的密码不符合规范，退出程序
        if (!checkStandards(newPassword.getText())) return;
        // 如果新旧密码相同，退出程序
        if(checkNewOldPWD(oldPassword.getText(), newPassword.getText())) return;
        // 询问是否确定修改
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定修改？");
        confirmation.setHeaderText("修改");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }

        // 密码没有问题，写入数据库
        user.setPassword(newPassword.getText());
        user.updateUser();

        // 检查是否写入成功
        if (user.getPassword().equals(newPassword.getText())){
            showAlert(Alert.AlertType.INFORMATION, "提示", "修改密码成功");
        }
        else {
            showAlert(Alert.AlertType.ERROR, "提示", "修改密码失败");
        }
        oldPassword.clear();
        newPassword.clear();
        againPassword.clear();
    }

    // 挂失借阅证按钮事件
    @FXML
    public void onReportLoss(ActionEvent event) throws SQLException {
        if(!checkPassword(lossPassword.getText())) return;
        // 确认是否要挂失
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定挂失？");
        confirmation.setHeaderText("挂失");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL) return;

        String state = user.getState();
        if (state.contains("L")){
            // 如果已经是挂失，那么直接弹出对话框，退出
            showAlert(Alert.AlertType.INFORMATION, "提示", "当前已经是挂失状态");
            lossPassword.clear();
            return;
        }
        else if (state.equals("N")){
            // 如果是正常状态，那么直接修改
            user.setState("L");
            user.updateUser();
        }
        else {
            // 如果是其它状态，那么在后面加上"L";
            state += "L";
            user.setState(state);
            user.updateUser();
        }
        showAlert(Alert.AlertType.INFORMATION, "提示", "挂失成功，注意你现在已经有一些功能不可以使用！");
        lossPassword.clear();
    }

    // 解除挂失按钮事件
    @FXML
    public void onRemoveLoss(ActionEvent event) throws SQLException {
        if(!checkPassword(removeLossPassword.getText())) return;

        // 确认是否要解除挂失
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定解除挂失？");
        confirmation.setHeaderText("解除挂失");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL) return;

        String state = user.getState();
        if (state.contains("N")){
            // 如果已经是正常状态，那么直接弹出对话框，退出
            showAlert(Alert.AlertType.INFORMATION, "提示", "当前已经是正常状态");
            removeLossPassword.clear();
            return;
        }
        else if (state.equals("L")){
            // 如果只有一个挂失状态，修改为"N"
            state = "N";
            user.setState(state);
            user.updateUser();
        }
        else {
            // 如果是其它状态，那么去掉"L";
            state.replace("L", "");
            user.setState(state);
            user.updateUser();
        }
        showAlert(Alert.AlertType.INFORMATION, "提示", "解除挂失成功，你的功能已经全部恢复啦！");
        removeLossPassword.clear();
    }

    // 检查密码
    public boolean checkPassword(String password) throws SQLException {
        if (user.getPassword().equals(password))
            return true;
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.headerTextProperty().set("密码错误");
        error.setContentText("请重新输入密码！");
        error.show();
        return false;
    }

    // 检测两次输入的密码是否相同
    public boolean checkOldNewPassword(String firstPassword, String secondPassword){
        if (firstPassword.equals(secondPassword))
            return true;
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.headerTextProperty().set("输入错误");
        error.setContentText("请重新核对两次输入的密码！");
        error.show();
        return false;
    }

    // 检密码是否符合规范
    public boolean checkStandards(String password){
        String PW_PATTERN = "^(?![a-z0-9]+$)(?![A-Za-z]+$)(?![A-Z0-9]+$)[a-zA-Z0-9\\W]{8,}$";
        if (password.matches(PW_PATTERN))
            return true;
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.headerTextProperty().set("密码不符合规范");
        error.setContentText("密码必须包含大小写字母、数字的8位以上三种组合");
        error.show();
        return false;
    }

    // 显示对话框
    public static void showAlert(Alert.AlertType type, String headerText, String content){
        Alert show = new Alert(type);
        show.setHeaderText(headerText);
        show.setContentText(content);
        show.show();
    }

    // 检查新旧密码是否相同
    public boolean checkNewOldPWD(String oldPWD, String newPWD){
        if (oldPWD.equals(newPWD)) {
            showAlert(Alert.AlertType.ERROR, "错误", "修改的密码不能与之前的密码一样！");
            return true;
        }
        return false;
    }
}
