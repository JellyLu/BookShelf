package bookshelf.controller;

import bookshelf.model.Book;
import bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
public class BookShelfController {

    @Autowired
    private BookService bookService;

    @Autowired
    public BookShelfController( BookService bookService){
        this.bookService = bookService;
    }

    @RequestMapping( method = RequestMethod.GET)
    public  Iterable<Book> queryBooks() {

       return bookService.findAll();
    }

    @RequestMapping(value = "{isbn}", method = RequestMethod.GET)
    public Book getBook(@PathVariable String isbn) {

        return bookService.findByIsbn(isbn);
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public  Book createBook(){

        return  new Book("","","",0.0);
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody Book book) throws Exception {

        if ( book.getIsbn() == null || book.getIsbn().length() == 0 || book.getTitle() == null || book.getTitle().length() == 0 ){
            throw new Exception( "参数不能为空" );
        }

        bookService.create( book );
    }

    @RequestMapping(value = "edit/{isbn}", method = RequestMethod.GET)
    public Book editBook(@PathVariable String isbn) {

      return   bookService.findByIsbn( isbn );
    }

    @RequestMapping(value = "{isbn}", method = RequestMethod.PUT)
  //  @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable String isbn, @RequestBody Book book ) throws Exception {
        if ( !book.getIsbn().equals(isbn) ){
            throw new Exception( "参数不正确" );
        }
         bookService.edit( book );
    }

    @RequestMapping(value = "delete/{isbn}", method = RequestMethod.DELETE)
  //  @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteBook(@PathVariable String isbn) {

        bookService.delete( isbn );
    }

    @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
    public Iterable<Book> queryByTitle(@PathVariable String title) {

        return bookService.findByTitle(title);

    }

    @RequestMapping(value = "/category/{categoryName}", method = RequestMethod.GET)
    public Iterable<Book> findByCategoryName(@PathVariable String categoryName) {

        return bookService.findByCategoryName(categoryName);

    }
}
