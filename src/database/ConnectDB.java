package database;

import java.sql.*;

public class ConnectDB {
    static String connURL = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
    static String connClass = "com.mysql.cj.jdbc.Driver";
    static Statement stmt;
    static ResultSet rSet;

    //数据库连接静态方法
    static public Statement connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC","root","root");

            stmt = conn.createStatement();

        }catch(Exception e){
            e.printStackTrace();
        }
        return stmt;
    }

    //搜索并返回结果静态方法
    static public ResultSet search(String str){
        try{
            rSet = stmt.executeQuery(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rSet;
    }

    //修改更新数据静态方法
    static public void update(String str){
        try{
            rSet = stmt.executeQuery(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
