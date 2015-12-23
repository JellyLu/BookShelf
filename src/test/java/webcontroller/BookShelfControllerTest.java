package webcontroller;

import application.SpringBootWebApplicationTests;
import bookshelf.controller.BookShelfController;
import bookshelf.model.Book;
import bookshelf.repository.BookRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookShelfControllerTest extends SpringBootWebApplicationTests{

    @Autowired
    private BookShelfController bookShelfController;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController).build();
    }

    @Test
    public void should_search_book_by_isbn_successfully() throws Exception {
        Book savedBook = bookRepository.save(new Book("book-isbn", "Book Name", "Book Author", 32.52));

        String isbn = savedBook.getIsbn();
        mockMvc.perform(get(format("/books/%s", isbn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.title").value(savedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(savedBook.getAuthor()))
                .andExpect(jsonPath("$.price").value(savedBook.getPrice()));
    }

    @Test
    public void should_be_able_to_add_book_to_shelf() throws Exception {
        Book bookEntity = new Book("1234567890", "Hello World", "sqlin", 54.2);
        String bookEntityJson = new Gson().toJson(bookEntity);

        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookEntityJson))
                .andExpect(status().isCreated());

        Book fetchBook = bookRepository.findOne(bookEntity.getIsbn());
        assertEquals(bookEntity.getIsbn(), fetchBook.getIsbn());
        assertEquals(bookEntity.getTitle(), fetchBook.getTitle());
    }

    @Ignore
    @Test
    public void should_add_book_conflict_when_book_already_exists() throws Exception {
        Book existedBook = bookRepository.save(new Book("123456", "Exist Book", "me", 34.3));

        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(existedBook)))
                .andExpect(status().isConflict());
    }

    @Test
    public void should_search_book_by_title_fuzzily() throws Exception {
        bookRepository.save(asList(
                new Book("12345", "Head First Java", "you", 55.6),
                new Book("45678", "Basic Java Learning", "she", 32.5),
                new Book("89234", "Other Books Basic", "me", 12.5)));

        String titleFuzzyFilter = "Java";

        mockMvc.perform(get(format("/books/title/%s", titleFuzzyFilter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Head First Java"))
                .andExpect(jsonPath("$[1].title").value("Basic Java Learning"));
    }


}
