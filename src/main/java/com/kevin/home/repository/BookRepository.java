package com.kevin.home.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.home.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
}
