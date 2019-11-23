package adminUI;

import database.ConnectDB;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BookManageController {
    @FXML
    private AnchorPane pane1, pane2;
    @FXML
    private ImageView change;
    @FXML
    private Button search1, search2, stopB, modify, returnB, delete;
    @FXML
    private TableView<BookManageRecord> table;
    @FXML
    private TableColumn<BookManageRecord, String> id,name,cat,publisher,author,isbn,year,state;
    @FXML
    private TextField key, nameText, idText, publisherText, authorText, isbnText, yearText;

    private int imageValue = 1;
    private ObservableList<BookManageRecord> manageDate = FXCollections.observableArrayList();
    private ResultSet rSet;
    int flag = 1;

    @FXML
    private void initialize(){
        initTable();                                //初始化表格
        switchSearch();                             //切换搜索模式
        showSearchResult();                         //显示搜索结果
        buttonFocusTableContext();                  //按钮监听所选内容
        tableFocusContext();                        //表格自身监听所选内容
//        test();

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
    }

    //不带参数初始化所有书籍数据
    private void initTable(){
        //设置初始状态
        pane2.setVisible(false);
        //设置每列属性
        id.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        name.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        cat.setCellValueFactory(new PropertyValueFactory<>("category"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        //设置表格属性
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String sql = "SELECT book_id, book_name, cat_id, publisher, author, isbn," +
                "publish_year,book_state FROM book_info_table";
        rSet = ConnectDB.search(sql);
        manageDate.clear();
        try{
            while (rSet.next()){
                String stateCN = new String();
                System.out.println(rSet.getString(1));
                if(rSet.getString(8).equals("N")){
                    stateCN = "正常";
                }
                if(rSet.getString(8).equals("B")){
                    stateCN = "已借出";
                }
                if(rSet.getString(8).equals("R")){
                    stateCN = "被预约";
                }
                if(rSet.getString(8).equals("S")){
                    stateCN = "暂停借阅";
                }
                manageDate.addAll(new BookManageRecord(rSet.getString(1),
                        rSet.getString(2), rSet.getString(3),
                        rSet.getString(4), rSet.getString(5),
                        rSet.getString(6), rSet.getString(7), stateCN));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        table.setItems(manageDate);

    }

    /****初始化查询书籍关键字****/
    private void initTable(String bookName){
        String sql = "SELECT book_id,book_name,cat_id,publisher,author,isbn," +
                "publish_year,book_state " +
                "FROM book_info_table " +
                "WHERE (book_name LIKE '%" + bookName + "%') " +
                "OR (book_name LIKE '" + bookName + "%'" +
                "OR (book_name LIKE '%" + bookName + "'))";
        rSet = ConnectDB.search(sql);
        manageDate.clear();
        try{
            while (rSet.next()){
                String stateCN = new String();
                System.out.println(rSet.getString(1));
                if(rSet.getString(8).equals("N")){
                    stateCN = "正常";
                }
                if(rSet.getString(8).equals("B")){
                    stateCN = "已借出";
                }
                if(rSet.getString(8).equals("R")){
                    stateCN = "被预约";
                }
                if(rSet.getString(8).equals("S")){
                    stateCN = "暂停借阅";
                }

                System.out.println(rSet.getString(1)+ rSet.getString(2)+
                        rSet.getString(3)+ rSet.getString(4)+
                        rSet.getString(5)+ rSet.getString(6) +
                        rSet.getString(7)+ stateCN);
                manageDate.addAll(new BookManageRecord(rSet.getString(1),
                        rSet.getString(2), rSet.getString(3),
                        rSet.getString(4), rSet.getString(5),
                        rSet.getString(6), rSet.getString(7), stateCN));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        table.setItems(manageDate);

    }

    /****监听表格所选内容***/
    private void tableFocusContext(){
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookManageRecord>() {
            @Override
            public void changed(ObservableValue<? extends BookManageRecord> observable, BookManageRecord oldValue, BookManageRecord newValue) {
                if(newValue.getState().equals("暂停借阅"))
                    stopB.setText("恢复借阅");
                else stopB.setText("暂停借阅");
            }
        });
    }

    /****按钮监听所选内容 实现响应功能***/
    private void buttonFocusTableContext(){
        //暂停恢复借阅功能
        stopB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeBorrowState(table.getSelectionModel().getSelectedItem().getBookId(),
                        table.getSelectionModel().getSelectedItem().getState());
            }
        });
        //归还图书功能
        returnB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String state = table.getSelectionModel().getSelectedItem().getState();
                String id = table.getSelectionModel().getSelectedItem().getBookId();
                if(state.equals("已借出") || state.equals("被预约"))
                    returnBook(id);
            }
        });
        //删除书籍功能
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteBook(table.getSelectionModel().getSelectedItem().getBookId());

            }
        });
        //修改书籍信息功能
        modify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(flag == 1){
                    setSingleBookInfoToTF();
                    modify.setText("完成");
                    flag++;
                }
                else{
                    setBookInfoToL(table.getSelectionModel().getSelectedItem().getBookId());
                    modify.setText("修改信息");
                    flag--;
                }


            }
        });

    }

    /****修改书籍借书状态，当所选书籍为可借阅时修改为不可借阅，反之同理*****/
    private void changeBorrowState(String id, String state){
        if(!state.equals("暂停借阅")){
            changeStateToStop(id);
            stopB.setText("恢复借阅");
        }
        else{
            changeStateToRecorvery(id);
            stopB.setText("暂停借阅");
        }
    }

    /****暂停图书借阅******/
    private void changeStateToStop(String id){
        int num;
        String sql = "UPDATE book_info_table SET book_state = 'S' " +
                "WHERE book_id = '" + id + "'";
        System.out.println(id);
        try{
            ConnectDB.update(sql);
            table.getSelectionModel().getSelectedItem().setState("暂停借阅");          //修改表格内数据
            System.out.println("修改状态成功");
        }catch (Exception e){
            System.out.println("修改状态失败");
            return;
        }

        try {//添加管理记录
            ResultSet resultSet = ConnectDB.search("select count(1) from bm_record_table");
            resultSet.next();
            num =  Integer.parseInt(resultSet.getString(1)) + 1;

            sql = "INSERT INTO bm_record_table VALUES " +
                    "('P"+num+"','P','"+id+"','"+ Property.getKeyValue("acct") +"',now(),'暂停借阅')";
            ConnectDB.update(sql);
        }catch (Exception e){
            System.out.println("添加记录失败");
            return;
        }


    }
    /****恢复图书借阅*****/
    /****需检测是否存在被预约或被借出的状态，不能直接修改为正常可借出状态*****/
    private void changeStateToRecorvery(String id){
        int flag = 1;       //判断是否有其他非正常状态
        String sql;
        ResultSet resultSet;

        //检测是否被借出
        try{
            sql = "SELECT book_id FROM book_borrow_table WHERE book_id = '"+id+"'";
            resultSet = ConnectDB.search(sql);
            while(resultSet.next()){
                if(!resultSet.getString("book_id").equals("")){
                    table.getSelectionModel().getSelectedItem().setState("已借出");          //修改表格内数据
                    ConnectDB.update("UPDATE book_info_table SET book_state = 'B' " +
                            "WHERE book_id = '" + id + "'");
                    flag = 0;
                }
                break;
            }
        }catch (SQLException e){
            System.out.println("检测book_borrow_table失败.");
        }

        //检测是否被预约
        sql = "SELECT book_id FROM book_order_table WHERE book_id = '"+id+"'";
        resultSet = ConnectDB.search(sql);
        try{
            while (resultSet.next()){
                if(!resultSet.getString("book_id").equals("")){
                    table.getSelectionModel().getSelectedItem().setState("被预约");          //修改表格内数据
                    sql = "UPDATE book_info_table SET book_state = 'R' " +
                            "WHERE book_id = '" + id + "'";
                    ConnectDB.update(sql);
                    flag = 0;
                }
                break;
            }

        }catch (SQLException e){
            System.out.println("检测book_order_table失败.");
        }

        //修改为N
        if(flag == 1){
            ConnectDB.update("UPDATE book_info_table SET book_state = 'N' " +
                    "WHERE book_id = '" + id + "'");
            table.getSelectionModel().getSelectedItem().setState("正常");          //修改表格内数据
        }
        else if(flag != 0){
            System.out.println("全部检测失败");
        }

        try {//添加管理记录
            resultSet = ConnectDB.search("select count(1) from bm_record_table");
            resultSet.next();
            int num =  Integer.parseInt(resultSet.getString(1)) + 1;

            sql = "INSERT INTO bm_record_table VALUES " +
                    "('R"+num+"','R','"+id+"','"+ Property.getKeyValue("acct") +"',now(),'恢复借阅')";
            ConnectDB.update(sql);
        }catch (Exception e){
            System.out.println("添加记录失败");
        }

        return;
    }

    /****归还图书***/
    private void returnBook(String id){
        String acct_id;
        try{//归还图书
            ConnectDB.update("UPDATE book_info_table SET book_state = 'N' " +
                    "WHERE book_id = '" + id + "'");
            table.getSelectionModel().getSelectedItem().setState("正常");
        }catch (Exception e){
            System.out.println("归还失败");
            return;
        }
        /***此处应添加对预约同学的提醒***/
        try{//删除预约表、借阅表对应书籍
            ConnectDB.update("DELETE FROM book_order_table WHERE book_id = '"+id+"'");

            ResultSet rSet = ConnectDB.search("SELECT acct_id FROM book_borrow_table " +
                    "WHERE book_id = '"+id+"'");
            rSet.next();
            acct_id = rSet.getString(1);
            ConnectDB.update("DELETE FROM book_borrow_table WHERE book_id = '"+id+"'");
        }catch (Exception e){
            System.out.println("检查预约表失败");
            acct_id = "";
        }


        int num;
        try{//归还记录
            ResultSet resultSet = ConnectDB.search("select count(1) from br_record_table");
            resultSet.next();
            num =  Integer.parseInt(resultSet.getString(1)) + 1;

            String sql = "INSERT INTO br_record_table VALUES " +
                    "('"+id+"' , '"+acct_id+"' , now() ,'"+ Property.getKeyValue("acct") +"','R"+num+"','R')";
            ConnectDB.update(sql);

        }catch (Exception e){
            System.out.println("添加归还记录失败");
            return;
        }


    }

    /*****删除书籍*****/
    /*****应该添加警示内容进行警告******/
    private void deleteBook(String id){
        try { // 删除书籍
            ConnectDB.update("DELETE FROM book_info_table WHERE book_id = '"+id+"'");
        }catch (Exception e){
            System.out.println("删除书籍失败");
            return;
        }

        int num;
        try {
            ResultSet resultSet = ConnectDB.search("select count(1) from bm_record_table");
            resultSet.next();
            num =  Integer.parseInt(resultSet.getString(1)) + 1;

            String sql = "INSERT INTO bm_record_table VALUES " +
                    "('D"+num+"','D','"+id+"','"+ Property.getKeyValue("acct") +"',now(),'删除书籍')";
            ConnectDB.update(sql);
        }
        catch (Exception e){
            System.out.println("加载记录失败");
            return;
        }
        manageDate.remove(table.getSelectionModel().getSelectedItem());

        return;

    }

    /********修改书籍信息  单行切换为TextField************/
    private void setSingleBookInfoToTF(){
        cat.setCellFactory(new Callback<TableColumn<BookManageRecord, String>, TableCell<BookManageRecord, String>>() {

            @Override
            public TableCell<BookManageRecord, String> call(TableColumn<BookManageRecord, String> param) {

                TableCell<BookManageRecord, String> cell = new TableCell<BookManageRecord, String>(){

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(!empty && item != null){
                            TextField tf = new TextField(item);
                            Label label = new Label(item);

                            HBox hbox = new HBox();
                            hbox.setAlignment(Pos.CENTER);

                            if(table.getSelectionModel().getSelectedIndex() == this.getTableRow().getIndex()){
                                Property.writeProperties("bookCategory", item);
                                hbox.getChildren().add(tf);
                                tf.textProperty().addListener((observable, oldValue, newValue) -> {
                                    Property.writeProperties("newCategory", newValue);
                                });

                                this.setGraphic(hbox);
                            }
                            else {
                                hbox.getChildren().add(label);
                                this.setGraphic(hbox);
                            }

                        }
                    }
                };

                return cell;
            }
        });

    }
    /********修改书籍信息  整列切换为TextField************/
    private void setBookInfoToTF(){
        cat.setCellFactory(new Callback<TableColumn<BookManageRecord, String>, TableCell<BookManageRecord, String>>() {

            @Override
            public TableCell<BookManageRecord, String> call(TableColumn<BookManageRecord, String> param) {

                TableCell<BookManageRecord, String> cell = new TableCell<BookManageRecord, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(!empty && item != null){
                            TextField tf = new TextField(item);

                            HBox hbox = new HBox();
                            hbox.setAlignment(Pos.CENTER);
                            hbox.getChildren().add(tf);

                            this.setGraphic(hbox);

                        }
                    }
                };

                return cell;
            }
        });

    }
    /********修改书籍信息  整列切换为Label************/
    private void setBookInfoToL(String id){
        cat.setCellFactory(new Callback<TableColumn<BookManageRecord, String>, TableCell<BookManageRecord, String>>() {

            @Override
            public TableCell<BookManageRecord, String> call(TableColumn<BookManageRecord, String> param) {

                TableCell<BookManageRecord, String> cell = new TableCell<BookManageRecord, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(!empty && item != null){
                            Label label = new Label(item);

                            HBox hbox = new HBox();
                            hbox.setAlignment(Pos.CENTER);
                            hbox.getChildren().add(label);


                            if(this.getTableRow().getIndex() == table.getSelectionModel().getSelectedIndex()){
                                String newstr = Property.getKeyValue("newCategory");
                                if(newstr.matches("[A-Z][0-9]+")){      //符合要求进行修改

                                    label.setText(newstr);                      //覆盖原label值
                                    this.setGraphic(hbox);
                                    table.getSelectionModel().getSelectedItem().setCategory(newstr);//覆盖原item值

                                    System.out.println("符合要求，可以修改数据库！");
                                    modifyBookInfo(id, newstr);
                                }
                                else{
                                    System.out.println("不符合要求，不允许修改数据库！");
                                    label.setText(Property.getKeyValue("bookCategory"));
                                    this.setGraphic(hbox);
                                }
                                Property.updateProperties("newCategory", "");
                                Property.updateProperties("bookCategory", "");
                            }
                            else this.setGraphic(hbox);



                        }
                    }
                };

                return cell;
            }
        });

    }

    private void modifyBookInfo(String id, String cat){
        try {
            ConnectDB.update("UPDATE book_info_table SET cat_id = '"+cat+"', category = '"+cat.charAt(0)+"'" +
                    "WHERE book_id = '"+id+"'");
        }catch (Exception e){
            System.out.println("修改数据库失败！");

        }

        addBookManageRecord(id, "M", "修改书籍分类ID为："+ cat);

    }


    /*********添加管理记录**********/
    private void addBookManageRecord(String id ,String type, String comment){
        int num;
        try{//归还记录
            ResultSet resultSet = ConnectDB.search("select count(1) from bm_record_table");
            resultSet.next();
            num =  Integer.parseInt(resultSet.getString(1)) + 1;

            String sql = "INSERT INTO bm_record_table VALUES " +
                    "('"+type+num+"','"+type+"','"+id+"','"+Property.getKeyValue("acct")+"',now(),'"+comment+"')";
            ConnectDB.update(sql);

        }catch (Exception e){
            System.out.println("添加记录失败");
            return;
        }

    }

}
