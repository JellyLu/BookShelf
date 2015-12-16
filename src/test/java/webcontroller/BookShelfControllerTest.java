package webcontroller;

import bookshelf.controller.BookShelfController;
import bookshelf.repository.BookRepository;
import bookshelf.service.BookService;
import bookshelf.service.BookServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BookShelfControllerTest {
    private MockMvc mockMvc;
    @Before
    public void setUp() {
        BookService bookService = new BookServiceImpl( new BookRepository() );
        BookShelfController bookShelfController = new BookShelfController( bookService );

        mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController).build();
    }

    @Test
    public void test_find_all_book() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("books"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertNotNull(result.getModelAndView().getModel().get("books"));
    }

    @Test
    public void test_find_book_9780131429017() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/book/9780201485677"))
                .andExpect(MockMvcResultMatchers.view().name("book"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertNotNull(result.getModelAndView().getModel().get("book"));
    }

    @Test
    public void test_new_book() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/book/new"))
                .andExpect(MockMvcResultMatchers.view().name("newBook"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertNotNull(result.getModelAndView().getModel().get("book"));
    }
}
