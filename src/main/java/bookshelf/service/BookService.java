package bookshelf.service;

import bookshelf.model.Book;

public interface BookService {
    Iterable<Book> findAll();

    Book findByIsbn(String isbn);

    void create( Book book );

    void edit( Book book );

    void delete( String isbn );

    Iterable<Book> findByTitle( String title );

    Iterable<Book> findByCategoryName( String name );
}
