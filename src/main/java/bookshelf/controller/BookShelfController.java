package bookshelf.controller;

import bookshelf.model.Book;
import bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody Iterable<Book> addBook(@RequestParam("isbn") String isbn, @RequestParam("name") String name,
                                                @RequestParam("author") String author, @RequestParam("price") Double price) throws Exception {

        if ( isbn == null || isbn.length() == 0 || name == null || name.length() == 0 ){
            throw new Exception( "参数不能为空" );
        }

        return bookService.addBook( isbn, name, author, price);
    }

    @RequestMapping(value = "book/edit/{isbn}", method = RequestMethod.GET)
    public @ResponseBody Book editBook(@PathVariable String isbn) {

        return bookService.findByIsbn( isbn );
    }

    @RequestMapping(value = "book/edit", method = RequestMethod.POST)
    public @ResponseBody Book updateBook(@RequestParam("isbn") String isbn, @RequestParam("name") String name,
                                         @RequestParam("author") String author, @RequestParam("price") Double price) throws Exception {
        if ( isbn == null || isbn.length() == 0 || name == null || name.length() == 0 ){
            throw new Exception( "参数不能为空" );
        }
        return bookService.updateBook( isbn, name, author, price);
    }

    @RequestMapping(value = "book/delete/{isbn}", method = RequestMethod.GET)
    public @ResponseBody Iterable<Book> deleteBook(@PathVariable String isbn) {

        return bookService.deleteBook( isbn );
    }
    
}
