package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import login.Login_UI;

import java.sql.*;

public class Main extends Application {
    private Statement stmt;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Login_UI test = new Login_UI();
        test.start(primaryStage);
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("图书馆管理系统");
//        primaryStage.setScene(new Scene(root, 1500, 875));
//        primaryStage.getIcons().add(new Image("/image/system_icon_128px.png"));
//
//        //hide the Title
//        //primaryStage.initStyle(StageStyle.TRANSPARENT);
//
//        primaryStage.show();

//        initializeDB();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //连接数据库的方法
    private void initializeDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC","root","root");

            stmt = conn.createStatement();

            //Test MySQL
            try {
                String queryString ="select book_name, cat_id, author from book_info_table";
                ResultSet rSet = stmt.executeQuery(queryString);// 查询数据库，并返回查询结果
                while(rSet.next()){
                    String a = rSet.getString(1);
                    String b = rSet.getString(2);
                    String c = rSet.getString(3);

                    System.out.println(a + " " + b + " " + c);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
