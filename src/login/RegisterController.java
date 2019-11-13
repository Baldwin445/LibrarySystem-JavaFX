package login;

import database.ConnectDB;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import properties.RegProperty;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RegisterController {
    private int count = 1;
    private double x1, y1, x_stage, y_stage;
    @FXML
    private Pane step_one, step_two;
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView final_step, step, min, close;
    @FXML
    private ImageView pwd_tip, pwd_tip_context, major_tip, major_tip_context, nextstep;
    @FXML
    private TextField acct, name;
    @FXML
    public TextField tel, email, age, faculty, major;
    @FXML
    public ChoiceBox role, sex;
    @FXML
    private PasswordField pwd1, pwd2;
    @FXML
    public void initialize(){
        defaultFocus();                             //设置初始焦点在nextstep图片按钮上

        step_two.setVisible(false);
        final_step.setVisible(false);               //设置步骤2.3界面的不可见性

        nextButtonEvent();
        textFieldContextFocus();
        choiceBoxContextFocus();

        onCloseEvent();                             //关闭按钮方法
        onMinEvent();                               //最小化按钮方法
//        screenMove();                               //窗体移动方法

        tipsShowEvent();                            //输入框tips提醒显示
    }


    //下一步按钮响应事件
    private void nextButtonEvent(){
        nextstep.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (count){
                    case 1:
                        regStepOne();
                        break;
                    case 2:
                        regStepTwo();
                        break;
                    case 3:
                        regStepThree();
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

    //textfiedl控件内容监听设置样式
    //默认初始状态 内容发生变化后才进行监听 内容为空时边框为红色 存在内容时恢复为蓝色
    private void textFieldContextFocus(){
        //pane1
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
        //pane2
        age.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!age.getText().toString().equals("")) age.setStyle("-fx-border-color: #039ed3");
                else age.setStyle("-fx-border-color: #FF0000");
            }
        });
        faculty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!faculty.getText().toString().equals("")) faculty.setStyle("-fx-border-color: #039ed3");
                else faculty.setStyle("-fx-border-color: #FF0000");
            }
        });
        major.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(major.getText().toString().equals("") && !role.getValue().equals("教师"))
                    major.setStyle("-fx-border-color: #FF0000");
                else major.setStyle("-fx-border-color: #039ed3");
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
    //选择框内容选择监听
    private void choiceBoxContextFocus(){
        role.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(!role.getValue().equals("请选择身份"))
                    role.setStyle("-fx-border-color: #039ed3");
                else
                    role.setStyle("-fx-border-color: #FF0000");
                if(role.getValue().equals("教师")) {
                    major.setEditable(false);
                    major.setStyle("-fx-border-color: #039ed3");
                    major.setPromptText("无需输入");
                    major.setText("");
                    System.out.println("选中教师");
                    major.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus();
                        }
                    });
                    major.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus();
                        }
                    });
                }
                else{
                    major.setEditable(true);
                    major.setPromptText("输入专业");
                    major.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                        }
                    });
                    major.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus(major);
                        }
                    });
                    if(role.getValue().equals("学生")) System.out.println("选中学生");
                }
            }
        });
        sex.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(!sex.getValue().equals("请选择性别"))
                    sex.setStyle("-fx-border-color: #039ed3");
                else
                    sex.setStyle("-fx-border-color: #FF0000");
            }
        });
    }
    //tips内容显示监听
    private void tipsShowEvent(){
        pwd_tip_context.setVisible(false);
        major_tip_context.setVisible(false);
        //密码提示显示监听
        pwd_tip.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pwd_tip_context.setVisible(true);
                        pwd_tip.setCursor(Cursor.HAND);
                    }
                });

            }
        });
        pwd_tip.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pwd_tip_context.setVisible(false);
                    }
                });

            }
        });
        //专业提示显示监听
        major_tip.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        major_tip_context.setVisible(true);
                        major_tip_context.setCursor(Cursor.HAND);
                    }
                });

            }
        });
        major_tip.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        major_tip_context.setVisible(false);
                    }
                });


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

    //注册第一步切换第二步
    private void regStepOne(){
        String acct, name, pwd1, pwd2;
        acct = this.acct.getText().toString();
        name = this.name.getText().toString();
        pwd1 = this.pwd1.getText().toString();
        pwd2 = this.pwd2.getText().toString();

        if(checkStep1Input()){
            count++;
            step_one.setVisible(false);
            step_two.setVisible(true);
            step.setImage(new Image("/image/reg/step2.png"));

            defaultFocus();                     //恢复默认聚焦点
        }

    }

    //注册第二步切换第三步
    private void regStepTwo(){
        String role, sex, age, faculty, major, tel, email;
        age = this.age.getText().toString();
        faculty = this.faculty.getText().toString();
        major = this.major.getText().toString();
        tel = this.tel.getText().toString();
        email = this.email.getText().toString();
        role = this.role.getValue().toString();
        sex = this.sex.getValue().toString();
        //检查内容是否为空
        if(age.equals("")) this.age.setStyle("-fx-border-color: #FF0000;");
        if(faculty.equals("")) this.faculty.setStyle("-fx-border-color: #FF0000;");
        if(!this.role.getValue().equals("教师") && major.equals(""))
            this.major.setStyle("-fx-border-color: #FF0000;");
        if(tel.equals("")) this.tel.setStyle("-fx-border-color: #FF0000;");
        if(email.equals("")) this.email.setStyle("-fx-border-color: #FF0000;");
        if(role.equals("请选择身份"))
            this.role.setStyle("-fx-border-color: #FF0000");
        if(sex.equals("请选择性别"))
            this.sex.setStyle("-fx-border-color: #FF0000");

        if(!role.equals("请选择身份") && !sex.equals("请选择性别")) {
            if (role.equals("学生") && !major.equals("") && !age.equals("") && !faculty.equals("") && !tel.equals("") && !email.equals("")) {
                subStep2();
                RegProperty.writeProperties("role", role);
                RegProperty.writeProperties("age", age);
                RegProperty.writeProperties("faculty", faculty);
                RegProperty.writeProperties("major", major);
                RegProperty.writeProperties("tel", tel);
                RegProperty.writeProperties("email", email);
                RegProperty.writeProperties("sex", sex);

                min.setVisible(false);
                close.setVisible(false);
            }
            if (role.equals("教师") && !age.equals("") && !faculty.equals("") && !tel.equals("") && !email.equals("")) {
                subStep2();
                RegProperty.writeProperties("role", role);
                RegProperty.writeProperties("age", age);
                RegProperty.writeProperties("faculty", faculty);
                RegProperty.writeProperties("major", major);
                RegProperty.writeProperties("tel", tel);
                RegProperty.writeProperties("email", email);
                RegProperty.writeProperties("sex", sex);

                min.setVisible(false);
                close.setVisible(false);
            }
        }

    }

    //注册第三步完成
    private void regStepThree(){
        ConnectDB.connect();

        String name = RegProperty.getKeyValue("name");
        String tel = RegProperty.getKeyValue("tel");
        String role;
        if(RegProperty.getKeyValue("role").equals("学生")) role = "2";
        else role = "1";
        String pwd = RegProperty.getKeyValue("pwd");
        String sex;
        if(RegProperty.getKeyValue("sex").equals("男")) sex = "M";
        else sex = "女";
        String major = RegProperty.getKeyValue("major");
        String email = RegProperty.getKeyValue("email");
        String acct = RegProperty.getKeyValue("acct");
        String faculty = RegProperty.getKeyValue("faculty");
        String age = RegProperty.getKeyValue("age");

        String sql = "insert into acct_info_table values('"+ name + "','"+ acct +
                "','"+ pwd +"','N',0,now(),now(),'"+ role +"','"+ sex +"','"+ age +
                "','"+ faculty+"','"+ major +"','"+ tel +"','"+ email +"')";
        ConnectDB.update(sql);
        ConnectDB.update("update acct_info_table set cancel_date = " +
                "DATE_ADD(cancel_date, INTERVAL 4 YEAR)  Where acct_id = '"+ acct +"'");

        Stage stage = (Stage) step_one.getScene().getWindow();
        stage.close();
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
        Platform.runLater(new Runnable() {          //设置初始焦点在nextstep图片按钮上
            @Override
            public void run() {
                tf.requestFocus();
            }
        });
    }

    //Step2 子步骤
    private void subStep2(){
        count++;
        step_two.setVisible(false);
        final_step.setVisible(true);
        step.setImage(new Image("/image/reg/step3.png"));
        nextstep.setImage(new Image("/image/reg/finish.png"));
    }

    //窗口可拖动响应
    private void screenMove(){
        Stage s = (Stage) pane.getScene().getWindow();
        Scene sc = (Scene) pane.getScene();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        s.setX(x_stage + event.getScreenX() - x1);
                        s.setY(y_stage + event.getScreenY() - y1);
                    }
                });
                sc.setOnDragEntered(null);
                sc.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //按下鼠标后，记录当前鼠标的坐标
                        x1 =event.getScreenX();
                        y1 =event.getScreenY();
                        x_stage = s.getX();
                        y_stage = s.getY();
                    }
                });
            }
        });

    }

    private boolean checkStep1Input(){
        //检测是否为空
        if(acct.getText().equals("")) acct.setStyle("-fx-border-color: #FF0000;");
        if(name.getText().equals("")) name.setStyle("-fx-border-color: #FF0000;");
        if(pwd1.getText().equals("")) pwd1.setStyle("-fx-border-color: #FF0000;");
        if(pwd2.getText().equals("")) pwd2.setStyle("-fx-border-color: #FF0000;");

        //检测是否正确重复输入密码
        if(!pwd2.getText().equals("") && pwd1.getText().equals(pwd2.getText())){
            RegProperty.writeProperties("acct", acct.getText());
            RegProperty.writeProperties("name", name.getText());
            RegProperty.writeProperties("pwd", pwd2.getText());
        }
        else{
            pwd2.setStyle("-fx-border-color: #FF0000;");
            return false;
        }

        //检测是否存在同账号
        try {
            ResultSet rSet = ConnectDB.search("select acct_id from acct_info_table " +
                    "where acct_id = '" + acct.getText() +"'");
            if(rSet.next()) {
                System.out.println(rSet.getString(1));
                acct.setStyle("-fx-border-color: #FF0000;");
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
