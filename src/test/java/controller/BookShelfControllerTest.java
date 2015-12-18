package controller;

import bookshelf.controller.BookShelfController;
import bookshelf.repository.BookRepository;
import bookshelf.service.BookService;
import bookshelf.service.BookServiceImpl;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookShelfControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        BookService bookService = new BookServiceImpl( new BookRepository() );
        BookShelfController bookShelfController = new BookShelfController( bookService );

        mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController).build();
    }

    @Test
    public void should_json_array_size_is_3() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                                  .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                  .andExpect(status().isOk())
                                  .andReturn();

        JSONArray array = (JSONArray)JSONValue.parse(result.getResponse().getContentAsString());
        assertThat( array.size(), is( 3 ));
    }

    @Test
    public void test_find_book_9780132350884() throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/book/9780132350884")
                                   .accept(MediaType.APPLICATION_JSON))
                                   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                   .andExpect(status().isOk())
                                   .andExpect(jsonPath("$.name").value("Clean Code"))
                                   .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat( response, is( "{\"isbn\":\"9780132350884\",\"name\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"price\":35.44}"));
    }

    @Test
    public void test_new_book() throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/book/new")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat( response, is( "{\"isbn\":\"\",\"name\":\"\",\"author\":\"\",\"price\":0.0}"));
    }

    @Test
    public void test_add_book() throws Exception {
        String requestBody = "{\"isbn\":\"9780132350875\", \"name\":\"Test Driven Development by Example\",\"author\":\"Kent Beck\",\"price\":49.44}";
        MvcResult result  = mockMvc.perform( MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray array = (JSONArray)JSONValue.parse(result.getResponse().getContentAsString());
        assertThat( array.size(), is( 4 ));
    }

    @Test
    public void test_edit_book_9780132350884() throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/book/edit/9780132350884")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Clean Code"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat( response, is( "{\"isbn\":\"9780132350884\",\"name\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"price\":35.44}"));
    }

    @Test
    public void test_update_9780132350884() throws Exception {
        String requestBody = "{\"isbn\":\"9780132350884\", \"name\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"price\":32.44}";
        mockMvc.perform( MockMvcRequestBuilders.post("/book/edit")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(32.44))
                .andReturn();

    }

    @Test
    public void test_delete_book() throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/book/delete/9780132350884")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray array = (JSONArray)JSONValue.parse(result.getResponse().getContentAsString());
        assertThat( array.size(), is( 2 ));
    }
}
