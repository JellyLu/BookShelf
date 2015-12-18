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
    public Iterable<Book> addBook( Book book ){
         bookRepository.addBook( book );
         return bookRepository.findAll();
    }

    @Override
    public Book updateBook( Book book ){
         bookRepository.updateOne( book );
         return bookRepository.findOne( book.getIsbn() );
    }

    @Override
    public Iterable<Book> deleteBook( String isbn ){

         bookRepository.deleteBook( isbn );
         return bookRepository.findAll();
    }

}
