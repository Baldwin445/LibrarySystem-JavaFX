package adminUI;

import database.ConnectDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookManageHistoryController {
    private String sql = "SELECT * FROM bm_record_table";
    private ObservableList<BookMHistoryRecord> addData = FXCollections.observableArrayList();

    @FXML
    private TableView table;
    @FXML
    private TableColumn op,optype,bookID,adminID,time,notes;
    @FXML
    private Button search;
    @FXML
    private TextField bookText, adminText, opText;
    @FXML
    private ChoiceBox type;
    @FXML
    private void initialize(){
        initTable();
        searchEvent();
        choiceEvent();
    }

    //初始化表格
    private void initTable(){
        //设置属性
        op.setCellValueFactory(new PropertyValueFactory<>("id"));
        optype.setCellValueFactory(new PropertyValueFactory<>("type"));
        bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        adminID.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        //获取数据库数据
        ResultSet rSet = ConnectDB.search(sql);
        addData.clear();
        try{
            while(rSet.next()){
                String type = rSet.getString(2);
                if(type.equals("D")) type = "删除";
                if(type.equals("N")) type = "入库";
                if(type.equals("M")) type = "修改信息";
                if(type.equals("P")) type = "暂停借阅";
                if(type.equals("R")) type = "恢复借阅";
                addData.addAll(new BookMHistoryRecord(rSet.getString(1),
                        type,rSet.getString(3),rSet.getString(4),
                        rSet.getString(5), rSet.getString(6)));
            }
            table.setItems(addData);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("获取信息失败");
            return;

        }


    }

    //监听choicebox选择
    private void choiceEvent(){
        type.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(type.getValue().equals("所有"))
                    sql = "SELECT * FROM bm_record_table";
                if(type.getValue().equals("书籍入库"))
                    sql = "SELECT * FROM bm_record_table WHERE type = 'N'";
                if(type.getValue().equals("删除书籍"))
                    sql = "SELECT * FROM bm_record_table WHERE type = 'D'";
                if(type.getValue().equals("修改信息"))
                    sql = "SELECT * FROM bm_record_table WHERE type = 'M'";
                if(type.getValue().equals("暂停/恢复借阅"))
                    sql = "SELECT * FROM bm_record_table WHERE (type = 'P' OR type = 'R')";

                initTable();
            }
        });
    }

    //搜索功能
    private void searchEvent(){
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String op_id, book_id, admin_id, opsql,booksql, adminsql;
                op_id = opText.getText();
                book_id = bookText.getText();
                admin_id = adminText.getText();
                opsql = " AND ((op_id LIKE '%" + op_id +
                        "%') OR (op_id LIKE '%" + op_id +
                        "') OR (op_id LIKE '" + op_id +
                        "%'))";
                booksql = " AND ((book_id LIKE '%" + book_id +
                        "%') OR (book_id LIKE '%" + book_id +
                        "') OR (book_id LIKE '" + book_id +
                        "%'))";
                adminsql = " AND ((admin_id LIKE '%" + admin_id +
                        "%') OR (admin_id LIKE '%" + admin_id +
                        "') OR (admin_id LIKE '" + admin_id +
                        "%'))";

                if(type.getValue().equals("所有")){
                    sql = "SELECT * FROM bm_record_table";
                    if(!op_id.equals("") || !book_id.equals("") || !admin_id.equals(""))
                        sql += " WHERE op_id = op_id ";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!book_id.equals(""))
                        sql += booksql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("书籍入库")){
                    sql = "SELECT * FROM bm_record_table WHERE type = 'N'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!book_id.equals(""))
                        sql += booksql;
                    if(!admin_id.equals(""))
                        sql += adminsql;

                }
                if(type.getValue().equals("删除书籍")){
                    sql = "SELECT * FROM bm_record_table WHERE type = 'D'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!book_id.equals(""))
                        sql += booksql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("修改信息")){
                    sql = "SELECT * FROM bm_record_table WHERE type = 'M'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!book_id.equals(""))
                        sql += booksql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("暂停/恢复借阅")){
                    sql = "SELECT * FROM bm_record_table WHERE (type = 'P' OR type = 'R')";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!book_id.equals(""))
                        sql += booksql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }

                initTable();




            }
        });
    }
}
