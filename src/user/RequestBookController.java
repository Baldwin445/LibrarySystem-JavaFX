package user;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import properties.Property;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RequestBookController {
    @FXML
    private TableView<RequestBook> requestTable;
    @FXML
    private TableColumn<RequestBook, String> requestIDCol, requestNameCol, requestDateCol;
    @FXML
    private ImageView refreshRequest;

    private Statement stm = ConnectDB.connect();
    private String userID = Property.getKeyValue("ID");

    @FXML
    private void initialize()
    {
        try {
            init();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        onRefresh();
    }
    private void init()throws SQLException {
        ObservableList<RequestBook> requestData = FXCollections.observableArrayList();
        String query = "SELECT book_order_table.book_id, book_name, remain_day FROM `book_order_table`, book_info_table \n" +
                "where book_order_table.book_id = book_info_table.book_id and acct_id = '" + userID + "'";
        ResultSet resultSet = stm.executeQuery(query);
        while (resultSet.next()) {
            requestData.addAll(
                    new RequestBook(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3))
            );
        }
        requestIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        requestNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        requestDateCol.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        requestTable.setItems(requestData);
    }

    private void onRefresh(){
        refreshRequest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    init();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }

            ;
            }
        });
    }
}
