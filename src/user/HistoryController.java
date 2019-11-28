package user;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
<<<<<<< HEAD
=======
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
>>>>>>> jinl
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
<<<<<<< HEAD
=======
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
>>>>>>> jinl
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
<<<<<<< HEAD
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
=======
import java.util.*;
>>>>>>> jinl

public class HistoryController {
    @FXML
    private TableView<BorrowRecord> historyTable;
    @FXML
<<<<<<< HEAD
    private TableColumn<BorrowRecord, String> bookIDCol, bookNameCol, authorCol, borrowTimeCol, returnTimeCol, operateCol;

    private String userID = Property.getKeyValue("ID");
    private Statement stm = ConnectDB.connect();

    @FXML
    private void initialize() throws SQLException {
=======
    private TableColumn<BorrowRecord, String> bookIDCol, bookNameCol, authorCol, borrowTimeCol, returnTimeCol, operateCol, historyState;
    @FXML
    private ImageView refresh;
    private String userID = Property.getKeyValue("ID");
    private Statement stm = ConnectDB.connect();
    private ResultSet rSet = null;

    @FXML
    private void initialize() throws SQLException {
        initTable();
        onRefresh();
    }

    public void initTable() throws SQLException {
>>>>>>> jinl
        ObservableList<BorrowRecord> historyData = FXCollections.observableArrayList();
        // 设置每个column对应的属性
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowTimeCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnTimeCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

<<<<<<< HEAD
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
=======
        // 获取数据库中正在借阅的数据
        String queryString = "SELECT book_borrow_table.book_id, book_name, author, b_date, r_date, renew\n" +
                "FROM `book_borrow_table`, `book_info_table`\n" +
                "WHERE book_borrow_table.book_id = book_info_table.book_id and acct_id = "+ userID ;
        ResultSet resultSet = stm.executeQuery(queryString);
        // 把查询到的数据放进表里
        while (resultSet.next()){
            historyData.addAll(new BorrowRecord( resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4).substring(0,10), resultSet.getString(5).substring(0,10)));
        }
        // 先获取借阅日期
        queryString = "SELECT book_id, br_date FROM `br_record_table`\n" +
                "WHERE br_type = 'B' and acct_id = '" + userID + "'";
        resultSet = stm.executeQuery(queryString);
        Map<String, String> BrDate = new HashMap<>();
        while (resultSet.next()){
            BrDate.put(resultSet.getString(1), resultSet.getString(2));
        }
        // 获取数据库中已经归还的书
        queryString = "SELECT br_record_table.book_id, book_name, author, br_date FROM `br_record_table`, `book_info_table` " +
                "where br_type = 'R' and br_record_table.book_id = book_info_table.book_id and acct_id = '" + userID + "'";
        resultSet = stm.executeQuery(queryString);
        while (resultSet.next()){
            historyData.addAll(new BorrowRecord(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    BrDate.get(resultSet.getString(1)), resultSet.getString(4)));
        }
        // 添加一个按钮
        operateCol.setCellFactory((col) -> {
            TableCell<BorrowRecord, String> cell = new TableCell<>(){
                Button renewButton = new Button("续借");
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    renewButton.getStyleClass().add("green-theme");
                    if (!empty) {
                        BorrowRecord borrowRecord = getTableView().getItems().get(getIndex());
                        // 比较时间的大小，如果时间
                        // 检查是续借过
                        String query = "select renew from `book_borrow_table` where book_id = '%s' and acct_id = '%s'";
                        query = String.format(query, borrowRecord.getBookID(), userID);
                        try {
                            ResultSet resultSet;
                            resultSet = stm.executeQuery(query);
                            resultSet.next();
                            String renewText = resultSet.getString("renew");
                            resultSet.last();
                            query = "SELECT br_type FROM `br_record_table` where book_id = " +borrowRecord.getBookID()
                                    + " and acct_id = '" + userID + "' and br_date = '" + borrowRecord.getReturnDate() + "'";
                            resultSet = stm.executeQuery(query);
                            resultSet.next();
                            String brType = resultSet.getString("br_type");
                            resultSet.last();
                            if (brType.equals("R")){
                                // 如果已经归还，那么没有操作
                                renewButton.setText("无");
                            }
                            else if (!renewText.equals("N")){
                                // 如果续借过，Button显示为已经续借过
                                renewButton.setText("己续借");
                            }
                            else {
                                renewButton.setOnMouseClicked((m) -> {
                                    try {
                                        Renew(borrowRecord);
                                    } catch (SQLException | ParseException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        setGraphic(renewButton);
                    } else {
>>>>>>> jinl
                        setGraphic(null);
                    }
                }
            };
<<<<<<< HEAD

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
=======
            return cell;
        });

        historyState.setCellFactory((col) -> {
            TableCell<BorrowRecord, String> cell = new TableCell<>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    Label state = new Label("借阅中");
                    super.updateItem(item, empty);
                    if (!empty) {
                        BorrowRecord borrowRecord = getTableView().getItems().get(getIndex());
                        String queryString = "SELECT br_type FROM `br_record_table` where book_id = " +borrowRecord.getBookID()
                                + " and acct_id = '" + userID + "' and br_date = '" + borrowRecord.getReturnDate() + "'";
                        try {
                            ResultSet resultSet = stm.executeQuery(queryString);
                            resultSet.next();
                            String a = resultSet.getString(1);
                            if (resultSet.getString(1).equals("R")){
                                state.setText("已归还");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        setGraphic(state);
                    } else {
                        setGraphic(null);
                    }
                }
            };
>>>>>>> jinl
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

<<<<<<< HEAD
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
=======
>>>>>>> jinl

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
<<<<<<< HEAD
        query = "UPDATE `library`.`book_borrow_table` SET `r_date` = '%s', `renew` = 'Y' " +
=======
        String query = "UPDATE `library`.`book_borrow_table` SET `r_date` = '%s', `renew` = 'Y' " +
>>>>>>> jinl
                "WHERE `book_id` = '%s' AND `acct_id` = '%s'";
        query = String.format(query, rDate, borrowRecord.getBookID(), userID);
        stm.executeUpdate(query);

        // 写入图书操作记录表
<<<<<<< HEAD
        query = "";
=======
        String getBrID = "select count(*) from `br_record_table`";
        rSet = stm.executeQuery(getBrID);
        rSet.next();
        String BrID = "C" + rSet.getString(1);
        rSet.last();
        query = "INSERT INTO `library`.`br_record_table`(`book_id`, `acct_id`, `br_date`, `admin_id`, `br_id`, `br_type`) VALUES " +
                "(%s, '%s', now(), '%s', '%s', 'C')";
        query = String.format(query, borrowRecord.getBookID(), userID, userID, BrID);
>>>>>>> jinl
        stm.executeUpdate(query);

        AccountManageController.showAlert(Alert.AlertType.INFORMATION, "提示", "续借成功！！");
        initialize();
    }
<<<<<<< HEAD
=======

    private void onRefresh(){
        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    initTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
}
>>>>>>> jinl
}
