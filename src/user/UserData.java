package user;

import database.ConnectDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserData{
    private String id;
    private String name;
    private String password;
    private String state;
    private int bookOwn;
    private String regDate;
    private String cancelDate;
    private String role;
    private String sex;
    private int age;
    private String faculty;
    private String major;
    private String tel;
    private String email;
    private String queryString;
    private Statement stmt = ConnectDB.connect();

    public UserData(String id) throws SQLException {
        this.id = id;
        queryString = "select acct_name, acct_pwd, acct_state, book_own, reg_date,acct_age, role, cancel_date, acct_id, acct_sex, acct_faculty, acct_major," +
                " acct_tel, acct_email from acct_info_table where acct_id =\"" + id + "\"";
        ResultSet rSet = ConnectDB.search(queryString);
        rSet.next();
        state = rSet.getString("acct_state");
        name = rSet.getString("acct_name");
        bookOwn = Integer.parseInt(rSet.getString("book_own"));
        regDate = rSet.getString("reg_date");
        cancelDate = rSet.getString("cancel_date");
        role = rSet.getString("role");
        sex = rSet.getString("acct_sex");
        age = Integer.parseInt(rSet.getString("acct_age"));
        faculty = rSet.getString("acct_faculty");
        major = rSet.getString("acct_major");
        tel = rSet.getString("acct_tel");
        email = rSet.getString("acct_email");
        password = rSet.getString("acct_pwd");
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public int getBookOwn() {
        return bookOwn;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public String getRole() {
        return role;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getMajor() {
        return major;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBookOwn(int bookOwn) {
        this.bookOwn = bookOwn;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void updateUser(){
//        String text = "INSERT INTO `library`.`acct_info_table`(`acct_name`, `acct_id`, `acct_pwd`, `acct_state`, " +
//                "`book_own`, `reg_date`, `cancel_date`, `role`, `acct_sex`, `acct_age`, `acct_faculty`, `acct_major`, `acct_tel`, `acct_email`)" +
//                " VALUES ('道理', '100101', '100101', 'N', 0, '2019-08-05', '2023-05-06', 1, 'M', 20, '信息学院', '软件工程', '1008611', 'jinl1874@163.com')";
//        String VALUES = "(\'%s\', \'%s\', \'%s\', \'%s\',  %d , \'%s\',    \'%s\',       %d, \'%s\', %d, \'%s\',   \'%s\',     \'%s\', \'%s\')";
//        VALUES = String.format(VALUES, name, id, password, state, bookOwn, regDate, cancelDate, role, sex,
//                age, faculty, major, tel, email);
        String query = "UPDATE `library`.`acct_info_table` SET `acct_name` = '%s', `acct_pwd` = '%s', `acct_state` = '%s', " +
                "`book_own` = %d, `acct_sex` = '%s', `acct_age` = %d, " +
                "`acct_tel` = '%s', `acct_email` = '%s' WHERE `acct_id` = '%s'";
        query = String.format(query, name, password, state, bookOwn,  sex, age, tel, email, id);
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                ", bookOwn=" + bookOwn +
                ", regData='" + regDate + '\'' +
                ", cancelData='" + cancelDate + '\'' +
                ", role='" + role + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", faculty='" + faculty + '\'' +
                ", major='" + major + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

