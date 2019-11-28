package adminUI;

import javafx.beans.property.SimpleStringProperty;

public class BookManageRecord {
    private SimpleStringProperty bookName = new SimpleStringProperty();
    private SimpleStringProperty bookId = new SimpleStringProperty();
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleStringProperty publisher = new SimpleStringProperty();
    private SimpleStringProperty author = new SimpleStringProperty();
    private SimpleStringProperty isbn = new SimpleStringProperty();
    private SimpleStringProperty year = new SimpleStringProperty();
    private SimpleStringProperty state = new SimpleStringProperty();

    public BookManageRecord(String bookId, String bookName, String category, String publisher,
                            String author, String isbn, String year, String state){
        this.bookName.set(bookName);
        this.bookId.set(bookId);
        this.category.set(category);
        this.publisher.set(publisher);
        this.author.set(author);
        this.isbn.set(isbn);
        this.state.set(state);
        this.year.set(year);

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

    public String getBookId() {
        return bookId.get();
    }

    public SimpleStringProperty bookIdProperty() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId.set(bookId);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public String getIsbn() {
        return isbn.get();
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }
}
