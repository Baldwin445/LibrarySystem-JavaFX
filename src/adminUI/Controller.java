package adminUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    // fxml文件
    @FXML
    private AnchorPane home, xgmm, zxzh, gsjgjyz, jcwg, xzsj, scsj, xgsjxx,tszthfjy,xzgrts,glsjxx,zczh,glzh,sjjhls,zhglls,ycxxcx;
    // Button
    @FXML
    private Button homeButton, xgmmButton, zxzhButton, gsjgjyzButton, jcwgButton, xzsjButton, scsjButton, xgsjxxButton,tszthfjyButton,tc,xz,xzgrtsButton,glzhButton,glsjxxButton,zczhButton,sjjhlsButton,zhgllsButton,ycxxcxButton,tcButton;

    @FXML
    private ImageView close;
    @FXML
    private ImageView min;

    public Controller() {
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
    public void ontcClick(ActionEvent event){
        visible("tcButton");
    }



    public void visible(String button){
        // 设置点击按钮后fxml文件的可见
        if(button.equals("homeButton")) {
            home.setVisible(true);
        }
        else{
            home.setVisible(false);
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





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
