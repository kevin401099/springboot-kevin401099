package com.kevin.home;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kevin.home.controller.BookController;
import com.kevin.home.domain.Book;
import com.kevin.home.repository.BookRepository;
import com.kevin.home.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
	
	private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	Book RECORD_1 = new Book(1L, "book1", "First1", "Last1", 150);
	Book RECORD_2 = new Book(2L, "book2", "First2", "Last2", 250);
	Book RECORD_3 = new Book(3L, "book3", "First3", "Last3", 350);
	
	@Mock
	private BookRepository bookRes;
	
	@InjectMocks
	private BookController bookController;
	
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }
	
	@Test
    public void getAllBooksSuccess() throws Exception {
		List<Book> records = new ArrayList<Book>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        
        when(bookRes.findAll()).thenReturn(records);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/book/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(records)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
            	.andExpect(jsonPath("$[2].bookname", is("book3")));

        verify(bookRes, times(1)).findAll();
        verifyNoMoreInteractions(bookRes);
    }
	
	@Test
	public void getBookByIdSuccess() throws Exception {
		Mockito.when(bookRes.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/book/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.bookname", is("book1")));
	}
	
	@Test
	public void createBookSuccess() throws Exception {
		Book requestBook = new Book();
		requestBook.setId(4L);
		requestBook.setAuthorFirstname("NewAuthor");
		requestBook.setAuthorLastname("NewLastname");
		requestBook.setBookname("NewBook");
		requestBook.setNoOfPages(150);
		
		Mockito.when(bookRes.save(Mockito.any())).thenReturn(requestBook);
		
		String content = objectWriter.writeValueAsString(requestBook);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book/save")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
    	
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.bookname").value("NewBook"));	}
	
	@Test
	public void updateBookSuccess() throws Exception {
		Book updatebook = new Book(1L, "ubook1", "uFirst1", "uLast1", 150);
    	
    	Mockito.when(bookRes.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
    	Mockito.when(bookRes.save(Mockito.any())).thenReturn(updatebook);
    	String updateContent = objectWriter.writeValueAsString(updatebook);
    	
    	
    	MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book/update")
    		.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(updateContent);
    	
    	mockMvc.perform(mockRequest)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.bookname", is("ubook1")));
	}
	
	@Test
	public void deleteBookByIdSuccess() throws Exception {
		Mockito.lenient().when(bookRes.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
    	
    	mockMvc.perform(MockMvcRequestBuilders
    		.delete("/book/delete/1")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk());
	}
}
