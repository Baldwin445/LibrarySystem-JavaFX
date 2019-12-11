package user;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import operate.UIOperate;
import properties.Property;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;


public class SearchController {
        @FXML
        private AnchorPane pane1, pane2;
        @FXML
        private ImageView change;
        @FXML
        private Button search1, search2;
        @FXML
        private TableView<BookManageRecord> table;
        @FXML
        private TableColumn<BookManageRecord, String> id,name,cat,publisher,author,isbn,year,state,operate;
        @FXML
        private TextField key, nameText, idText, publisherText, authorText, isbnText, yearText;

        private int imageValue = 1;
        private ObservableList<BookManageRecord> manageDate = FXCollections.observableArrayList();
        private ResultSet rSet;
        private Statement stmt = ConnectDB.connect();
        private String userID = Property.getKeyValue("ID");
        private String sql = "SELECT book_id, book_name, cat_id, publisher, author, isbn," +
            "publish_year,book_state FROM book_info_table";

        @FXML
        private void initialize(){
            initTable();
            switchSearch();
            showSearchResult();

        }


        //切换简略/详细搜索界面
        private void switchSearch(){
            change.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    change.setCursor(Cursor.HAND);
                }
            });
            change.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(imageValue == 1){
                        imageValue += 1;
                        change.setImage(new Image("/image/bookmanage/fold.png"));
                        pane1.setVisible(false);
                        pane2.setVisible(true);
                    }
                    else {
                        imageValue -= 1;
                        change.setImage(new Image("/image/bookmanage/open.png"));
                        pane1.setVisible(true);
                        pane2.setVisible(false);
                    }
                }
            });
        }

        //搜索显示结果
        private void showSearchResult() {
            search1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    initTable(key.getText());

                }
            });
            search2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    searchAllInfo();
                }
            });
        }

        //不带参数初始化所有书籍数据
        private void initTable() {
            //设置每列属性
//            id.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            name.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            cat.setCellValueFactory(new PropertyValueFactory<>("category"));
            publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
            author.setCellValueFactory(new PropertyValueFactory<>("author"));
            isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            year.setCellValueFactory(new PropertyValueFactory<>("year"));
            state.setCellValueFactory(new PropertyValueFactory<>("book_num"));
            //设置表格属性
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            // 获取总数
            String getBookNum = "SELECT book_name, count(book_name) FROM `book_info_table` " +
                    "GROUP BY book_name;";
            rSet = ConnectDB.search(getBookNum);
            // 获取未借的数量
            String getBookSurplus = "SELECT count(book_name) FROM `book_info_table` where book_state = 'N' and book_name = '%s'";
            manageDate.clear();
            Map<String, Integer> BookNumName = new HashMap<>();
            Map<String, Integer> BookNumNameSurplus = new HashMap<>();

            try{
                while (rSet.next()){
                    BookNumName.put(rSet.getString(1), rSet.getInt(2));
                }
//                rSet = ConnectDB.search(getBookSurplus);
                Set nameSet = BookNumName.keySet();
                for(Iterator item = nameSet.iterator(); item.hasNext();)
                {
                    String key = (String)item.next();
                    String getSurplus = String.format(getBookSurplus, key);
                    ResultSet resultSet = stmt.executeQuery(getSurplus);
                    resultSet.next();
                    BookNumNameSurplus.put(key, resultSet.getInt(1));
                    resultSet.last();
                }
                rSet = stmt.executeQuery("SELECT book_name, cat_id, publisher, author, isbn,publish_year FROM book_info_table GROUP BY book_name");
                while (rSet.next()){
                    String num = BookNumNameSurplus.get(rSet.getString("book_name")) + "/" + BookNumName.get(rSet.getString("book_name"));
                    manageDate.addAll(new BookManageRecord(rSet.getString("book_name"),
                            rSet.getString("cat_id"), rSet.getString("publisher"),
                            rSet.getString("author"), rSet.getString("isbn"),
                            rSet.getString("publish_year"), num));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            table.setItems(manageDate);
            operate.setCellFactory((col) -> {
                TableCell<BookManageRecord, String> cell = new TableCell<BookManageRecord, String>(){
                    Button borrowButton = new Button("借阅");
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        borrowButton.getStyleClass().add("green-theme");
                        if (!empty) {
                            BookManageRecord bookManageRecord = getTableView().getItems().get(getIndex());
                            int num = Integer.parseInt(bookManageRecord.getBook_num().substring(0,1));
                            String name = bookManageRecord.getBookName();
                            if (num==0)
                                borrowButton.setText("预约");
                            borrowButton.setOnMouseClicked((m) -> {
                                Borrow(bookManageRecord, borrowButton);
                            });
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            setGraphic(borrowButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            });
        }

        //初始化查询书籍关键字
        private void initTable(String bookName){
            String sql = "SELECT book_name,cat_id,publisher,author,isbn," +
                    "publish_year, count(book_name) " +
                    "FROM book_info_table " +
                    "WHERE (book_name LIKE '%" + bookName + "%')" +
                    "Group by book_name";

            rSet = ConnectDB.search(sql);
            String getBookSurplus = "SELECT count(book_name) FROM `book_info_table` where book_name = '%s' and book_state = 'N'";
            // 获取剩余数量
            manageDate.clear();
            List<String> BookName = new ArrayList<>();
            List<String> BookNum = new ArrayList<>();
            try{
                while (rSet.next()){
                    BookName.add(rSet.getString(1));
                }
                rSet.last();
                for (int i=0; i < BookName.size(); i ++){
                    String getSurplus = String.format(getBookSurplus, BookName.get(i));
                    ResultSet resultSet = stmt.executeQuery(getSurplus);
                    resultSet.next();
                    BookNum.add(resultSet.getInt(1)+"");
                    resultSet.last();
                }
                rSet = ConnectDB.search(sql);
                int i = 0;
                while (rSet.next()){
                    String stateCN = BookNum.get(i) + "/" + rSet.getString(7);
                    manageDate.addAll(new BookManageRecord(rSet.getString(1),
                            rSet.getString(2), rSet.getString(3),
                            rSet.getString(4), rSet.getString(5),
                            rSet.getString(6), stateCN));
                    i++;
                }
                System.out.println(BookName.toString()+ BookNum.toString());
            }catch (SQLException e){
                e.printStackTrace();
            }

            table.setItems(manageDate);

        }

        // 借阅事件
        private void Borrow(BookManageRecord bookManage, Button button){
            // 检查用户状态以及用户借书的数量
            String query = "SELECT acct_state FROM `acct_info_table` where acct_id = " + userID;
            try {
                rSet = stmt.executeQuery(query);
                rSet.next();
                if (!rSet.getString(1).equals("N")){
                    UIOperate.showAlert(Alert.AlertType.ERROR, "提示", "你的账户处于非法状态，请处理后再借书！");
                    return;
                }
                rSet.last();
                query = "SELECT book_own FROM `acct_info_table` where acct_id = " + userID ;
                rSet = stmt.executeQuery(query);
                rSet.next();
                if (rSet.getInt(1) > 200) {
                    UIOperate.showAlert(Alert.AlertType.INFORMATION, "提示", "你所借的书过多，无法继续借阅！");
                    return;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            int num = Integer.parseInt(bookManage.getBook_num().substring(0,1));
            // 如果还有库存
            if (num!=0){
                // 提示是否真的要借
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定借阅这本书？");
                confirmation.setHeaderText("借阅");
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.get() == ButtonType.CANCEL){
                    return;
                }

                // 在数据库中找到符合条件的书，设置为借阅状态
                String queryBook = "SELECT book_id, long_time FROM `book_info_table` where book_name = '%s' and book_state = 'N';";
                queryBook = String.format(queryBook, bookManage.getBookName());
                try{
                    rSet = stmt.executeQuery(queryBook);
                    rSet.next();
                    String bookID = rSet.getString(1);
                    String renewTime = rSet.getString(2);
                    rSet.last();
                    query = "UPDATE `library`.`book_info_table` SET `book_state` = 'B' WHERE book_id = '%s' ";
                    query = String.format(query, bookID);
                    stmt.executeUpdate(query);
                    // 增加一个借阅记录
                    String getBrID = "select count(*) from `book_borrow_table`";
                    rSet = stmt.executeQuery(getBrID);
                    rSet.next();
                    String BrID = "B" + rSet.getString(1);
                    rSet.last();
                    query = "INSERT INTO `library`.`book_borrow_table`(`book_id`, `acct_id`, `b_date`, `r_date`, `admin_id`, `br_id`, `renew`)" +
                            " VALUES (%s, '%s', now(), now(), '%s', '%s', 'N')";
                    query = String.format(query, bookID, userID, userID, BrID);
                    stmt.executeUpdate(query);
                    query = "update book_borrow_table set r_date = DATE_ADD(r_date, INTERVAL %s DAY)  Where br_id = '" + BrID + "'";
                    query = String.format(query, renewTime);
                    stmt.executeUpdate(query);
                    UIOperate.showAlert(Alert.AlertType.INFORMATION, "提示", "借阅成功！");
                    String keyValue = key.getText();
                    if (keyValue.equals("")) initTable();
                    else initTable(keyValue);
                    // 用户的所拥有的书本+1
                    query = "UPDATE `library`.`acct_info_table` SET book_own = book_own + 1 WHERE `acct_id` = " + userID;
                    stmt.executeUpdate(query);

                    //将操作记录写入表
                    query = "INSERT INTO `library`.`br_record_table`(`book_id`, `acct_id`, `br_date`, `admin_id`, `br_id`, `br_type`) " +
                            "VALUES (%s, '%s', now(), '%s', '%s', 'B')";
                    query = String.format(query, bookID, userID, userID, BrID);
                    stmt.executeUpdate(query);

                } catch (SQLException e) {
                    UIOperate.showAlert(Alert.AlertType.ERROR, "错误", e.toString());
                    e.printStackTrace();
                }

            }
            else{
                // 确认
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定预约这本书？");
                confirmation.setHeaderText("预约");
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.get() == ButtonType.CANCEL){
                    return;
                }
                try {
                    // 检查用户的预约，如果超过三次就提示不能再预约了
                    query = "SELECT count(acct_id) FROM `book_order_table` where acct_id = " + userID;
                    rSet = stmt.executeQuery(query);
                    rSet.next();
                    int requestNum = rSet.getInt(1);
                    if (requestNum >= 3){
                        UIOperate.showAlert(Alert.AlertType.INFORMATION, "提示", "你当前所预约的书已经到达三本，不能继续预约！");
                        return;
                    }
                    // 获取预约书本的ID
                    String queryBook = "SELECT book_id FROM `book_info_table` where book_name = '%s' and book_state = 'B';";
                    queryBook = String.format(queryBook, bookManage.getBookName());
                    rSet = stmt.executeQuery(queryBook);
                    rSet.next();
                    String bookID = rSet.getString(1);
                    // 更改书本的状态
                    query = "UPDATE `library`.`book_info_table` SET `book_state` = 'R' WHERE `book_id` = %s";
                    query = String.format(query, bookID);
                    stmt.executeUpdate(query);

                    // 写入预约
                    query = "INSERT INTO `library`.`book_order_table`(`book_id`, `acct_id`, `remain_day`, `isbn`) " +
                            "VALUES (%s, '%s', 10, '%s')";
                    query = String.format(query, bookID, userID, bookManage.getIsbn());
                    stmt.executeUpdate(query);

                    UIOperate.showAlert(Alert.AlertType.INFORMATION, "提示", "预约成功!");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    private void searchAllInfo(){
        String name, id, publisher, author, isbn, year;
        sql = "SELECT book_id,book_name,cat_id,publisher,author,isbn, count(book_name)" +
                "publish_year,book_state FROM book_info_table ";
        String namesql, idsql, publishersql, authorsql, isbnsql, yearsql;
        name = nameText.getText();
        id = idText.getText();
        publisher = publisherText.getText();
        author = authorText.getText();
        isbn = isbnText.getText();
        year = yearText.getText();

        namesql = " AND ((book_name LIKE '%" + name +
                "%') OR (book_name LIKE '%" + name +
                "') OR (book_name LIKE '" + name +
                "%'))";
        idsql = " AND ((book_id LIKE '%" + id +
                "%') OR (book_id LIKE '%" + id +
                "') OR (book_id LIKE '" + id +
                "%'))";
        publishersql = " AND ((publisher LIKE '%" + publisher +
                "%') OR (publisher LIKE '%" + publisher +
                "') OR (publisher LIKE '" + publisher +
                "%'))";
        authorsql = " AND ((author LIKE '%" + author +
                "%') OR (author LIKE '%" + author +
                "') OR (author LIKE '" + author +
                "%'))";
        isbnsql = " AND ((isbn LIKE '%" + isbn +
                "%') OR (isbn LIKE '%" + isbn +
                "') OR (isbn LIKE '" + isbn +
                "%'))";
        yearsql = " AND ((publish_year LIKE '%" + year +
                "%') OR (publish_year LIKE '%" + year +
                "') OR (publish_year LIKE '" + year +
                "%'))";

        if(!name.equals("")||!id.equals("")||!publisher.equals("")
                ||!author.equals("")||!isbn.equals("")||!year.equals(""))
            sql += "WHERE book_id = book_id ";
        if(!name.equals("")) sql += namesql;
        if(!id.equals("")) sql += idsql;
        if(!publisher.equals("")) sql += publishersql;
        if(!author.equals("")) sql += authorsql;
        if(!isbn.equals("")) sql += isbnsql;
        if(!year.equals("")) sql += yearsql;

        sql += " group by book_name";
        initTable();





    }
}

