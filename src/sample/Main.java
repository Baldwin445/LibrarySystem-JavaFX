package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("图书馆管理系统");
        primaryStage.setScene(new Scene(root, 1500, 875));
        primaryStage.getIcons().add(new Image("/image/system_icon_128px.png"));

        //hide the Title
        //primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
