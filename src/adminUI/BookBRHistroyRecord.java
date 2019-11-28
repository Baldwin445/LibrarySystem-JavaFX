package adminUI;

import javafx.beans.property.SimpleStringProperty;

public class BookBRHistroyRecord {
    private SimpleStringProperty opid = new SimpleStringProperty();
    private SimpleStringProperty book = new SimpleStringProperty();
    private SimpleStringProperty acct = new SimpleStringProperty();
    private SimpleStringProperty admin = new SimpleStringProperty();
    private SimpleStringProperty op = new SimpleStringProperty();
    private SimpleStringProperty duetime = new SimpleStringProperty();

    public BookBRHistroyRecord(String opid,String book,String acct,
                               String admin,String op,String duetime){
        this.opid.set(opid);
        this.book.set(book);
        this.acct.set(acct);
        this.admin.set(admin);
        this.op.set(op);
        this.duetime.set(duetime);

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

    public String getBook() {
        return book.get();
    }

    public SimpleStringProperty bookProperty() {
        return book;
    }

    public void setBook(String book) {
        this.book.set(book);
    }

    public String getAcct() {
        return acct.get();
    }

    public SimpleStringProperty acctProperty() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct.set(acct);
    }

    public String getAdmin() {
        return admin.get();
    }

    public SimpleStringProperty adminProperty() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin.set(admin);
    }

    public String getOp() {
        return op.get();
    }

    public SimpleStringProperty opProperty() {
        return op;
    }

    public void setOp(String op) {
        this.op.set(op);
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
}
