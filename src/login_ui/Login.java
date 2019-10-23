package login_ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;

import java.awt.*;

public class Login extends Application {
    double x1;
    double y1;
    double x_stage;
    double y_stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_layout.fxml"));
        Scene scene = new Scene(root, 800, 540);
        primaryStage.setScene(scene);

        //stage窗体移动
        screenMove(primaryStage, scene);
        //stage属性设置
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();

    }

    //窗口移动监听
    private void screenMove(Stage primaryStage, Scene scene){
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //计算
                primaryStage.setX(x_stage + event.getScreenX() - x1);
                primaryStage.setY(y_stage + event.getScreenY() - y1);
            }
        });
        scene.setOnDragEntered(null);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //按下鼠标后，记录当前鼠标的坐标
                x1 =event.getScreenX();
                y1 =event.getScreenY();
                x_stage = primaryStage.getX();
                y_stage = primaryStage.getY();
            }
        });
    }
}
