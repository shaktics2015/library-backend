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
		List<Book> books = Arrays.asList(new Book("BN1", "BAN1", 1), new Book("BN2", "BAN2", 2), new Book("BN3", "BAN3", 3), new Book("BN4", "BAN4", 4));
		bookService.saveAll(books);
	}

}