package com.kevin.home.controller;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.home.domain.Book;
import com.kevin.home.repository.BookRepository;
import com.kevin.home.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookRepository bookRes;
	
	@GetMapping (value = "/all")
	public List<Book> getAllBook(){
		return bookRes.findAll();
	}
	
	@GetMapping (value = "{id}")
	public Book getBook(@PathVariable(value = "id") Long id){
		return bookRes.findById(id).get();
	}
	
	@PostMapping("/save")
	public Book saveBook(@RequestBody Book book) {
		return bookRes.save(book);
	}
	
	@PutMapping("/update")
	public Book updateBook(@RequestBody Book book) throws Exception {
		if(book == null || book.getId() == null) {
			throw new Exception("Book or ID must not be null");
		}
		Optional<Book> optionalBook = bookRes.findById(book.getId());
		if(!optionalBook.isPresent()) {
			throw new Exception("Book ID " + book.getId() + " not exits.");
		}
		Book existingBookRecord = optionalBook.get();
		
		BeanUtils.copyProperties(book, existingBookRecord, getNullPropertyNames(book));
		return bookRes.save(book);
	}
	
	@DeleteMapping("/delete/{id}")
	 public void deleteEntity(@PathVariable Long id) {
		bookRes.deleteById(id);;
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
}
