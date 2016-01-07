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
    public @ResponseBody Iterable<Book> query() {

       return bookService.findAll();
    }

    @RequestMapping(value = "{isbn}", method = RequestMethod.GET)
    public Book get(@PathVariable String isbn) {

        return bookService.findByIsbn(isbn);
    }

    @RequestMapping(value = "book/new", method = RequestMethod.GET)
    public @ResponseBody Book createBook(){

        return  new Book("","","",0.0);
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Iterable<Book> addBook(@RequestBody Book book) throws Exception {

        if ( book.getIsbn() == null || book.getIsbn().length() == 0 || book.getTitle() == null || book.getTitle().length() == 0 ){
            throw new Exception( "参数不能为空" );
        }

        return bookService.create( book );
    }

    @RequestMapping(value = "book/edit/{isbn}", method = RequestMethod.GET)
    public @ResponseBody Book editBook(@PathVariable String isbn) {

        return bookService.findByIsbn( isbn );
    }

    @RequestMapping(value = "{isbn}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody Book updateBook( @PathVariable String isbn, @RequestBody Book book ) throws Exception {
        if ( !isbn.equals( book.getIsbn() ) ){
            throw new Exception( "不能编辑" );
        }
        return bookService.edit( book );
    }

    @RequestMapping(value = "{isbn}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody Iterable<Book> deleteBook(@PathVariable String isbn) {

        return bookService.delete( isbn );
    }

    @RequestMapping(value = "title/{title}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Iterable<Book> findByTitle(@PathVariable String title) {

        return bookService.findByTitle(title);

    }

    @RequestMapping(value = "category/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Iterable<Book> findByCategoryName(@PathVariable String name) {

        return bookService.findByCategoryName(name);

    }
}
