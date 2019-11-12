package user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import operate.UIOperate;
import properties.Property;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class InformationController {
    private static Statement stmt;
    String userID = Property.getKeyValue("ID");
    private UserData user = new UserData(userID);

    @FXML
    private TextField name, ID, sex, faculty, major, tel, mail, cancelDate, registerDate, state, age;

    public InformationController() throws SQLException {
    }

    @FXML
    private void initialize() {
        // 设置控件的数据
        name.setText(user.getName());
        ID.setText(userID);
        age.setText(String.valueOf(user.getAge()));
        if (user.getSex().equals("M")) sex.setText("男");
        else sex.setText("女");
        faculty.setText(user.getFaculty());
        major.setText(user.getMajor());
        tel.setText(user.getTel());
        mail.setText(user.getEmail());
        String stateText = "";
        if (user.getState().equals("N")) stateText = "正常";
        else {
            if (user.getState().contains("L")) stateText += "挂失 ";
            if (user.getState().contains("D")) stateText += "欠费 ";
            if (user.getState().contains("F")) stateText += "冻结 ";
        }
        state.setText(stateText);
        registerDate.setText(user.getRegDate());
        cancelDate.setText(user.getCancelDate());
    }

    @FXML
    private void onModifyClick(ActionEvent event){
        setEditAndCss(true, true);
    }

    @FXML
    private void onSaveClick(ActionEvent event){
        if (!checkInput()) return;
        // 确定是否修改
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定修改？");
        confirmation.setHeaderText("修改");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.get() == ButtonType.CANCEL) return;
        user.setName(name.getText());
        if (sex.getText().equals("男")) user.setSex("M");
        else user.setSex("F");
        user.setEmail(mail.getText());
        user.setTel(tel.getText());
        user.setAge(Integer.parseInt(age.getText()));
        user.updateUser();
        setEditAndCss(false, false);
        UIOperate.showAlert(Alert.AlertType.INFORMATION, "提示", "修改成功！（￣︶￣）↗　");
        initialize();
    }


    // 检查输入
    public boolean checkInput(){
        String problemText = "";
        if (name.getText().length() == 0) problemText += "姓名区不能为空！\n";
        if (name.getText().length() > 20) problemText += "姓名过长\n";
        if (age.getText().length() == 0) problemText += "年龄不能为空\n";
        else if (!RecommendController.isNumber(age.getText())) problemText += "年龄必须为数字\n";
        else if (Integer.parseInt(age.getText()) > 100 || Integer.parseInt(age.getText()) < 0) problemText += "年龄必须为0~100\n";
        if (!sex.getText().equals("男")&&!sex.getText().equals("女")) problemText += "性别只能为男或者女\n";
        if (tel.getText().length() != 11) problemText += "电话只能为11位数字\n";
        if (mail.getText().length() == 0) problemText += "邮箱不能为空\n";
        if (problemText.equals("")){
            return true;
        }
        UIOperate.showAlert(Alert.AlertType.ERROR, "输入有错", problemText);
        return false;
    }

    // 设置控件的可编辑状态与样式
    public void setEditAndCss(Boolean bool, Boolean fieldVisible){
        // 把不可编辑的控件改为可编辑状态
        name.setEditable(bool);
        sex.setEditable(bool);
        age.setEditable(bool);
        mail.setEditable(bool);
        tel.setEditable(bool);
        if (!fieldVisible){
            // 把输入框隐藏起来
            age.getStyleClass().add("text-area");
            name.getStyleClass().add("text-area");
            sex.getStyleClass().add("text-area");
            mail.getStyleClass().add("text-area");
            tel.getStyleClass().add("text-area");
        }
        else {
            // 把输入框显示出来
            age.getStyleClass().remove("text-area");
            name.getStyleClass().remove("text-area");
            sex.getStyleClass().remove("text-area");
            mail.getStyleClass().remove("text-area");
            tel.getStyleClass().remove("text-area");
        }


    }
}
