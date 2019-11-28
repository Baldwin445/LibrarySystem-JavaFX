package adminUI;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookBRHistoryController {
    private String sql = "SELECT br_id,book_id,acct_id,admin_id,br_type,br_date" +
            " FROM br_record_table";
    private ObservableList<BookBRHistroyRecord> addData
            = FXCollections.observableArrayList();

    @FXML
    private TableView table;
    @FXML
    private TableColumn  opid,book,acct,admin,op,duetime;
    @FXML
    private TextField bookText,acctText,opText,adminText;
    @FXML
    private Button search;
    @FXML
    private Label tip;
    @FXML
    private void initialize(){
        tip.setVisible(false);
        initTable();
        searchEvent();
    }


    //初始化表格
    private void initTable(){
        //设置属性
        opid.setCellValueFactory(new PropertyValueFactory<>("opid"));
        book.setCellValueFactory(new PropertyValueFactory<>("book"));
        acct.setCellValueFactory(new PropertyValueFactory<>("acct"));
        admin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        op.setCellValueFactory(new PropertyValueFactory<>("op"));
        duetime.setCellValueFactory(new PropertyValueFactory<>("duetime"));

        //获取数据库数据
        ResultSet rSet = ConnectDB.search(sql);
        addData.clear();
        try{
            while(rSet.next()){
                String op = rSet.getString(5);
                if(op.equals("B"))
                    op = "借书";
                else op = "还书";
                addData.addAll(new BookBRHistroyRecord(rSet.getString(1),
                        rSet.getString(2),rSet.getString(3),
                        rSet.getString(4), op,
                        rSet.getString(6)));
            }
            table.setItems(addData);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("获取信息失败");
            return;
        }
    }


    //搜索功能
    private void searchEvent(){
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sql = "SELECT br_id,book_id,acct_id,admin_id,br_type,br_date" +
                        " FROM br_record_table";
                String book,acct,op,admin, booksql,acctsql, opsql, adminsql;
                book = bookText.getText();
                acct = acctText.getText();
                op = opText.getText();
                admin = adminText.getText();
                booksql = " AND ((book_id LIKE '%" + book +
                        "%') OR (book_id LIKE '%" + book +
                        "') OR (book_id LIKE '" + book +
                        "%'))";
                acctsql = " AND ((acct_id LIKE '%" + acct +
                        "%') OR (acct_id LIKE '%" + acct +
                        "') OR (acct_id LIKE '" + acct +
                        "%'))";
                opsql = " AND ((br_id LIKE '%" + op +
                        "%') OR (br_id LIKE '%" + op +
                        "') OR (br_id LIKE '" + op +
                        "%'))";
                adminsql = " AND ((admin_id LIKE '%" + admin +
                        "%') OR (admin_id LIKE '%" + admin +
                        "') OR (admin_id LIKE '" + admin +
                        "%'))";

                if(!book.equals("")||!acct.equals("")||!op.equals("")||!admin.equals(""))
                    sql += " WHERE br_date = br_date ";
                if(!book.equals(""))
                    sql += booksql;
                if(!acct.equals(""))
                    sql += acctsql;
                if(!op.equals(""))
                    sql += opsql;
                if(!admin.equals(""))
                    sql += adminsql;

                initTable();

            }
        });
    }


}
