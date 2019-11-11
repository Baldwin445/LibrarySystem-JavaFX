package user;

import database.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;


public class RecommendController {
    // 各个输入框的id
    @FXML
    private TextField bookName, author, publisher, publisherYear, isbn, lang;
    @FXML
    private TextArea recommendInfo;
    // 提交按钮的id
    @FXML
    private Button recommendButton;

    // 提示窗口
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    Statement stmt = ConnectDB.connect();
    private String userID;


    @FXML
    private void initialize(){
        userID = Property.getKeyValue("ID");
    }

    // 点击推荐按钮事件
    @FXML
    public void onRecommendButtonClick(ActionEvent event) throws SQLException {
        // 各文本框里面的文本
        String bookNameText = bookName.getText();
        String authorText = author.getText();
        String publisherText = publisher.getText();
        String publishYearText = publisherYear.getText();
        String isbnText = isbn.getText();
        String langText = lang.getText();
        String recommendInfoText = recommendInfo.getText();
        // 获取一个Date对象
        Date date = new Date();
        // 将日期时间转换为数据库中的timestamp类型
        Timestamp timeStamp = new Timestamp(date.getTime());

        // 检查输入, 如果错误则直接退出程序
        if (checkInput() == false)  return;
        // 连接数据库并插入语句
        String VALUES = "(\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', %d , \'%s\', \'%s\', \'%s\', \'%s\')";
        VALUES = String.format(VALUES, bookNameText, userID, publisherText, authorText, isbnText,
                Integer.parseInt(publishYearText), langText, recommendInfoText, "N", timeStamp.toString());
        String query = "INSERT INTO `library`.`recommend_record_table`(`book_name`, `acct_id`, `publisher`, " +
                "`author`, `isbn`, `publish_year`, `lang`, `reco_info`, `info_state`, `op_date`) " +
                "VALUES " + VALUES;
        ConnectDB.update(query);

        // 检查是否成功写入数据库
        int ifSuccess = checkInsert(isbnText);
        if (ifSuccess==0) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.headerTextProperty().set("推荐失败");
            alert.setContentText("请检查ISBN是否输入正确!");
        }
        else {
            alert.titleProperty().set("提示");
            alert.headerTextProperty().set("推荐成功");
            alert.setContentText("感谢你的建议");
            // 清空输入框的内容
            clear();
        }
        alert.show();
    }

    // 检查是否写入数据库
    public int checkInsert(String ISBN) throws SQLException {
        String queryString = "select isbn from recommend_record_table where isbn =\"" + ISBN + "\"";
        ResultSet rSet = ConnectDB.search(queryString);
        if (rSet.next()) return 1;
        return 0;
    }

    //清空文本框
    public void clear(){
        bookName.clear();
        author.clear();
        publisher.clear();
        publisherYear.clear();
        isbn.clear();
        lang.clear();
        recommendInfo.clear();
    }

    //检查输入是否正确
    public boolean checkInput() throws SQLException {
        String Text = "错误信息:\n";
        String problemText = "";
        // 判断错误信息
        if (bookName.getText().length()>100)  problemText += "书名过长！\n";
        if (bookName.getText().length()==0) problemText += "书名不能为空\n";
        if (author.getText().length()==0) problemText += "作者不能为空\n";
        if (author.getText().length()>100) problemText += "作者的名字过长\n";
        if (publisher.getText().length()>20) problemText += "出版社的名字过长\n";
        if (publisher.getText().length()==0) problemText += "出版社不能为空\n";
//        if (isbnText.length()!=13) problemText += "ISBN的长度必须为13\n";
            // 检查数据库是否存在isbn
        if (checkInsert(isbn.getText())==1) problemText += "请检查ISBN是否输入正确";
        if (isbn.getText().length()==0) problemText += "ISBN不能为空\n";
        if (publisherYear.getText().equals("")) problemText += "年份不能为空\n";
        else if (!isNumber(publisherYear.getText())) problemText += "年份必须为数字\n";
        else if (publisherYear.getText().length()!=4) problemText += "年份必须为4位数字\n";
        if (lang.getText().length()>2 || lang.getText().length()==1) problemText += "语言为CN或者EN\n";
        if (recommendInfo.getText().length()>100) problemText += "推荐理由过长\n";

        // 将错误信息写入提示框
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.headerTextProperty().set("请检查输入");
        error.setContentText(Text + problemText);
        // 如果problemText没有错误，直接退出程序
        if (problemText.equals("")) {
            return true;
        }
        // 显示错误信息
        error.show();
        return false;
    }

    // 判断字符串是否为数字
    public static boolean isNumber(String str){
        if ((str!=null) && !"".equals(str.trim())){
            return str.matches("^[0-9]*$");
        }
        return false;
    }
}
