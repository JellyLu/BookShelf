package bookshelf.controller;

import bookshelf.model.Book;
import bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class BookShelfController {

    @Autowired
    private BookService bookService;

    @Autowired
    public BookShelfController( BookService bookService){
        this.bookService = bookService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Iterable<Book> queryBooks() {

       return bookService.findAll();
    }

    @RequestMapping(value = "book/{isbn}", method = RequestMethod.GET)
    public @ResponseBody Book getBook(@PathVariable String isbn) {

        return bookService.findByIsbn(isbn);
    }

    @RequestMapping(value = "book/new", method = RequestMethod.GET)
    public @ResponseBody Book createBook(){

        return  new Book("","","",0.0);
    }

    @RequestMapping(value = "book", method = RequestMethod.POST)
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

    @RequestMapping(value = "book/edit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody Book updateBook(@RequestBody Book book ) throws Exception {
        if ( book.getIsbn() == null || book.getIsbn().length() == 0 || book.getTitle() == null || book.getTitle().length() == 0 ){
            throw new Exception( "参数不能为空" );
        }
        return bookService.edit( book );
    }

    @RequestMapping(value = "book/delete/{isbn}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody Iterable<Book> deleteBook(@PathVariable String isbn) {

        return bookService.delete( isbn );
    }

    @RequestMapping(value = "books/title/{title}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Iterable<Book> findByTitle(@PathVariable String title) {

        return bookService.findByTitle(title);

    }

    @RequestMapping(value = "books/category/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Iterable<Book> findByCategoryName(@PathVariable String name) {

        return bookService.findByCategoryName(name);

    }
}
