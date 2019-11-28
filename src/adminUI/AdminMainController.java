package adminUI;

import database.ConnectDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import properties.Property;

<<<<<<< HEAD
import javax.xml.soap.Text;
=======
//import javax.xml.soap.Text;
>>>>>>> jinl
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class AdminMainController implements Initializable{

    // fxml文件
    @FXML
    private AnchorPane home, xgmm, zxzh, gsjgjyz, jcwg, xzsj, scsj, xgsjxx,tszthfjy,xzgrts,glsjxx,zczh,glzh,sjjhls,zhglls,ycxxcx,brHistory;
    // Button
    @FXML
    private Button homeButton, xgmmButton, zxzhButton, gsjgjyzButton, jcwgButton, xzsjButton, scsjButton, xgsjxxButton,tszthfjyButton,tc,xz,xzgrtsButton,glzhButton,glsjxxButton,zczhButton,sjjhlsButton,zhgllsButton,ycxxcxButton,tcButton;

    @FXML
    private ImageView close;
    @FXML
    private ImageView min;
    @FXML
    private Label username, userid;

    public AdminMainController() {
    }

    @FXML
    public void onhomeClick(ActionEvent event){
        visible("homeButton");
    }

    @FXML
    public void onxzgrtsClick(ActionEvent event){
        visible("xzgrtsButton");
    }

    @FXML
    public void onglsjxxClick(ActionEvent event){
        visible("glsjxxButton");
    }

    @FXML
    public void onzczhClick(ActionEvent event){
        visible("zczhButton");
    }

    @FXML
    public void onglzhClick(ActionEvent event){
        visible("glzhButton");
    }

    @FXML
    public void onsjjhlsClick(ActionEvent event){
        visible("sjjhlsButton");
    }

    @FXML
    public void onzhgllsClick(ActionEvent event){
        visible("zhgllsButton");
    }

    @FXML
    public void onycxxcxClick(ActionEvent event){
        visible("ycxxcxButton");
    }

    @FXML
    public void onbookBRHistoryClick(ActionEvent event ){
        visible("onbookBRHistoryClick");
    }

    @FXML
    public void ontcClick(ActionEvent event){
        visible("tcButton");
    }

    private Statement stmt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initUI();

        onCloseEvent();
        onMinEvent();
    }


    public void visible(String button){
        // 设置点击按钮后fxml文件的可见
        if(button.equals("homeButton")) {
            home.setVisible(true);
        }
        else{
            home.setVisible(false);
        }
        if(button.equals("onbookBRHistoryClick")) {
            brHistory.setVisible(true);
        }
        else{
            brHistory.setVisible(false);
        }

        if(button.equals("xzgrtsButton")) {
            xzgrts.setVisible(true);
        }
        else{
            xzgrts.setVisible(false);
        }

        if(button.equals("glsjxxButton")) {
            glsjxx.setVisible(true);
        }
        else{
            glsjxx.setVisible(false);
        }

        if(button.equals("zczhButton")) {
            zczh.setVisible(true);
        }
        else{
            zczh.setVisible(false);
        }

        if(button.equals("glzhButton")) {
            glzh.setVisible(true);
        }
        else{
            glzh.setVisible(false);
        }

        if(button.equals("sjjhlsButton")) {
            sjjhls.setVisible(true);
        }
        else{
            sjjhls.setVisible(false);
        }

        if(button.equals("zhgllsButton")) {
            zhglls.setVisible(true);
        }
        else{
            zhglls.setVisible(false);
        }

        if(button.equals("ycxxcxButton")) {
            ycxxcx.setVisible(true);
        }
        else{
            ycxxcx.setVisible(false);
        }
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

    private void initUI(){
        // 加载配置文件
        String ID = Property.getKeyValue("ID");
        // 连接数据库查询name
        stmt = ConnectDB.connect();
        String queryString = "select admin_name from admin_info_table where admin_id=\""+ID+"\"";
        ResultSet rSet = ConnectDB.search(queryString);// 查询数据库，并返回查询结果
        String name = new String();
        try {
            rSet.next();
            name = rSet.getString("admin_name");
        }catch (SQLException e){
            e.printStackTrace();
        }

        // 更改配置中的name
        Property.updateProperties("name", name);

        // 设置顶栏的name和ID
        username.setText(name);
        userid.setText(ID);
    }




}
