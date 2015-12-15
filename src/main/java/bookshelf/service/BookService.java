package bookshelf.service;

import bookshelf.model.Book;

public interface BookService {
    Iterable<Book> findAll();

    Book findByIsbn(String isbn);

    Iterable<Book> addBook( String isbn, String name, String author, Double price );

    Book updateBook( String isbn, String name, String author, Double price );

    Iterable<Book> deleteBook( String isbn );
}
