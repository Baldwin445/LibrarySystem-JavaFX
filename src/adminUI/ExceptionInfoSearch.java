package adminUI;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ExceptionInfoSearch {
    private String sql = "SELECT * FROM acct_illegal_table";
    private ObservableList<ExceptionInfoRecord> addData = FXCollections.observableArrayList();

    @FXML
    private TableColumn id,name,adminid,info,time,duetime,money;
    @FXML
    private TableView table;
    @FXML
    private TextField acctText, nameText, adminText;
    @FXML
    private Button search;
    @FXML
    private void initialize(){
        initTable();
        searchEvent();
    }


    //初始化表格
    private void initTable(){
        //设置属性
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        adminid.setCellValueFactory(new PropertyValueFactory<>("adminid"));
        info.setCellValueFactory(new PropertyValueFactory<>("info"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        duetime.setCellValueFactory(new PropertyValueFactory<>("duetime"));
        money.setCellValueFactory(new PropertyValueFactory<>("money"));

        //获取数据库数据
        ResultSet rSet = ConnectDB.search(sql);
        addData.clear();
        try{
            while(rSet.next()){
                addData.addAll(new ExceptionInfoRecord(rSet.getString(1),
                        rSet.getString(2),rSet.getString(3),
                        rSet.getString(4), rSet.getString(5),
                        rSet.getString(6),rSet.getString(7)));
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
                sql = "SELECT * FROM acct_illegal_table";
                String acct_id, acct_name, admin_id, idsql,namesql, adminsql;
                acct_id = acctText.getText();
                acct_name = nameText.getText();
                admin_id = adminText.getText();
                idsql = " AND ((acct_id LIKE '%" + acct_id +
                        "%') OR (acct_id LIKE '%" + acct_id +
                        "') OR (acct_id LIKE '" + acct_id +
                        "%'))";
                namesql = " AND ((acct_name LIKE '%" + acct_name +
                        "%') OR (acct_name LIKE '%" + acct_name +
                        "') OR (acct_name LIKE '" + acct_name +
                        "%'))";
                adminsql = " AND ((admin_id LIKE '%" + admin_id +
                        "%') OR (admin_id LIKE '%" + admin_id +
                        "') OR (admin_id LIKE '" + admin_id +
                        "%'))";
                if(!acct_id.equals("")||!acct_name.equals("")||!admin_id.equals(""))
                    sql += " WHERE acct_id = acct_id ";
                if(!acct_id.equals(""))
                    sql += idsql;
                if(!acct_name.equals(""))
                    sql += namesql;
                if(!admin_id.equals(""))
                    sql += adminsql;

                initTable();

            }
        });
    }



}
