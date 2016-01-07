package controller;

import application.SpringBootWebApplicationTests;
import bookshelf.controller.BookShelfController;
import bookshelf.model.Book;
import bookshelf.repository.BookRepository;
import bookshelf.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookShelfControllerTest  extends SpringBootWebApplicationTests{

    @Autowired
    private BookShelfController bookShelfController;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
}
