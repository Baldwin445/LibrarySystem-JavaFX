package login;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
//    private Stage stage;                            //保存调用的Stage
//    private AnchorPane root;
    LoginData usr;

    @FXML
    private ImageView close;
    @FXML
    private ImageView min;
    @FXML
    private ImageView login;
    @FXML
    private PasswordField pwd;
    @FXML
    private TextField acct;
    @FXML
    private Text loginText;
    @FXML
    private Text tips;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onButtonEvent();

    }

    //获取调用的Stage
//    private Stage getStage() {
//        if (stage == null) {
//            stage = (Stage) root.getScene().getWindow();
//        }
//        return stage;
//    }


    //按钮监听实现
    private void onButtonEvent(){
        onCloseEvent();
        onMinEvent();
        onLoginEvent();
        onLoginTextEvent();
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
    //登录按钮的相关事件
    //登录验证函数
    private void onLoginEvent(){
        login.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login_on.png"));
            }
        });
        login.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login.png"));
            }
        });
        login.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login_click.png"));
            }
        });
        login.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login.png"));
            }
        });
        //登录认证
        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println(acct.getText().toString() + pwd.getText().toString());
                String str1, str2, queryString;
                str1 = acct.getText().toString();
                str2 = pwd.getText().toString();
                usr = new LoginData(str1, str2);

                //将账号类型判断放在监听类中，若放在登录类LoginData中会导致无响应
                if(str1.length()>= 5 && str1.substring(0,5).equals("admin"))
                    queryString = "select admin_id, admin_pwd, admin_limits from admin_info_table";
                else
                    queryString ="select acct_id, acct_pwd, role from acct_info_table";
                switch(usr.loginAccess(queryString)){
                    case 0:
                        tips.setText("请输入账号密码");
                        break;
                    case -1:
                        tips.setText("账号不存在");
                        break;
                    case -2:
                        tips.setText("密码错误");
                        break;
                    default:
                        tips.setText("登录成功");
                        break;

                }
            }
        });
    }
    //登录Text相关事件
    private void onLoginTextEvent(){
        loginText.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login_on.png"));
            }
        });

        loginText.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login.png"));
            }
        });

        loginText.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login_click.png"));
            }
        });
        loginText.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login.setImage(new Image("/image/login_on.png"));
            }
        });
        loginText.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                login.setImage(new Image("/image/login_on.png"));
            }
        });
    }
}
