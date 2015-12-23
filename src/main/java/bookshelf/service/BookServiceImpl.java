package bookshelf.service;

import bookshelf.model.Book;
import bookshelf.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findOne(isbn);
    }

    @Override
    public Iterable<Book> create( Book book ){
         bookRepository.save( book );
         return bookRepository.findAll();
    }

    @Override
    public Book edit( Book book ){
         bookRepository.save( book );
         return bookRepository.findOne( book.getIsbn() );
    }

    @Override
    public Iterable<Book> delete( String isbn ){

         bookRepository.delete( isbn );
         return bookRepository.findAll();
    }

    @Override
    public Iterable<Book> findByTitle( String title ){

        return bookRepository.findByTitle( title );
    }

}
