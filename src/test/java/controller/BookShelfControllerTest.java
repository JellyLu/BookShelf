package controller;

import application.SpringBootWebApplicationTests;
import bookshelf.controller.BookShelfController;
import bookshelf.model.Book;
import bookshelf.model.Category;
import bookshelf.repository.BookRepository;
import bookshelf.repository.CategoryRepository;
import com.google.gson.Gson;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
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

    private Book commonBook;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController).build();
        commonBook = new Book("9780201485677", "Refactoring", "Martin Fowler", 64.57);

    }

    @Test
    public void should_json_array_size_is_7() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray array = (JSONArray)JSONValue.parse(result.getResponse().getContentAsString());
        assertThat( array.size(), is( 7 ));
    }

    @Test
    public void test_find_book_9780201485677() throws Exception {

        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/books/9780201485677"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat( response, is( "{\"isbn\":\"9780201485677\",\"title\":\"Refactoring\",\"author\":\"Martin Fowler\",\"price\":64.57}"));
    }

    @Test
    public void test_new_book() throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/books/new"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat( response, is( "{\"isbn\":\"\",\"title\":\"\",\"author\":\"\",\"price\":0.0}"));
    }

    @Test
    public void test_add_book() throws Exception {
        String requestBody = "{\"isbn\":\"9780132350884\", \"title\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"price\":49.44}";
        mockMvc.perform( MockMvcRequestBuilders.post("/books/")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_edit_book_9780201485677() throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/books/edit/9780201485677"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat( response, is( "{\"isbn\":\"9780201485677\",\"title\":\"Refactoring\",\"author\":\"Martin Fowler\",\"price\":64.57}"));
    }

    @Test
    public void test_update_9780201485677() throws Exception {
        String requestBody = "{\"isbn\":\"9780201485677\", \"title\":\"Refactoring\",\"author\":\"Martin Fowler\",\"price\":32.44}";
        mockMvc.perform( MockMvcRequestBuilders.put( "/books/9780201485677")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    public void test_delete_book() throws Exception {
        bookRepository.save(new Book("9780132350884", "Head First Java", "you", 55.6));
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete/9780132350884"))
                .andExpect(status().isOk());

    }

    @Test
    public void should_find_books_by_title() throws Exception {
        bookRepository.save(asList(
                new Book("12345", "Head First Java", "you", 55.6),
                new Book("45678", "Basic Java Learning", "she", 32.5),
                new Book("89234", "Other Books Basic", "me", 12.5)));

        String titleFuzzyFilter = "Java";


        MvcResult result =  mockMvc.perform(get(format("/books/title/%s", titleFuzzyFilter )))
                .andExpect(status().isOk())
                .andExpect( jsonPath(("$"), hasSize(2)))
                .andReturn();
    }

    @Test
    public void should_search_book_by_category_name() throws Exception {
        Category category = new Category("C123456", "Category Name", "Category Description");
        categoryRepository.save(category);
        Book book1 = new Book("12345", "Hello1", "monkey1", 23.5);
        Book book2 = new Book("22345", "Hello2", "monkey2", 23.5);
        Book book3 = new Book("32345", "Hello3", "monkey3", 23.5);
        book1.setCategoryCode(category.getCode());
        book2.setCategoryCode(category.getCode());
        bookRepository.save(asList(book1, book2, book3));

        mockMvc.perform(get(format("/books/category/%s", category.getName())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$.[1].title").value(book2.getTitle()));

    }

    @Ignore
    @Test
    public void should_add_book_conflict_when_book_already_exists() throws Exception {
        Book existedBook = bookRepository.save(new Book("123456", "Exist Book", "me", 34.3));

        mockMvc.perform( MockMvcRequestBuilders.post("/books/")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(existedBook))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void should_search_book_by_page() throws Exception {

        bookRepository.save(asList(
                new Book("1", "Hello1", "monkey1", 23.5),
                new Book("2", "Hello2", "monkey2", 23.5),
                new Book("3", "Hello3", "monkey3", 23.5),
                new Book("4", "Hello4", "monkey4", 23.5),
                new Book("5", "Hello5", "monkey5", 23.5),
                new Book("6", "Hello6", "monkey6", 23.5),
                new Book("7", "Hello7", "monkey7", 23.5)
        ));

        int pageIndex = (2-1)*3;
        int pageSize = 3;

        mockMvc.perform( MockMvcRequestBuilders.get(format("/books/page/%d/%d", pageIndex, pageSize )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andReturn();

    }
}