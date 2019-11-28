package user;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class Punishment {
    private SimpleStringProperty userID;
    private SimpleStringProperty illegalInfo;
    private SimpleStringProperty begTime;
    private SimpleStringProperty hanTime;
    private SimpleFloatProperty rmb;

    public Punishment(String userID, String illegalInfo, String begTime, String hanTime, Float rmb) {
        this.userID = new SimpleStringProperty(userID);
        this.illegalInfo = new SimpleStringProperty(illegalInfo);
        this.begTime = new SimpleStringProperty(begTime);
        this.hanTime = new SimpleStringProperty(hanTime);
        this.rmb = new SimpleFloatProperty(rmb);
    }

    public String getUserID() {
        return userID.get();
    }

    public SimpleStringProperty userIDProperty() {
        return userID;
    }

    public String getIllegalInfo() {
        return illegalInfo.get();
    }

    public SimpleStringProperty illegalInfoProperty() {
        return illegalInfo;
    }

    public String getBegTime() {
        return begTime.get();
    }

    public SimpleStringProperty begTimeProperty() {
        return begTime;
    }

    public String getHanTime() {
        return hanTime.get();
    }

    public SimpleStringProperty hanTimeProperty() {
        return hanTime;
    }

    public float getRmb() {
        return rmb.get();
    }

    public SimpleFloatProperty rmbProperty() {
        return rmb;
    }

    public void setIllegalInfo(String illegalInfo) {
        this.illegalInfo.set(illegalInfo);
    }

    public void setBegTime(String begTime) {
        this.begTime.set(begTime);
    }

    public void setHanTime(String hanTime) {
        this.hanTime.set(hanTime);
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    public void setRmb(float rmb) {
        this.rmb.set(rmb);
    }
}
