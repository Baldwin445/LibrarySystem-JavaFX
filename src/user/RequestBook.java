package user;

import javafx.beans.property.SimpleStringProperty;

public class RequestBook {
    private SimpleStringProperty bookID;
    private SimpleStringProperty bookName;
    private SimpleStringProperty requestDate;

    public RequestBook(String bookID, String bookName, String requestDate) {
        this.bookID = new SimpleStringProperty(bookID);
        this.bookName = new SimpleStringProperty(bookName);
        this.requestDate = new SimpleStringProperty(requestDate);
    }

    public String getBookID() {
        return bookID.get();
    }

    public SimpleStringProperty bookIDProperty() {
        return bookID;
    }

    public String getBookName() {
        return bookName.get();
    }

    public SimpleStringProperty bookNameProperty() {
        return bookName;
    }

    public String getRequestDate() {
        return requestDate.get();
    }

    public SimpleStringProperty requestDateProperty() {
        return requestDate;
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public void setRequestDate(String requestDate) {
        this.requestDate.set(requestDate);
    }
}
