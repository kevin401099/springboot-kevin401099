package com.kevin.home.service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.home.domain.Book;
import com.kevin.home.master.MasterDomain;
import com.kevin.home.repository.BookRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRes;

	@Override
	public List<Book> getAllBook() {
		return bookRes.findAll();
	}

	@Override
	public Book saveBook(Book book) {
		return bookRes.save(book);
	}

	@Override
	public Book getBookById(Long id) {
		return bookRes.findById(id).get();
	}
	
	@Override
	public Book updateBook(Long id, Book book) {
		Book bookSave = bookRes.findById(id).orElseThrow();
		BeanUtils.copyProperties(book, bookSave, getNullPropertyNames(book));
		return bookRes.save(bookSave);
	}
	
	private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            // Check if the property value is null
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

	@Override
	public void deleteBook(Long id) {
		bookRes.deleteById(id);
	}
}
