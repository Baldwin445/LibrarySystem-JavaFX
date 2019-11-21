package adminUI;

import javafx.beans.property.SimpleStringProperty;

public class AcctMHistoryRecord {
    private SimpleStringProperty opid = new SimpleStringProperty();
    private SimpleStringProperty type = new SimpleStringProperty();
    private SimpleStringProperty acctId = new SimpleStringProperty();
    private SimpleStringProperty adminId = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();

    public AcctMHistoryRecord(String opid,String type,String acctId,
                            String adminId,String time){
        this.opid.set(opid);
        this.type.set(type);
        this.acctId.set(acctId);
        this.adminId.set(adminId);
        this.time.set(time);
    }

    public String getOpid() {
        return opid.get();
    }

    public SimpleStringProperty opidProperty() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid.set(opid);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getAcctId() {
        return acctId.get();
    }

    public SimpleStringProperty acctIdProperty() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId.set(acctId);
    }

    public String getAdminId() {
        return adminId.get();
    }

    public SimpleStringProperty adminIdProperty() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId.set(adminId);
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
}
