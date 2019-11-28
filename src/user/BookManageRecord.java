package user;

import javafx.beans.property.SimpleStringProperty;

public class BookManageRecord {
    private SimpleStringProperty bookName = new SimpleStringProperty();
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleStringProperty publisher = new SimpleStringProperty();
    private SimpleStringProperty author = new SimpleStringProperty();
    private SimpleStringProperty isbn = new SimpleStringProperty();
    private SimpleStringProperty year = new SimpleStringProperty();
    private SimpleStringProperty book_num = new SimpleStringProperty();

    public BookManageRecord( String bookName, String category, String publisher,
                            String author, String isbn, String year, String state){
        this.bookName.set(bookName);
        this.category.set(category);
        this.publisher.set(publisher);
        this.author.set(author);
        this.isbn.set(isbn);
        this.book_num.set(state);
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

    public String getBook_num() {
        return book_num.get();
    }

    public SimpleStringProperty book_numProperty() {
        return book_num;
    }

    public void setBook_num(String book_num) {
        this.book_num.set(book_num);
    }
}
