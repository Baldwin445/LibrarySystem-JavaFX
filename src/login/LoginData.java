package login;

import database.ConnectDB;
import javafx.scene.Parent;

import java.sql.*;
import java.util.regex.Pattern;

public class LoginData {
    int flag = 0;           //标记是否存在对应账号
    int role = 0;           //标记对应身份
    String acct;
    String pwd;
    private String queryString;
    private Statement stmt;

    public LoginData(){

    }

    public LoginData(String acct, String pwd){
        this.acct = acct;
        this.pwd = pwd;
    }

    public String getAcct() {
        return acct;
    }

    public String getPwd() {
        return pwd;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int loginAccess() {

        //检查账号、密码栏是否为空，为空返回0并提示“请输入账号密码”
        if(acct.equals("")||pwd.equals("")){
            System.out.println("请输入账号密码！");
            return 0;
        }

        //普通用户表
        try {
            stmt = ConnectDB.connect();

            //检查是否为纯数字账号
            if(acct.length()>= 5 && acct.substring(0,5).equals("admin"))
                queryString = "select admin_id, admin_pwd, admin_limits from admin_info_table";
            else
                queryString ="select acct_id, acct_pwd, role from acct_info_table";
            ResultSet rSet = ConnectDB.search(queryString);// 查询数据库，并返回查询结果
            while(rSet.next()){
                if(acct.equals(rSet.getString(1))){
                    flag = 1;
                    break;
                }
            }
            if(flag != 1) {
                //遍历查询是否存在该账号，不存在返回-1并提示“该账号不存在”
                System.out.println("账号不存在");
                return -1;
            }
            else if(!pwd.equals(rSet.getString(2))) {
                //查询该账号对应的密码，不存在返回-2并提示“密码错误”
                System.out.println("密码错误");
                return -2;
            }

            role = Integer.parseInt(rSet.getString(3));
        }catch(Exception e){
            e.printStackTrace();
        }

        return role;
    }

    public int loginAccess(String queryString){
        //检查账号、密码栏是否为空，为空返回0并提示“请输入账号密码”
        if(acct.equals("")||pwd.equals("")){
            System.out.println("请输入账号密码！");
            return 0;
        }

        //普通用户表
        try {
            stmt = ConnectDB.connect();

            //检查是否为纯数字账号
            ResultSet rSet = ConnectDB.search(queryString);// 查询数据库，并返回查询结果
            while(rSet.next()){
                if(acct.equals(rSet.getString(1))){
                    flag = 1;
                    break;
                }
            }
            if(flag != 1) {
                //遍历查询是否存在该账号，不存在返回-1并提示“该账号不存在”
                System.out.println("账号不存在");
                return -1;
            }
            else if(!pwd.equals(rSet.getString(2))) {
                //查询该账号对应的密码，不存在返回-2并提示“密码错误”
                System.out.println("密码错误");
                return -2;
            }

            role = Integer.parseInt(rSet.getString(3));
        }catch(Exception e){
            e.printStackTrace();
        }

        return role;
    }
}
