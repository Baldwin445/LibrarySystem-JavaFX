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

public class AcctManageHistoryController {
    private String sql = "SELECT * FROM am_record_table";
    private ObservableList<AcctMHistoryRecord> addData
            = FXCollections.observableArrayList();

    @FXML
    private TextField acctText,adminText,opText;
    @FXML
    private TableView table;
    @FXML
    private TableColumn opid,optype,acctid,adminid,time;
    @FXML
    private Button search;
    @FXML
    private ChoiceBox type;
    @FXML
    private void initialize(){
        initTable();
        choiceEvent();
        searchEvent();

    }

    //初始化表格
    private void initTable(){
        //设置属性
        opid.setCellValueFactory(new PropertyValueFactory<>("opid"));
        optype.setCellValueFactory(new PropertyValueFactory<>("type"));
        acctid.setCellValueFactory(new PropertyValueFactory<>("acctId"));
        adminid.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        //获取数据库数据
        ResultSet rSet = ConnectDB.search(sql);
        addData.clear();
        try{
            while(rSet.next()){
                String type = rSet.getString(2);
                if(type.equals("L")) type = "挂失";
                if(type.equals("F")) type = "解挂";
                if(type.equals("R")) type = "注册账号";
                if(type.equals("C")) type = "注销账号";
                if(type.equals("M")) type = "修改密码";
                if(type.equals("S")) type = "解除异常";
                if(type.equals("P")) type = "缴费";
                addData.addAll(new AcctMHistoryRecord(rSet.getString(1),
                        type,rSet.getString(3),
                        rSet.getString(4), rSet.getString(5)));
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
                    sql = "SELECT * FROM am_record_table";
                if(type.getValue().equals("注册账号"))
                    sql = "SELECT * FROM am_record_table WHERE type = 'R'";
                if(type.getValue().equals("注销账号"))
                    sql = "SELECT * FROM am_record_table WHERE type = 'C'";
                if(type.getValue().equals("修改密码"))
                    sql = "SELECT * FROM am_record_table WHERE type = 'M'";
                if(type.getValue().equals("解除异常"))
                    sql = "SELECT * FROM am_record_table WHERE type = 'S'";
                if(type.getValue().equals("缴费"))
                    sql = "SELECT * FROM am_record_table WHERE type = 'P'";

                initTable();
            }
        });
    }

    //搜索功能
    private void searchEvent(){
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String op_id, acct_id, admin_id, opsql,acctsql, adminsql;
                op_id = opText.getText();
                acct_id = acctText.getText();
                admin_id = adminText.getText();
                opsql = " AND ((op_id LIKE '%" + op_id +
                        "%') OR (op_id LIKE '%" + op_id +
                        "') OR (op_id LIKE '" + op_id +
                        "%'))";
                acctsql = " AND ((acct_id LIKE '%" + acct_id +
                        "%') OR (acct_id LIKE '%" + acct_id +
                        "') OR (acct_id LIKE '" + acct_id +
                        "%'))";
                adminsql = " AND ((admin_id LIKE '%" + admin_id +
                        "%') OR (admin_id LIKE '%" + admin_id +
                        "') OR (admin_id LIKE '" + admin_id +
                        "%'))";

                if(type.getValue().equals("所有")){
                    sql = "SELECT * FROM am_record_table";
                    if(!op_id.equals("") || !acct_id.equals("") || !admin_id.equals(""))
                        sql += " WHERE op_id = op_id ";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!acct_id.equals(""))
                        sql += acctsql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("注册账号")){
                    sql = "SELECT * FROM am_record_table WHERE type = 'R'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!acct_id.equals(""))
                        sql += acctsql;
                    if(!admin_id.equals(""))
                        sql += adminsql;

                }
                if(type.getValue().equals("注销账号")){
                    sql = "SELECT * FROM am_record_table WHERE type = 'C'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!acct_id.equals(""))
                        sql += acctsql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("修改密码")){
                    sql = "SELECT * FROM am_record_table WHERE type = 'M'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!acct_id.equals(""))
                        sql += acctsql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("解除异常")){
                    sql = "SELECT * FROM am_record_table WHERE type = 'S'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!acct_id.equals(""))
                        sql += acctsql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                if(type.getValue().equals("缴费")){
                    sql = "SELECT * FROM am_record_table WHERE type = 'P'";
                    if(!op_id.equals(""))
                        sql += opsql;
                    if(!acct_id.equals(""))
                        sql += acctsql;
                    if(!admin_id.equals(""))
                        sql += adminsql;
                }
                initTable();
            }
        });
    }
}
