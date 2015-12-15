package bookshelf.controller;

import bookshelf.model.Book;
import bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BookShelfController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView queryBooks() {

        ModelMap model = new ModelMap();
        model.put("books", bookService.findAll());
        return new ModelAndView("books", model);

    }

    @RequestMapping(value = "book/{isbn}", method = RequestMethod.GET)
    public ModelAndView getBook(@PathVariable String isbn) {

        ModelMap model = new ModelMap();
        model.put("book", bookService.findByIsbn(isbn));
        return new ModelAndView("book", model);
    }

    @RequestMapping(value = "book/new", method = RequestMethod.GET)
    public ModelAndView createBook(){
        return new ModelAndView( "newBook" , new ModelMap( new Book()) );

    }

    @RequestMapping(value = "book", method = RequestMethod.POST)
    public ModelAndView addBook(@RequestParam("isbn") String isbn, @RequestParam("name") String name,
                                @RequestParam("author") String author, @RequestParam("price") Double price){
        ModelMap modelMap = new ModelMap();
        modelMap.put("books", bookService.addBook( isbn, name, author, price) );
        return new ModelAndView( "books" , modelMap );

    }

    @RequestMapping(value = "book/edit/{isbn}", method = RequestMethod.GET)
    public ModelAndView editBook(@PathVariable String isbn) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("book", bookService.findByIsbn( isbn ));
        return new ModelAndView( "editBook" , modelMap );

    }

    @RequestMapping(value = "book/edit", method = RequestMethod.POST)
    public ModelAndView updateBook(@RequestParam("isbn") String isbn, @RequestParam("name") String name,
                                   @RequestParam("author") String author, @RequestParam("price") Double price) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("book", bookService.updateBook( isbn, name, author, price) );
        return new ModelAndView( "book" , modelMap );

    }

    @RequestMapping(value = "book/delete/{isbn}", method = RequestMethod.GET)
    public ModelAndView deleteBook(@PathVariable String isbn) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("books", bookService.deleteBook( isbn ));
        return new ModelAndView( "books" , modelMap );

    }



}
