package service;


import bookshelf.model.Book;
import bookshelf.repository.BookRepository;
import bookshelf.service.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class BookServiceImplTest {

    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        bookRepository = new BookRepository();
        bookService = new BookServiceImpl( bookRepository );
    }

    @Test
    public void should_return_all_books(){
        Iterable<Book> books = bookService.findAll();

        for ( Book book: books) {
            assertThat( book, is( bookRepository.BOOKS_MAP.get( book.getIsbn() ) ));
        }

    }

    @Test
    public void should_find_by_isbn_9780201485677(){
        Book book = bookService.findByIsbn( "9780201485677" );

            assertThat( book.getName(), is( "Refactoring" ));
    }

    @Test
    public void should_not_find_by_isbn(){
        Book book = bookService.findByIsbn( "9780132350886" );

        assertNull( book );
    }

    @Test
    public void should_return_4_books_when_add_one(){

        Iterable<Book> books = bookService.addBook( "9780132350555", "Test Driven Development by Example", "Kent Beck",  30.32 );

        for ( Book book: books) {
            assertThat( book, is( bookRepository.BOOKS_MAP.get( book.getIsbn() ) ));
        }
    }

    @Test
    public void should_return_new_book_when_update_9780132350884(){

        Book book = bookService.updateBook( "9780132350884", "Effective Java 2", "Joshua Bloch",  35.99 );

        assertThat( book.getName(), is( "Effective Java 2" ));
    }

    @Test
    public void should_return_2_books_when_delete_9780132350884(){

        Iterable<Book> books = bookService.deleteBook( "9780132350884" );

        for ( Book book: books) {
            assertThat( book, is( bookRepository.BOOKS_MAP.get( book.getIsbn() ) ));
        }
    }

}
