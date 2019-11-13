package login;

import database.ConnectDB;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class ForgetPwdController {
    private int count = 1;
    @FXML
    private PasswordField pwd1, pwd2;
    @FXML
    private Pane step_one, step_two;
    @FXML
    private ImageView nextstep, min, close, step, final_step;
    @FXML
    private TextField acct, name, tel, email, faculty;
    @FXML
    private Text warn;
    @FXML
    public void initialize(){
        step_two.setVisible(false);
        final_step.setVisible(false);               //设置步骤2.3界面的不可见性
        nextButtonEvent();
        textFieldEvent();
        onCloseEvent();
        onMinEvent();

        defaultFocus();
    }

    //下一步按钮监听
    private void nextButtonEvent(){
        nextstep.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (count){
                    case 1:
                        pwdStepOne();
                        break;
                    case 2:
                        pwdStepTwo();
                        break;
                    case 3:
                        pwdStepThree();
                        break;
                    default:
                        break;
                }
            }
        });
        nextstep.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(count == 1 || count == 2)
                    nextstep.setImage(new Image("/image/reg/nextstep_click.png"));
                else
                    nextstep.setImage(new Image("/image/reg/finish_click.png"));
            }
        });
        //松开鼠标响应
        nextstep.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(count == 1 || count == 2)
                    nextstep.setImage(new Image("/image/reg/nextstep.png"));
                else if(count == 3)
                    nextstep.setImage(new Image("/image/reg/finish.png"));
            }
        });
        //进入按钮响应
        nextstep.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(count == 1 || count == 2)
                    nextstep.setImage(new Image("/image/reg/nextstep_on.png"));
                else if(count == 3)
                    nextstep.setImage(new Image("/image/reg/finish_on.png"));
            }
        });
        nextstep.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(count == 1 || count == 2)
                    nextstep.setImage(new Image("/image/reg/nextstep.png"));
                else if(count == 3)
                    nextstep.setImage(new Image("/image/reg/finish.png"));
            }
        });
    }
    //textfield内容监听
    private void textFieldEvent(){
        acct.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!acct.getText().toString().equals("")) acct.setStyle("-fx-border-color: #039ed3");
                else acct.setStyle("-fx-border-color: #FF0000");
            }
        });
        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!name.getText().toString().equals("")) name.setStyle("-fx-border-color: #039ed3");
                else name.setStyle("-fx-border-color: #FF0000");
            }
        });
        pwd1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!pwd1.getText().toString().equals("")) pwd1.setStyle("-fx-border-color: #039ed3");
                else pwd1.setStyle("-fx-border-color: #FF0000");
            }
        });
        pwd2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!pwd2.getText().toString().equals("")) pwd2.setStyle("-fx-border-color: #039ed3");
                else pwd2.setStyle("-fx-border-color: #FF0000");
            }
        });
        faculty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!faculty.getText().toString().equals("")) faculty.setStyle("-fx-border-color: #039ed3");
                else faculty.setStyle("-fx-border-color: #FF0000");
            }
        });
        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!email.getText().toString().equals("")) email.setStyle("-fx-border-color: #039ed3");
                else email.setStyle("-fx-border-color: #FF0000");
            }
        });
        tel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!tel.getText().toString().equals("")) tel.setStyle("-fx-border-color: #039ed3");
                else tel.setStyle("-fx-border-color: #FF0000");
            }
        });
    }
    //关闭按钮的相关事件
    private void onCloseEvent(){
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) close.getScene().getWindow();
                stage.close();
            }
        });
        close.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setImage(new Image("/image/close.png"));
            }
        });

        close.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setImage(new Image("/image/close_on.png"));
            }
        });

        close.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setImage(new Image("/image/close.png"));
            }
        });

        close.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close.setImage(new Image("/image/close_click.png"));
            }
        });
    }
    //最小化按钮的相关事件
    private void onMinEvent(){
        min.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                min.setImage(new Image("/image/min_click.png"));
            }
        });
        min.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                min.setImage(new Image("/image/min.png"));
            }
        });
        min.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                min.setImage(new Image("/image/min_on.png"));
            }
        });
        min.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                min.setImage(new Image("/image/min.png"));
            }
        });
        min.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) close.getScene().getWindow();
                stage.setIconified(true);

            }
        });

    }

    //第一步
    private void pwdStepOne(){
        String acct = this.acct.getText().toString();
        String name = this.name.getText().toString();
        String tel = this.tel.getText().toString();
        String email = this.email.getText().toString();
        String faculty = this.faculty.getText().toString();

        if(!checkStep1Input()) return;

        ConnectDB.connect();
        ResultSet rSet = ConnectDB.search("select acct_pwd from " +
                "acct_info_table where acct_id = '"+acct+"', acct_name = '"+name+"'," +
                " acct_tel = '"+tel+"', acct_email = '"+email+"', acct_faculty = '"+faculty+"'");
        try{
            if(rSet.next()) {
                count++;
                step_one.setVisible(false);
                step_two.setVisible(true);
                step.setImage(new Image("/image/find_pwd/s2.png"));
                warn.setText("");

                defaultFocus();                     //恢复默认聚焦点
            }
            else {
                warn.setText("未找到相关账号信息！");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    //第二步
    private void pwdStepTwo(){

        if(!checkPwdInput()) return;

        if(pwd2.getText().equals(pwd1.getText().toString())){
            count++;
            step_two.setVisible(false);
            final_step.setVisible(true);
            step.setImage(new Image("/image/find_pwd/s3.png"));
            nextstep.setImage(new Image("/image/reg/finish.png"));
        }
    }

    //第三步
    private void pwdStepThree(){
        String pwd = pwd1.getText().toString();
        ConnectDB.connect();
        ConnectDB.update("update acct_info_table set acct_pwd = '" + pwd +
                "' where acct_id = '"+ acct.getText().toString() +"'");

        Stage stage = (Stage) step_one.getScene().getWindow();
        stage.close();

        //重新启动登录界面
        Login_UI lg = new Login_UI();
        try {
            lg.start(new Stage());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //设置默认聚焦点
    private void defaultFocus(){
        Platform.runLater(new Runnable() {          //设置初始焦点在nextstep图片按钮上
            @Override
            public void run() {
                nextstep.requestFocus();
            }
        });
    }
    private void defaultFocus(TextField tf){
        Platform.runLater(new Runnable() {          //设置初始焦点在Textfield上
            @Override
            public void run() {
                tf.requestFocus();
            }
        });
    }

    private boolean checkStep1Input(){
        String warn = "";
        if(acct.getText().equals("")) {
            acct.setStyle("-fx-border-color: #FF0000;");
            warn += "账号不能为空\n";
        }
        if(email.getText().equals("")) {
            email.setStyle("-fx-border-color: #FF0000;");
            warn += "邮箱不能为空\n";
        }
        if(tel.getText().equals("")) {
            tel.setStyle("-fx-border-color: #FF0000;");
            warn += "电话不能为空\n";
        }
        if(tel.getText().length() != 11){
            tel.setStyle("-fx-border-color: #FF0000;");
            warn += "电话长度应为11位\n";
        }
        if(name.getText().equals("")) {
            name.setStyle("-fx-border-color: #FF0000;");
            warn += "姓名不能为空\n";
        }
        if(faculty.getText().equals("")) {
            faculty.setStyle("-fx-border-color: #FF0000;");
            warn += "学院不能为空\n";
        }

        System.out.println(warn);
        if(warn.equals("")) {
            return true;
        }
        else{
            this.warn.setText(warn);
            return false;
        }
    }

    private boolean checkPwdInput(){
        String PW_PATTERN = "^(?![a-z0-9]+$)(?![A-Za-z]+$)(?![A-Z0-9]+$)[a-zA-Z0-9\\W]{8,}$";
        if(pwd1.getText().matches(PW_PATTERN))
            return true;
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.headerTextProperty().set("密码不符合规范");
            error.setContentText("密码必须包含大小写字母、数字的8位以上三种组合");
            error.show();
            return false;
        }
    }

}
