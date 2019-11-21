package user;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class HistoryController {
    @FXML
    private TableView<BorrowRecord> historyTable;
    @FXML
    private TableColumn<BorrowRecord, String> bookIDCol, bookNameCol, authorCol, borrowTimeCol, returnTimeCol, operateCol;

    private String userID = Property.getKeyValue("ID");
    private Statement stm = ConnectDB.connect();

    @FXML
    private void initialize() throws SQLException {
        ObservableList<BorrowRecord> historyData = FXCollections.observableArrayList();
        // 设置每个column对应的属性
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowTimeCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnTimeCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        // 获取数据库中的数据
        String queryString = "SELECT book_borrow_table.book_id, book_name, author, b_date, r_date, renew\n" +
                "FROM `book_borrow_table`, `book_info_table`\n" +
                "WHERE book_borrow_table.book_id = book_info_table.book_id";
        ResultSet rSet = stm.executeQuery(queryString);
        // 把查询到的数据放进表里
        while (rSet.next()){
            historyData.addAll(new BorrowRecord( rSet.getString(1), rSet.getString(2), rSet.getString(3), rSet.getString(4).substring(0,10), rSet.getString(5).substring(0,10)));
        }

        // 添加一个按钮
        operateCol.setCellFactory((col) -> {
            TableCell<BorrowRecord, String> cell = new TableCell() {
                Button renewButton = new Button("续借");

                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    renewButton.getStyleClass().add("green-theme");
                    if (!empty){
                        BorrowRecord borrowRecord = (BorrowRecord) getTableView().getItems().get(getIndex());
                        renewButton.setOnMouseClicked((m) -> {
                            try {
                                Renew(borrowRecord);
                            } catch (SQLException | ParseException e) {
                                e.printStackTrace();
                            }
                        });
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        setGraphic(renewButton);
                    }
                    else {
                        setGraphic(null);
                    }
                }
            };

//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    renewButton.getStyleClass().add("green-theme");
//                    if (!empty) {
//                        BorrowRecord borrowRecord = getTableView().getItems().get(getIndex());
//                        renewButton.setOnMouseClicked((m) -> {
//                            try {
//                                Renew(borrowRecord);
//                            } catch (SQLException | ParseException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//                        setGraphic(renewButton);
//                    } else {
//                        setGraphic(null);
//                    }
//                }
//            };
            return cell;
        });
        historyTable.setItems(historyData);
    }

    public void Renew(BorrowRecord borrowRecord) throws SQLException, ParseException {
        // 检查用户状态
        UserData user = new UserData(userID);
        if (!user.getState().equals("N")){
            // 如果不是正常状态，那么直接退出程序，并弹出提示框。
            AccountManageController.showAlert(Alert.AlertType.WARNING, "提示", "你的账户处于非正常状态，请处理后再进行续借操作！");
            return;
        }

        // 检查是续借过
        String query = "select renew from `book_borrow_table` where book_id = '%s' and acct_id = '%s'";
        query = String.format(query, borrowRecord.getBookID(), userID);
        ResultSet rSet = stm.executeQuery(query);
        rSet.next();
        if (!rSet.getString("renew").equals("N")){
            // 如果续借过，退出程序
            AccountManageController.showAlert(Alert.AlertType.INFORMATION, "提示", "你已经续借过一次！");
            return;
        }

        // 确认是否真的要续借
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定续借？");
        confirmation.setHeaderText("续借");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        // 更改数据库，更新归还时间
        String time = borrowRecord.getReturnDate() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = simpleDateFormat.parse(time);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, 1);
        String  rDate = simpleDateFormat.format(rightNow.getTime());
        query = "UPDATE `library`.`book_borrow_table` SET `r_date` = '%s', `renew` = 'Y' " +
                "WHERE `book_id` = '%s' AND `acct_id` = '%s'";
        query = String.format(query, rDate, borrowRecord.getBookID(), userID);
        stm.executeUpdate(query);

        // 写入图书操作记录表
        query = "";
        stm.executeUpdate(query);

        AccountManageController.showAlert(Alert.AlertType.INFORMATION, "提示", "续借成功！！");
        initialize();
    }
}
