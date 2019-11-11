package user;

import database.ConnectDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import properties.Property;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController{

    // 顶栏名字标签
    @FXML
    private Label username;
    // 顶栏ID标签
    @FXML
    private Label userID;
    // fxml文件
    @FXML
    private AnchorPane home, history, userInformation, accountManage, punishManage, recommend, requestBook, search, exit;
    // Button
    @FXML
    private Button homeButton, historyButton, informationButton, accountButton, punishButton, recommendButton, requestButton, searchButton, exitButton;

    @FXML
    private ImageView close;
    @FXML
    private ImageView min;

    // 存放数据的地方
//    public static final Properties properties = new Properties();
//    public static final String path = "@../data.properties";

    private static Statement stmt;
    @FXML
    private void initialize() throws IOException, SQLException {
        // 加载配置文件
        String ID = Property.getKeyValue("ID");
        // 连接数据库查询name
        stmt = ConnectDB.connect();
        String queryString = "select acct_name from acct_info_table where acct_id=\""+ID+"\"";
        ResultSet rSet = ConnectDB.search(queryString);// 查询数据库，并返回查询结果
        rSet.next();
        String name = rSet.getString("acct_name");
        // 更改配置中的name
        Property.updateProperties("name", name);

        // 设置顶栏的name和ID
        username.setText(name);
        userID.setText(ID);
        onButtonEvent();
    }


    // 点击左侧按钮后的事件
    @FXML
    public void onHomepageClick(ActionEvent event){
        visible("home");
    }
    @FXML
    public void onHistoryClick(ActionEvent event){
        visible("history");
    }

    @FXML
    public void onInformationClick(ActionEvent event){
        visible("userInformation");
    }

    @FXML
    public void onAccountClick(ActionEvent event){
        visible("accountManage");
    }
    @FXML
    public void onPunishClick(ActionEvent event){
        visible("punishManage");
    }

    @FXML
    public void onRecommendClick(ActionEvent event){
        visible("recommend");
    }

    @FXML public void onRequestClick(ActionEvent event){
        visible("requestBook");
    }

    @FXML
    public void onSearchClick(ActionEvent event){
        visible("search");
    }
    @FXML void onExitClick(ActionEvent event){
        visible("exit");
    }

    // 设置点击按钮后各fxml文件的可见
    public void visible(String button){
        if (button.equals("exit")){
            exit.setVisible(true);
        }
        else{
            exit.setVisible(false);
        }
        if(button.equals("home")) {
            home.setVisible(true);
        }
        else{
            home.setVisible(false);
        }
        if(button.equals("history")) {
            history.setVisible(true);
        }
        else{
            history.setVisible(false);
        }
        if(button.equals("userInformation")) {
            userInformation.setVisible(true);
        }
        else{
            userInformation.setVisible(false);
        }
        if(button.equals("accountManage")) {
            accountManage.setVisible(true);
        }
        else{
            accountManage.setVisible(false);
        }

        if(button.equals("recommend")) {
            recommend.setVisible(true);
        }
        else{
            recommend.setVisible(false);
        }
        if(button.equals("requestBook")) {
            requestBook.setVisible(true);
        }
        else{
            requestBook.setVisible(false);
        }
        if(button.equals("search")) {
            search.setVisible(true);
        }
        else{
            search.setVisible(false);
        }
        if(button.equals("punishManage")) {
            punishManage.setVisible(true);
        }
        else{
            punishManage.setVisible(false);
        }
    }

    private void onButtonEvent(){
        onCloseEvent();
        onMinEvent();

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
    private void onMinEvent() {
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
}
