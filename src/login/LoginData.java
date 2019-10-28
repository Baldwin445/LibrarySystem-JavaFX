package login;

import java.sql.*;

public class LoginData {
    String acct;
    String pwd;
    private Statement stmt;

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
        if(acct.equals("")||pwd.equals("")){
            System.out.println("请输入账号密码！");
            return -1;
        }
        if(!acct.equals("admin")) {
            System.out.println("账号不存在");
            return -1;
        }
        if(!pwd.equals("admin")){
            System.out.println("密码错误");
            return -1;
        }







        return 0;
    }
}
