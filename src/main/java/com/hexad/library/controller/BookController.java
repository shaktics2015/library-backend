package com.hexad.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexad.library.dto.BookDTO;
import com.hexad.library.model.Book;
import com.hexad.library.service.IBookService;
import com.hexad.library.util.CustomErrorType;
import com.hexad.library.util.StandardValidationHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book")
@CrossOrigin
@Tag(name = "Book", description = "REST APIs")
public class BookController {
	private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private IBookService service;
	
	@Operation(summary = "Get all Books with  >=1 copies")
	@GetMapping("/all")
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = service.findAll();
		if (books.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@Operation(summary = "Add a Book")
	@PostMapping("/create")
	public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO) {

		StandardValidationHelper validationHelper = new StandardValidationHelper();

		validationHelper.objectNotNull("name", bookDTO.getName(), "Name cannot be null.");

		if (validationHelper.hasValidationErrors()) {
			return new ResponseEntity(validationHelper.getValidationErrors(), HttpStatus.BAD_REQUEST);
		}

		Book book = service.findByName(bookDTO.getName());

		if (book != null) {
			LOG.error("Unable to create, duplicate  Book name {}", bookDTO.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create, duplicate Book name " + bookDTO.getName()),
					HttpStatus.CONFLICT);
		}
		book = new Book(bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getCopies());
		service.save(book);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
