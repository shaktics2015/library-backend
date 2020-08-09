package com.hexad.library.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hexad.library.model.Book;
import com.hexad.library.service.IBookService;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private IBookService bookService;

	public void run(ApplicationArguments args) {
		insertBooks();
	}

	private void insertBooks() { 
		List<Book> books = Arrays.asList(new Book("Patterns of Software", "Richard P. Gabriel", 1), new Book("The Java Programming Language", "James Gosling", 1));
		bookService.saveAll(books);
	}

}