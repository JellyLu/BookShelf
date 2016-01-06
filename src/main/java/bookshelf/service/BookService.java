package bookshelf.service;

import bookshelf.model.Book;

public interface BookService {
    Iterable<Book> findAll();

    Book findByIsbn(String isbn);

    Iterable<Book> create( Book book );

    Book edit( Book book );

    Iterable<Book> delete( String isbn );

    Iterable<Book> findByTitle( String title );

    Iterable<Book> findByCategoryName( String name );
}
