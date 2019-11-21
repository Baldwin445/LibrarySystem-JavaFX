package user;

import javafx.beans.property.SimpleStringProperty;

public class BorrowRecord {
    private SimpleStringProperty bookID;
    private SimpleStringProperty bookName;
    private SimpleStringProperty author;
    private SimpleStringProperty borrowDate;
    private SimpleStringProperty returnDate;

    public BorrowRecord(String bookID, String bookName, String author, String borrowDate, String returnDate) {
        this.bookID = new SimpleStringProperty(bookID);
        this.bookName = new SimpleStringProperty(bookName);
        this.borrowDate = new SimpleStringProperty(borrowDate);
        this.returnDate = new SimpleStringProperty(returnDate);
        this.author = new SimpleStringProperty(author);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
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

    public String getBorrowDate() {
        return borrowDate.get();
    }

    public SimpleStringProperty borrowDateProperty() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public SimpleStringProperty returnDateProperty() {
        return returnDate;
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate.set(borrowDate);
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "bookID=" + bookID +
                ", bookName=" + bookName +
                ", author=" + author +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
