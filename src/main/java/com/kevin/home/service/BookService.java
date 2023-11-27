package com.kevin.home.service;

import java.util.List;

import com.kevin.home.domain.Book;

public interface BookService {
	public List<Book> getAllBook();
	public Book saveBook(Book book);
	public Book getBookById(Long id);
	public Book updateBook(Long id, Book book);
	public void deleteBook(Long id);
}
