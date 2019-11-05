package user;

import java.sql.Statement;

public class Reader extends User{
    String acct;
    String pwd;
    private String queryString;
    private Statement stmt;

    public Reader(){

    }
    public Reader(String acct, String pwd){
        this.acct = acct;
        this.pwd = pwd;
    }

}
