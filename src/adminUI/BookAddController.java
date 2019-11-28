package adminUI;

import database.ConnectDB;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookAddController {
    @FXML
    private TableView<AddRecord> table;
    @FXML
    private TableColumn<AddRecord, String> name,isbn,publisher,author,year,lang,reason;
    @FXML
    private Button pass, nopass, getInfo;


    private ObservableList<AddRecord> addData = FXCollections.observableArrayList();
    private ResultSet rSet;
    private String sql;



    @FXML
    private void initialize(){
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        buttonEvent();

    }

    private void initTable()throws SQLException {
        //初始添加每列对应属性
        name.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        year.setCellValueFactory(new PropertyValueFactory<>("bookYear"));
        lang.setCellValueFactory(new PropertyValueFactory<>("lang"));
        reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        //获取数据库信息
        sql = "SELECT book_name, isbn, publisher, author, publish_year, lang, reco_info " +
                "FROM recommend_record_table WHERE info_state = 'N'";
        rSet = ConnectDB.search(sql);

        addData.clear();                //清除原有列表内容
        while(rSet.next()){             //重新填充列表内容
            System.out.println(rSet.getString(1));
            addData.addAll(new AddRecord(rSet.getString(1),rSet.getString(2),
                    rSet.getString(3), rSet.getString(4),rSet.getString(5),
                    rSet.getString(6),rSet.getString(7)));

            table.setItems(addData);
        }



    }

    //button监听
    private void buttonEvent(){
        //获取信息button响应
        getInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    initTable();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
        //通过button响应
        pass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table.getSelectionModel().getSelectedIndices().forEach(item -> updatePassInfo(item));
                try{
                    initTable();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
        });
        //不通过button响应
        nopass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table.getSelectionModel().getSelectedIndices().forEach(item -> updateNoPassInfo(item));
                try{
                    initTable();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });

    }

    //
    private void updatePassInfo(int index){
        String isbn, name, publisher, author, year;
        int id;
        try {
            rSet.beforeFirst();
            for(int a = 0; true ;a++){
                rSet.next();
                if (a == index){
                    isbn = rSet.getString("isbn");
                    name = rSet.getString("book_name");
                    publisher = rSet.getString("publisher");
                    author = rSet.getString("author");
                    year = rSet.getString("publish_year");
                    break;
                }
            }

            ConnectDB.update("UPDATE recommend_record_table SET info_state = 'T' " +
                    "WHERE isbn = '" + isbn +"'");
            System.out.println("ISBN: "+isbn +" 已处理通过");

            //添加书籍
            ResultSet resultSet = ConnectDB.search("select count(1) from book_info_table");
            resultSet.next();
            id =  Integer.parseInt(resultSet.getString(1)) + 1;
            String VALUES = "(\'%s\', %d, \'A%d\', \'A\', \'%s\', \'%s\', \'%s\', %s, 180, \'N\')";
            System.out.println(VALUES);
            VALUES = String.format(VALUES, name, id, id, publisher, author, isbn, year);
            String sql = "INSERT INTO book_info_table VALUES " + VALUES;
            ConnectDB.update(sql);
            System.out.println("ISBN: "+isbn +" 已加入书籍库中");

            //添加管理员信息
            ConnectDB.update("UPDATE recommend_record_table SET admin_id = '" +
                    Property.getKeyValue("ID")+ "', op_date = now()" +
                    "WHERE isbn = " + isbn);

            //修改为已在库中状态
            ConnectDB.update("UPDATE recommend_record_table SET info_state = 'O' " +
                    "WHERE isbn = '" + isbn +"'");
            System.out.println("ISBN: "+isbn +" 已处理通过");

            //添加入管理记录
            resultSet = ConnectDB.search("select count(1) from bm_record_table");
            resultSet.next();
            int rec_id = Integer.parseInt(resultSet.getString(1)) + 1;
            ConnectDB.update("INSERT INTO bm_record_table VALUES ('N"+ rec_id +"', 'N', '"+ id +
                    "', '"+ Property.getKeyValue("ID")+"' , '从荐购记录添加书籍' , now())");
            System.out.println("ISBN: "+isbn +" 已记录管理");



        }
        catch (SQLException e){
            e.printStackTrace();
        }






    }
    private void updateNoPassInfo(int index){
        String isbn;
        try {
            rSet.beforeFirst();
            for(int a = 0; true ;a++){
                rSet.next();
                if (a == index){
                    isbn = rSet.getString("isbn");
                    break;
                }
            }

            ConnectDB.update("UPDATE recommend_record_table SET info_state = 'F' " +
                    "WHERE isbn = '" + isbn +"'");
            System.out.println("ISBN: "+isbn +" 已处理并不通过");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
