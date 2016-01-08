package bookshelf.service;

import bookshelf.model.Book;
import bookshelf.model.Category;
import bookshelf.repository.BookRepository;
import bookshelf.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    @Override
    public Iterable<Book> findByCategoryName( String name ){
        Category category = categoryRepository.findByName( name );
        Optional.ofNullable(category).orElseThrow(()->new RuntimeException( "Category can not find" ));
        return bookRepository.findByCategoryCode( category.getCode() );
    }

    @Override
    public  Iterable<Book> findByPage( Pageable pageable ){
        return bookRepository.findAll( pageable );
    }

}
