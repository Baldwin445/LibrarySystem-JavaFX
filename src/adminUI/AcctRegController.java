package adminUI;

import database.ConnectDB;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import login.Register;
import org.omg.PortableInterceptor.INACTIVE;
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcctRegController {
    @FXML
    private AnchorPane adminPane, usrPane;
    @FXML
    private ChoiceBox choice;
    @FXML
    private Button reg, regAll;
    @FXML
    private TextField adminacct, adminpwd1, adminpwd2, adminsex, adminage, admintel, adminname, adminemail;
    @FXML
    private TextField usracct1, usrfaculty, usrmajor, usracct2, usrtel, usremail;
    @FXML
    private void initialize(){
        initShowState();                //设置初始状态

        textFieldContextListener();     //监听textfield内容
    }

    //初始显示状态
    private void initShowState(){
        adminPane.setVisible(false);
        usrPane.setVisible(true);

        //选项切换界面
        choice();
        //注册按钮响应
        buttonRegEvent();
    }

    //选项切换界面
    private void choice(){
        choice.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(choice.getValue().equals("管理员")){
                    adminPane.setVisible(true);
                    usrPane.setVisible(false);
                }
                if(choice.getValue().equals("教师")){
                    regAll.setText("注册");

                    adminPane.setVisible(false);
                    usrPane.setVisible(true);

                    usracct2.setEditable(false);
                    usracct2.setStyle("-fx-border-color: #039ed3");
                    usracct2.setPromptText("无需输入");
                    usracct2.setText("");

                    usrmajor.setEditable(false);
                    usrmajor.setStyle("-fx-border-color: #039ed3");
                    usrmajor.setPromptText("无需输入");
                    usrmajor.setText("");
                    usrmajor.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus();
                        }
                    });
                    usrmajor.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus();
                        }
                    });
                    usracct2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus();
                        }
                    });
                    usracct2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            defaultFocus();
                        }
                    });
                }
                else{
                    usrmajor.setEditable(true);
                    usrmajor.setPromptText("输入专业");
                    usracct2.setEditable(true);
                    usracct2.setPromptText("输入账号");
                    usrmajor.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                        }
                    });
                    usrmajor.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                        }
                    });
                    usracct2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                        }
                    });
                    usracct2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                        }
                    });
                }
                if(choice.getValue().equals("学生")){
                    adminPane.setVisible(false);
                    usrPane.setVisible(true);
                    regAll.setText("批量注册");

                }
            }
        });
    }
    //注册响应事件
    private void buttonRegEvent(){
        reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regAdmin();
                clearAll();
            }
        });

        regAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regUsr();
                clearAll();
            }
        });
    }

    //注册用户、管理员方法
    private void regAdmin(){
        //检查内容
        if(!checkAdminInput()) return;
        String acct = adminacct.getText();
        String pwd1 = adminpwd1.getText();
        String pwd2 = adminpwd2.getText();
        String sex = adminsex.getText();
        String age = adminage.getText();
        String tel = admintel.getText();
        String email = adminemail.getText();
        String name = adminname.getText();
        if(!pwd1.equals(pwd2)){
            adminpwd2.setStyle("-fx-border-color: #FF0000");
            return;
        }
        if(sex.contains("男") || sex.contains("M")) sex = "M";
        else
        if(sex.contains("女") || sex.contains("F"))
            sex = "F";
        else
            sex = "M";

        try {
            int a = Integer.valueOf(age);
        }catch (Exception e){
            e.printStackTrace();
            adminage.setStyle("-fx-border-color: #FF0000");
            return;
        }

        String VALUES;
        VALUES = "(\'%s\',\'admin_%s\',\'%s\',\'N\',3,\'%s\',%s,\'%s\',\'%s\')";
        VALUES = String.format(VALUES, name, acct, pwd2, sex, age, tel, email);
        String sql = "INSERT INTO admin_info_table VALUES " + VALUES;
        ConnectDB.update(sql);
        System.out.println("注册成功！");

        try {
            ResultSet resultSet = ConnectDB.search("select count(1) from am_record_table");
            resultSet.next();
            int rec_id = Integer.parseInt(resultSet.getString(1)) + 1;
            ConnectDB.update("INSERT INTO am_record_table VALUES ('R"+ rec_id +"', 'R', 'admin_"+
                    acct + "', '"+ Property.getKeyValue("ID")+"' , now())");

        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("记录成功！");

    }
    private void regUsr(){
        //检查内容
        if(!checkUsrInput()) return;
        String acct1 = usracct1.getText();
        String acct2 = usracct2.getText();


        int ac1 = Integer.valueOf(acct1), ac2;
        if(!acct2.equals(""))
            try {
                ac2 = Integer.valueOf(acct2);
            }catch (Exception e){
                e.printStackTrace();
                ac2 = 0;
            }
        else
            ac2 = 0;

        if(ac2 > ac1 && ac2 != 0 && ac2-ac1 <= 100)
            usrRegMultiple();
        else
            usrRegSingle();


    }

    //监听TextField内容响应
    private void textFieldContextListener(){
        /***********               USER               *************/
        usracct1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!usracct1.getText().toString().equals("")) usracct1.setStyle("-fx-border-color: #039ed3");
                else usracct1.setStyle("-fx-border-color: #FF0000");
            }
        });
        usrfaculty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!usrfaculty.getText().toString().equals("")) usrfaculty.setStyle("-fx-border-color: #039ed3");
                else usrfaculty.setStyle("-fx-border-color: #FF0000");
            }
        });
        usrmajor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!usrmajor.getText().toString().equals("")) usrmajor.setStyle("-fx-border-color: #039ed3");
                else usrmajor.setStyle("-fx-border-color: #FF0000");
            }
        });

        usrtel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(choice.getValue().equals("教师")){
                    if(!usrtel.getText().equals("")) usrtel.setStyle("-fx-border-color: #039ed3");
                    else usrtel.setStyle("-fx-border-color: #FF0000");
                }
                else
                    usrtel.setStyle("-fx-border-color: #039ed3");


            }
        });
        usremail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(choice.getValue().equals("教师")){
                    if(!usremail.getText().equals("")) usremail.setStyle("-fx-border-color: #039ed3");
                    else usremail.setStyle("-fx-border-color: #FF0000");
                }
                else
                    usremail.setStyle("-fx-border-color: #039ed3");
            }
        });

        /***********              ADMIN              *************/
        adminacct.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminacct.getText().toString().equals("")) adminacct.setStyle("-fx-border-color: #039ed3");
                else adminacct.setStyle("-fx-border-color: #FF0000");
            }
        });
        adminpwd1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminpwd1.getText().toString().equals("")) adminpwd1.setStyle("-fx-border-color: #039ed3");
                else adminpwd1.setStyle("-fx-border-color: #FF0000");
            }
        });
        adminpwd2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminpwd2.getText().toString().equals("")) adminpwd2.setStyle("-fx-border-color: #039ed3");
                else adminpwd2.setStyle("-fx-border-color: #FF0000");
            }
        });
        adminsex.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminsex.getText().toString().equals("")) adminsex.setStyle("-fx-border-color: #039ed3");
                else adminsex.setStyle("-fx-border-color: #FF0000");
            }
        });
        adminage.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminage.getText().toString().equals("")) adminage.setStyle("-fx-border-color: #039ed3");
                else adminage.setStyle("-fx-border-color: #FF0000");
            }
        });
        admintel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!admintel.getText().toString().equals("")) admintel.setStyle("-fx-border-color: #039ed3");
                else admintel.setStyle("-fx-border-color: #FF0000");
            }
        });
        adminemail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminemail.getText().toString().equals("")) adminemail.setStyle("-fx-border-color: #039ed3");
                else adminemail.setStyle("-fx-border-color: #FF0000");
            }
        });
        adminname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!adminname.getText().toString().equals("")) adminname.setStyle("-fx-border-color: #039ed3");
                else adminname.setStyle("-fx-border-color: #FF0000");
            }
        });



    }

    //检查管理员内容输入
    private boolean checkAdminInput(){
        int flag = 0;
        if(adminacct.getText().equals("")){
            adminacct.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        if(adminpwd1.getText().equals("")){
            adminpwd1.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }

        if(adminpwd2.getText().equals("")){
            adminpwd2.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        if(adminsex.getText().equals("")){
            adminsex.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        if(adminage.getText().equals("")){
            adminage.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        if(admintel.getText().equals("")){
            admintel.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        if(adminemail.getText().equals("")){
            adminemail.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        if(adminname.getText().equals("")){
            adminname.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }

        if(flag == 1)return false;
        else return true;

    }
    //检查用户内容输入
    private boolean checkUsrInput(){
        int flag = 0;
        /****对于教师和学生通用****/
        if(usracct1.getText().equals("")){
            usracct1.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }
        else {
            try{
                if(Integer.valueOf(usracct1.getText()) <= 0) {
                    usracct1.setStyle("-fx-border-color: #FF0000");
                    flag = 1;
                }
                if(usracct1.getLength() < 6) {
                    usracct1.setStyle("-fx-border-color: #FF0000");
                    flag = 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                usracct1.setStyle("-fx-border-color: #FF0000");
                flag = 1;
            }
        }



        if(usrfaculty.getText().equals("")){
            usrfaculty.setStyle("-fx-border-color: #FF0000");
            flag = 1;
        }

        /****对于学生****/
        if(usrmajor.getText().equals("")){
            if(choice.getValue().equals("学生")){
                usrmajor.setStyle("-fx-border-color: #FF0000");
                flag = 1;
            }
        }
        if(!usracct2.getText().equals("")){
            try{
                if(Integer.valueOf(usracct2.getText()) <= 0) {
                    usracct2.setStyle("-fx-border-color: #FF0000");
                    flag = 1;
                }
                if(usracct2.getLength() < 6) {
                    usracct2.setStyle("-fx-border-color: #FF0000");
                    flag = 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                flag = 1;
            }
        }

        /****对于教师****/
        if(choice.getValue().equals("教师")){
            if(usrtel.getText().equals("")){
                usrtel.setStyle("-fx-border-color: #FF0000");
                flag = 1;
            }
            if(usremail.getText().equals("")){
                usremail.setStyle("-fx-border-color: #FF0000");
                flag = 1;
            }
        }

        if(flag == 1) return false;
        else return true;
    }

    //单人注册
    private void usrRegSingle(){
        String acct = usracct1.getText();
        String faculty = usrfaculty.getText();
        String major = usrmajor.getText();
        String tel = usrtel.getText();
        String email = usremail.getText();
        int role;
        if (choice.getValue().equals("教师"))
            role = 1;
        else role = 2;

        try {
            String VALUES = "(\'User\',\'%s\',\'%s\',\'N\',0,now(),now(),%d,\'M\',18,\'%s\',\'%s\',\'%s\',\'%s\')";
            VALUES = String.format(VALUES, acct, acct, role, faculty, major, tel, email);
            String sql = "INSERT INTO acct_info_table VALUES " + VALUES;
            ConnectDB.update(sql);
            ConnectDB.update("update acct_info_table set cancel_date = " +
                    "DATE_ADD(cancel_date, INTERVAL 4 YEAR)  Where acct_id = '"+ acct +"'");
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        System.out.println("注册成功！");

        try {
            ResultSet resultSet = ConnectDB.search("select count(1) from am_record_table");
            resultSet.next();
            int rec_id = Integer.parseInt(resultSet.getString(1)) + 1;
            ConnectDB.update("INSERT INTO am_record_table VALUES ('R"+ rec_id +"', 'R', '"+
                    acct + "', '"+ Property.getKeyValue("ID")+"' , now())");
        }catch (SQLException e){
            e.printStackTrace();
            return;
        }
        System.out.println("记录成功！");

    }
    //多人注册
    private void usrRegMultiple(){
        String acct = usracct1.getText();
        String faculty = usrfaculty.getText();
        String major = usrmajor.getText();
        String tel = usrtel.getText();
        String email = usremail.getText();
        int role;
        if (choice.getValue().equals("教师"))
            role = 1;
        else role = 2;

        //确定注册账号区间
        int begin, end;
        try{
            begin = Integer.valueOf(acct);
            end = Integer.valueOf(usracct2.getText());
        }catch (Exception e){
            System.out.println("填写非数字");
            return;
        }

        //记录注册操作码
        int rec_id;
        String VALUES;
        try{                //获取操作码
            ResultSet resultSet = ConnectDB.search("select count(1) from am_record_table");
            resultSet.next();
            rec_id = Integer.parseInt(resultSet.getString(1)) + 1;

            //循环注册
            for(;begin <= end;begin++){
                //注册账号
                VALUES = "(\'User\',\'%d\',\'%d\',\'N\',0,now(),now(),%d,\'M\',18,\'%s\',\'%s\',\'%s\',\'%s\')";
                VALUES = String.format(VALUES,begin, begin, role, faculty, major, tel, email);
                String sql = "INSERT INTO acct_info_table VALUES " + VALUES;
                ConnectDB.update(sql);
                ConnectDB.update("update acct_info_table set cancel_date = " +
                        "DATE_ADD(cancel_date, INTERVAL 4 YEAR)  Where acct_id = '"+ acct +"'");

                //记录操作
                ConnectDB.update("INSERT INTO am_record_table VALUES ('R"+ rec_id +"', 'R', '"+
                        begin + "', '"+ Property.getKeyValue("ID")+"' , now())");
                rec_id++;
            }


        }catch (SQLException e){
            e.printStackTrace();
            return;
        }
        System.out.println("注册成功！");
        System.out.println("记录成功！");


    }

    //清空所有TextField
    private void clearAll(){
        adminname.setText("");
        adminpwd1.setText("");
        adminpwd2.setText("");
        adminsex.setText("");
        adminage.setText("");
        admintel.setText("");
        adminemail.setText("");
        adminacct.setText("");
        usracct1.setText("");
        usracct2.setText("");
        usrfaculty.setText("");
        usrmajor.setText("");
        usrtel.setText("");
        usremail.setText("");
    }


    //设置默认聚焦点
    private void defaultFocus(){
        Platform.runLater(new Runnable() {          //设置初始焦点在nextstep图片按钮上
            @Override
            public void run() {
                choice.requestFocus();
            }
        });
    }



}
