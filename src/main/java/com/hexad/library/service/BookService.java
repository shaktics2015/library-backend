package com.hexad.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexad.library.model.Book;
import com.hexad.library.repository.BookRepository;

@Service
public class BookService implements IBookService {

	@Autowired
	private BookRepository repository;

	@Override
	public List<Book> findAll() {
		return repository.findAll();
	}

	@Override
	public Book save(Book book) {
		return repository.save(book);
	}

	@Override
	public Book findById(Long id) {
		Optional<Book> book = repository.findById(id);
		return book.isPresent() ? repository.findById(id).get() : null;
	}

	@Override
	public Book findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Book> saveAll(List<Book> books) {
		return (List<Book>) repository.saveAll(books);
	}
}
