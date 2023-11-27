package com.kevin.home.domain;

import org.hibernate.annotations.Comment;

import com.kevin.home.master.MasterDomain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_book")
public class Book extends MasterDomain{
	
	@Column (name = "book_name")
	@Comment("Book Name")
	private String bookname; 
	@Column (name = "author_first")
	@Comment("Author First Name")
	private String authorFirstname;
	@Column (name = "author_last")
	@Comment("Author Last Name")
	private String authorLastname;
	@Column (name = "no_pages")
	@Comment("Number of pages")
	private Integer noOfPages;

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getAuthorFirstname() {
		return authorFirstname;
	}

	public void setAuthorFirstname(String authorFirstname) {
		this.authorFirstname = authorFirstname;
	}

	public String getAuthorLastname() {
		return authorLastname;
	}

	public void setAuthorLastname(String authorLastname) {
		this.authorLastname = authorLastname;
	}

	public Integer getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}

}
