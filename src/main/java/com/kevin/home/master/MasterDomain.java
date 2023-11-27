package com.kevin.home.master;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Comment;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class MasterDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
