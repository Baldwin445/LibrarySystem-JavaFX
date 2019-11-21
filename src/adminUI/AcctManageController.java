package adminUI;

import database.ConnectDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import properties.Property;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcctManageController {
    private String sql = "SELECT acct_id,acct_name,acct_sex,acct_age,acct_tel," +
            "acct_email,role,acct_state FROM acct_info_table";
    private ObservableList<AcctManageRecord> addData = FXCollections.observableArrayList();

    @FXML
    private TableView<AcctManageRecord> table;
    @FXML
    private TableColumn<AcctManageRecord, String> acct,name,sex,age,tel,email,role,state;
    @FXML
    private ChoiceBox choice;
    @FXML
    private TextField acctText, nameText;
    @FXML
    private Button search,modifyPwd,lostFind,logout,resolve;
    @FXML
    private Label tips;
    @FXML
    private void initialize(){
        initTable();
        choiceValueEvent();
        searchEvent();
        buttonFocusTableContext();
        tableFocusContext();
        tips.setText(" ");
    }


    /*******ChoiceBox选择监听*********/
    private void choiceValueEvent(){
        choice.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(choice.getValue().equals("学生"))
                    sql = "SELECT acct_id,acct_name,acct_sex,acct_age,acct_tel," +
                            "acct_email,role,acct_state FROM acct_info_table WHERE role = 2";
                if(choice.getValue().equals("教师"))
                    sql = "SELECT acct_id,acct_name,acct_sex,acct_age,acct_tel," +
                            "acct_email,role,acct_state FROM acct_info_table WHERE role = 1";
                if(choice.getValue().equals("管理员"))
                    sql = "SELECT admin_id,admin_name,admin_sex,admin_age,admin_tel," +
                            "admin_email,admin_limits,admin_state FROM admin_info_table";

                initTable();
            }
        });
    }

    /******table选择行监听*******/
    private void tableFocusContext(){
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AcctManageRecord>() {
            @Override
            public void changed(ObservableValue<? extends AcctManageRecord> observable, AcctManageRecord oldValue, AcctManageRecord newValue) {
                if(newValue.getState().contains("挂失"))
                    lostFind.setText("解挂");
                else lostFind.setText("挂失");
            }
        });
    }

    /*******初始化表格*********/
    private void initTable(){
        //设置属性
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        acct.setCellValueFactory(new PropertyValueFactory<>("acct"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));

        //获取数据库数据
        ResultSet rSet = ConnectDB.search(sql);
        addData.clear();
        try {
            while(rSet.next()){
                String sex, role, state;
                if(rSet.getString(3).equals("M")) sex = "男";
                else sex = "女";
                if(rSet.getString(7).equals("1")) role = "教师";
                else if(rSet.getString(7).equals("2")) role = "学生";
                     else if(rSet.getString(7).equals("3")) role = "管理员";
                          else role = "超级管理员";
                state = "";
                if(rSet.getString(8).equals("N")) state = "正常";
                else
                    if(rSet.getString(8).contains("F"))
                        state += "冻结 ";
                    else
                        if(rSet.getString(8).contains("L"))
                            state += "挂失 ";
                        else
                            if(rSet.getString(8).contains("D"))
                                state += "欠费";



                addData.addAll(new AcctManageRecord(rSet.getString(1),
                        rSet.getString(2),sex,
                        rSet.getString(4),rSet.getString(5),
                        rSet.getString(6),role, state));

            }
            table.setItems(addData);
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取信息失败");
            return;
        }

    }

    /*******搜索按钮监听响应*********/
    private void searchEvent(){
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String acctSql, nameSql, acct, name, adminacct , adminname;
                acct = acctText.getText();
                name = nameText.getText();
                acctSql = " AND ((acct_id LIKE '%" + acct +
                        "%') OR (acct_id LIKE '%" + acct +
                        "') OR (acct_id LIKE '" + acct +
                        "%'))";
                nameSql = " AND ((acct_name LIKE '%" + name +
                        "%') OR (acct_name LIKE '%" + name +
                        "') OR (acct_name LIKE '" + name +
                        "%'))";
                adminacct = " AND ((admin_id LIKE '%" + acct +
                        "%') OR (admin_id LIKE '%" + acct +
                        "') OR (admin_id LIKE '" + acct +
                        "%'))";
                adminname = " AND ((admin_name LIKE '%" + name +
                        "%') OR (admin_name LIKE '%" + name +
                        "') OR (admin_name LIKE '" + name +
                        "%'))";

                if(choice.getValue().equals("教师")){
                    sql = "SELECT acct_id,acct_name,acct_sex,acct_age,acct_tel," +
                            "acct_email,role,acct_state FROM acct_info_table WHERE role = 1";
                    if(!acct.equals("")) sql = sql + acctSql;
                    if(!name.equals("")) sql = sql + nameSql;

                }
                if(choice.getValue().equals("学生")){
                    sql = "SELECT acct_id,acct_name,acct_sex,acct_age,acct_tel," +
                            "acct_email,role,acct_state FROM acct_info_table WHERE role = 2";
                    if(!acct.equals("")) sql = sql + acctSql;
                    if(!name.equals("")) sql = sql + nameSql;

                }
                if(choice.getValue().equals("管理员")){
                    sql = "SELECT admin_id,admin_name,admin_sex,admin_age,admin_tel," +
                            "admin_email,admin_limits,admin_state FROM admin_info_table " +
                            "WHERE (admin_limits = 3 OR admin_limits = 4)";
                    if(!acct.equals("")) sql = sql + adminacct;
                    if(!name.equals("")) sql = sql + adminname;

                }

                initTable();


            }
        });
    }

    /*******功能按钮监听表格内容*************/
    private void buttonFocusTableContext(){
        //修改密码功能监听
        modifyPwd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modifyPwd(table.getSelectionModel().getSelectedItem().getAcct());

            }
        });
        //清除异常
        resolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearException(table.getSelectionModel().getSelectedItem().getAcct());
            }
        });
        //lostfind挂失解挂
        lostFind.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String state = table.getSelectionModel().getSelectedItem().getState();
                if(choice.getValue().equals("管理员"))
                    return;
                if(state.contains("挂失")){
                    lostFind(table.getSelectionModel().getSelectedItem().getAcct());
                    lostFind.setText("解挂");
                }
                else lostFind.setText("挂失");
            }
        });
    }


    /*********修改密码*************/
    private void modifyPwd(String id){
        String role = choice.getValue().toString();
        if(role.equals("管理员"))
            role = "admin";
        else role = "acct";

        try{
            ConnectDB.update("UPDATE "+ role +"_info_table SET "+role+"_pwd = "+role+"_id" +
                    " WHERE "+role+"_id = '"+id+"'");
        }catch (Exception e){
            System.out.println("恢复密码失败");
        }

        int num;
        try{//归还记录
            ResultSet resultSet = ConnectDB.search("select count(1) from am_record_table");
            resultSet.next();
            num =  Integer.parseInt(resultSet.getString(1)) + 1;

            String sql = "INSERT INTO am_record_table VALUES " +
                    "('M"+num+"','M','"+id+"','"+Property.getKeyValue("acct")+"',now())";
            ConnectDB.update(sql);

        }catch (Exception e){
            System.out.println("添加修改密码记录失败");
            return;
        }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
                    tips.setText("成功恢复为默认密码！");
//                    Thread.sleep(30000);
//                    tips.setText(" ");
//                }catch (Exception e){
//                    System.out.println("提醒失败!");
//                }
//            }
//        });


    }

    /*********清除账号状态异常*************/
    private void clearException(String id){
        if(table.getSelectionModel().getSelectedItem().getState().equals("正常"))
            return;

        try {
            ConnectDB.update("UPDATE acct_info_table SET acct_state = 'N' WHERE acct_id = '"
                    + id + "'");
            table.getSelectionModel().getSelectedItem().setState("正常");

        }catch (Exception e){
            System.out.println("恢复状态失败！");
            return;
        }

        addAcctManageRecord(id, "S");

    }

    /*********添加管理记录**********/
    private void addAcctManageRecord(String id ,String type){
        int num;
        try{//归还记录
            ResultSet resultSet = ConnectDB.search("select count(1) from am_record_table");
            resultSet.next();
            num =  Integer.parseInt(resultSet.getString(1)) + 1;

            String sql = "INSERT INTO am_record_table VALUES " +
                    "('"+type+num+"','"+type+"','"+id+"','"+Property.getKeyValue("acct")+"',now())";
            ConnectDB.update(sql);

        }catch (Exception e){
            System.out.println("添加修改密码记录失败");
            return;
        }

    }

    /********解挂挂失*********/
    private void lostFind(String id){
        String state;
        try {
            state = table.getSelectionModel().getSelectedItem().getState();
            state = deleteChars(state);
            table.getSelectionModel().getSelectedItem().setState("正常");
        }catch (Exception e){
            System.out.println("修改表格状态失败！");
            return;
        }

        String stateEN = "";
        try{
            if(state.contains("冻结"))
                stateEN += "F";
            else if(state.contains("欠费"))
                stateEN += "D";
            else stateEN = "N";
            ConnectDB.update("UPDATE acct_info_table SET acct_state = '"+stateEN+"'" +
                    " WHERE acct_id = '"+id+"'");
        }catch (Exception e){
            System.out.println("修改数据库状态失败！");
            return;
        }

        addAcctManageRecord(id, "S");



    }

    /*******删除字符串中指定字符*****/
    private String deleteChars(String str){
        String deleteString = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '挂') {
                deleteString += str.charAt(i);
            }
        }
        str = deleteString;
        deleteString = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '失') {
                deleteString += str.charAt(i);
            }
        }
        return deleteString;

    }


}
