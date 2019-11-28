package adminUI;

import javafx.beans.property.SimpleStringProperty;

public class AddRecord {
    private SimpleStringProperty bookName = new SimpleStringProperty();
    private SimpleStringProperty isbn = new SimpleStringProperty();
    private SimpleStringProperty publisher = new SimpleStringProperty();
    private SimpleStringProperty author = new SimpleStringProperty();
    private SimpleStringProperty bookYear = new SimpleStringProperty();
    private SimpleStringProperty lang = new SimpleStringProperty();
    private SimpleStringProperty Reason = new SimpleStringProperty();

    public AddRecord(String bookName,String isbn,String publisher,String author,
                     String bookYear,String lang, String Reason){
        this.bookName.set(bookName);
        this.author.set(author);
        this.isbn.set(isbn);
        this.publisher.set(publisher);
        this.bookYear.set(bookYear);
        this.lang.set(lang);
        this.Reason.set(Reason);
    }

    public String getBookName() {
        return bookName.get();
    }

    public SimpleStringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public SimpleStringProperty publisherProperty() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
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

    public String getBookYear() {
        return bookYear.get();
    }

    public SimpleStringProperty bookYearProperty() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear.set(bookYear);
    }

    public String getLang() {
        return lang.get();
    }

    public SimpleStringProperty langProperty() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang.set(lang);
    }

    public String getReason() {
        return Reason.get();
    }

    public SimpleStringProperty reasonProperty() {
        return Reason;
    }

    public void setReason(String reason) {
        this.Reason.set(reason);
    }


}
