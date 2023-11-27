package com.kevin.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.home.domain.Book;
import com.kevin.home.service.BookService;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping (value = "/books")
	public List<Book> getAllBook(){
		return bookService.getAllBook();
	}
	
	@GetMapping (value = "/book/{id}")
	public Book getBook(@PathVariable(value = "id") Long id){
		return bookService.getBookById(id);
	}
	
	@PostMapping("/book/save")
	public Book createBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}
	
	@PutMapping("/book/update")
	public Book updateBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}
	
	@DeleteMapping("/book/delete/{id}")
	 public void deleteEntity(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
