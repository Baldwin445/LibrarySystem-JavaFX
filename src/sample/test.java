package sample;

import database.ConnectDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        String getBookSurplus = "SELECT count(book_name) FROM `book_info_table` where book_name = '%s' and book_state = 'N'";
        getBookSurplus = String.format(getBookSurplus, "活着");
        ResultSet resultSet = ConnectDB.search(getBookSurplus);
        resultSet.next();
        String getBookNum = "SELECT book_name, cat_id, publisher, author, isbn, publish_year, count(book_name) FROM `book_info_table` " +
                "GROUP BY book_name;";
        ConnectDB connectDB = new ConnectDB();
        ResultSet rSet = connectDB.search(getBookNum);
        System.out.println(resultSet.getInt(1));
    }
}
