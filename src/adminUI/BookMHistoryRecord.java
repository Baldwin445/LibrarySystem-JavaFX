package adminUI;

import javafx.beans.property.SimpleStringProperty;

public class BookMHistoryRecord {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty type = new SimpleStringProperty();
    private SimpleStringProperty bookId = new SimpleStringProperty();
    private SimpleStringProperty adminId = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty notes = new SimpleStringProperty();

    public BookMHistoryRecord(String id,String type,String bookId,
                              String adminId,String time,String notes){
        this.id.set(id);
        this.type.set(type);
        this.bookId.set(bookId);
        this.adminId.set(adminId);
        this.time.set(time);
        this.notes.set(notes);
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

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getBookId() {
        return bookId.get();
    }

    public SimpleStringProperty bookIdProperty() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId.set(bookId);
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

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
