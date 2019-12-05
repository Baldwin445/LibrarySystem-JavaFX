package user;

import database.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import operate.UIOperate;
import properties.Property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

public class PunishManageController {
    @FXML
    private TableView<Punishment> punishTable;
    @FXML
    private TableColumn<Punishment, String>idCol, illegalInfoCol, begTimeCol, hanTimeCol, operateCol;
    @FXML
    private TableColumn<Punishment, Float> rmbCol;

    private String userID = Property.getKeyValue("ID");
    private Statement stm = ConnectDB.connect();

    @FXML
    private void initialize() throws SQLException {
        ObservableList<Punishment> punishData = FXCollections.observableArrayList();
        String query = "SELECT illegal_info, beg_time, han_date, rmb FROM `acct_illegal_table`\n" +
                "WHERE acct_id = '" + userID +"'";
        ResultSet resultSet = stm.executeQuery(query);
//        resultSet.next();
//        String info = resultSet.getString("illegal_info");
        while (resultSet.next()){
            punishData.addAll(new Punishment(userID, resultSet.getString(1), resultSet.getString(2).substring(0,10), resultSet.getString(3).substring(0,10), resultSet.getFloat(4)));
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        illegalInfoCol.setCellValueFactory(new PropertyValueFactory<>("illegalInfo"));
        begTimeCol.setCellValueFactory(new PropertyValueFactory<>("begTime"));
        hanTimeCol.setCellValueFactory(new PropertyValueFactory<>("hanTime"));
        rmbCol.setCellValueFactory(new PropertyValueFactory<>("rmb"));

        operateCol.setCellFactory((col) -> {
            TableCell<Punishment, String> cell = new TableCell<Punishment, String>(){
                Button renewButton = new Button("处理");
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    renewButton.getStyleClass().add("green-theme");
                    if (!empty) {
                        Punishment punishment = getTableView().getItems().get(getIndex());
                        if (punishment.getRmb() == 0){
                            renewButton.setText("已处理");
                        }
                        else {
                            renewButton.setOnMouseClicked((m) -> {
                                System.out.println("Button test");
                                try {
                                    OperatePunish(punishment);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });

                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        setGraphic(renewButton);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });

        punishTable.setItems(punishData);
    }

    public void OperatePunish(Punishment punishment) throws SQLException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "确定处理？");
        confirmation.setHeaderText("处理");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            return;
        }
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        String query = "UPDATE `library`.`acct_illegal_table` SET `han_date` = '%s', `rmb` = 0 WHERE `acct_id` = '%s' AND  `illegal_info` = '%s' ";
        query = String.format(query, time.toString(), userID, punishment.getIllegalInfo());
        stm.executeUpdate(query);

        UIOperate.showAlert(Alert.AlertType.INFORMATION, "提示", "处理成功！");
        initialize();
    }
}
