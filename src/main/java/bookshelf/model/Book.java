package bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wo_book")
public class Book {

    @Id
    @Column(name = "isbn", length = 30)
    @NotNull
    private String isbn;
    @Column(name = "title", nullable = false, length = 100)
    private String name;
    @Column(name = "author", nullable = false, length = 30)
    private String author;
    @Column(name = "price", nullable = false)
    private Double price;


    public Book() {

    }

    public Book(String isbn, String name, String author, Double price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getName() {
        return name;
    }


    public String getIsbn() {
        return isbn;
    }


    public String getAuthor() {
        return author;
    }


    public Double getPrice() {
        return price;
    }

}
