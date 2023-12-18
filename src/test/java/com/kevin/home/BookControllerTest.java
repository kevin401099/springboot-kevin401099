package com.kevin.home;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
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
import com.kevin.home.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
	
	
	
	@Mock
	private BookService bookService;
	
	@InjectMocks
	private BookController bookController;
	
	@Test
    public void getAllBooksSuccess() throws Exception {
		
        List<Book> books = new ArrayList<>();
        for(int i = 1; i <= 3; i++) {
        	Book book = new Book();
        	book.setId(Long.valueOf(i));
        	book.setBookname("book" + i);
        	book.setAuthorFirstname("first" + i);
        	book.setAuthorLastname("last" + i);
        	book.setNoOfPages(i * 100);
        	books.add(book);
        }
        
        when(bookService.getAllBook()).thenReturn(books);
        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(books)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
            	.andExpect(jsonPath("$[2].bookname", is("book3")));

        verify(bookService, times(1)).getAllBook();
        verifyNoMoreInteractions(bookService);
    }
	
	@Test
	public void getBoodByIdSuccess() throws Exception {
		Book book = new Book();
    	book.setId(Long.valueOf(1));
    	book.setBookname("book");
    	book.setAuthorFirstname("first");
    	book.setAuthorLastname("last");
    	book.setNoOfPages(100);
    	
    	
		Mockito.when(bookService.getBookById(book.getId())).thenReturn(book);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/book/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.bookname", is("book")));
	}
	
	@Test
	public void createBookSuccess() throws Exception {
		Book book = new Book();
    	book.setId(Long.valueOf(1));
    	book.setBookname("book");
    	book.setAuthorFirstname("first");
    	book.setAuthorLastname("last");
    	book.setNoOfPages(100);
    	
    	Mockito.when(bookService.saveBook(book)).thenReturn(book);
    	ObjectMapper objectMapper = new ObjectMapper();
    	ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    	String content = objectWriter.writeValueAsString(book);
    	System.out.println(content);
    	
    	MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
    		.contentType(MediaType.APPLICATION_JSON)
    		.accept(MediaType.APPLICATION_JSON)
    		.content(content);
    	
    	MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    	mockMvc.perform(mockRequest)
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.bookname", is("book")));
	}
}
