package com.hexad.library.service;

import java.util.List;

import com.hexad.library.model.Book;

public interface IBookService {
	List<Book> findAll();

	Book save(Book book);

	List<Book> saveAll(List<Book> books);

	Book findById(Long id);

	Book findByName(String name);
}
