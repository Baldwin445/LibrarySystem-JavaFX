package adminUI;

import javafx.beans.property.SimpleStringProperty;

public class ExceptionInfoRecord {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty adminid = new SimpleStringProperty();
    private SimpleStringProperty info = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty duetime = new SimpleStringProperty();
    private SimpleStringProperty money = new SimpleStringProperty();

    public ExceptionInfoRecord(String id,String name,String adminid,
                               String info,String time,String duetime,
                               String money){
        this.id.set(id);
        this.name.set(name);
        this.adminid.set(adminid);
        this.info.set(info);
        this.time.set(time);
        this.duetime.set(duetime);
        this.money.set(money);

    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAdminid() {
        return adminid.get();
    }

    public SimpleStringProperty adminidProperty() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid.set(adminid);
    }

    public String getInfo() {
        return info.get();
    }

    public SimpleStringProperty infoProperty() {
        return info;
    }

    public void setInfo(String info) {
        this.info.set(info);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getDuetime() {
        return duetime.get();
    }

    public SimpleStringProperty duetimeProperty() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime.set(duetime);
    }

    public String getMoney() {
        return money.get();
    }

    public SimpleStringProperty moneyProperty() {
        return money;
    }

    public void setMoney(String money) {
        this.money.set(money);
    }
}
