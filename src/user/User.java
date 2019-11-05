package user;

import java.sql.Statement;

public class User extends login.LoginData{
    String acct;
    String pwd;
    private String queryString;
    private Statement stmt;

    public User(){

    }
    public User(String acct, String pwd){
        this.acct = acct;
        this.pwd = pwd;
    }

}
