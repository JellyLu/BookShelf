package bookshelf.service;

import bookshelf.model.Book;

public interface BookService {
    Iterable<Book> findAll();

    Book findByIsbn(String isbn);

    Iterable<Book> addBook( Book book );

    Book updateBook( Book book );

    Iterable<Book> deleteBook( String isbn );
}
