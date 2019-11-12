package login;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Register extends Application {
    double x1;
    double y1;
    double x_stage;
    double y_stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("register_layout.fxml"));
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/image/logo_v3.png"));

        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        screenMove(primaryStage, scene);
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
