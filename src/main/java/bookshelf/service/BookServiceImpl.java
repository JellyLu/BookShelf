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
    public Iterable<Book> addBook( String isbn, String name, String author, Double price ){
         bookRepository.addBook( new Book( isbn, name, author, price) );
         return bookRepository.findAll();
    }

    @Override
    public Book updateBook(String isbn, String name, String author, Double price){
         bookRepository.updateOne( new Book( isbn, name, author, price) );
         return bookRepository.findOne( isbn );
    }

    @Override
    public Iterable<Book> deleteBook( String isbn ){

         bookRepository.deleteBook( isbn );
         return bookRepository.findAll();
    }

}
