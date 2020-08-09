package com.hexad.library.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexad.library.dto.BorrowHistoryDTO;
import com.hexad.library.model.Book;
import com.hexad.library.model.BorrowHistory;
import com.hexad.library.service.IBookService;
import com.hexad.library.service.IBorrowHistoryService;
import com.hexad.library.util.CustomErrorType;
import com.hexad.library.util.StandardValidationHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/borrow")
@CrossOrigin
@Tag(name = "BorrowHistory", description = "REST APIs")
public class BorrowHistoryController {
	private static final Logger LOG = LoggerFactory.getLogger(BorrowHistoryController.class);

	@Autowired
	private IBorrowHistoryService service;

	@Autowired
	private IBookService bookService;
	
	@Operation(summary = "Get Borrow History by userToken")
	@GetMapping("/get/{userToken}")
	public ResponseEntity<BorrowHistory> getBorrowHistoryByUserToken(@PathVariable("userToken") String userToken) {
		BorrowHistory borrowHistory = service.findByUserToken(userToken);
		if (borrowHistory==null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(borrowHistory, HttpStatus.OK);
	}
	@Operation(summary = "Get All Borrow Histories")
	@GetMapping("/all")
	public ResponseEntity<List<BorrowHistory>> getBorrowHistories() {
		List<BorrowHistory> histories = service.findAll();
		if (histories.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(histories, HttpStatus.OK);
	}
	
	@Operation(summary = "Return a Borrowed Book")
	@PutMapping("/return/{userToken}")
	public ResponseEntity<BorrowHistory> updateUser(
			@PathVariable("userToken") String userToken, @Valid @RequestBody Book book) {
		StandardValidationHelper validationHelper = new StandardValidationHelper();

		validationHelper.objectNotNull("userToken", userToken, "User Token cannot be null."); 
		if (validationHelper.hasValidationErrors()) {
			return new ResponseEntity(validationHelper.getValidationErrors(), HttpStatus.BAD_REQUEST);
		}

		BorrowHistory borrowHistory = service.findByUserToken(userToken);
		if(borrowHistory == null) { 
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		if(!borrowHistory.getBooks().contains(book)) {
 			return new ResponseEntity(
					new CustomErrorType("Book is not in borrow list"),
					HttpStatus.BAD_REQUEST);
		}
		borrowHistory.getBooks().remove(book);
		borrowHistory.setReturnedDate(new Date());
	    final BorrowHistory updatedBorrowHistory = service.saveOrUpdate(borrowHistory);
	    book.setCopies(book.getCopies()+1);
	    
	    bookService.save(book);
	    return ResponseEntity.ok(updatedBorrowHistory);
	  }

	@Operation(summary = "Borrow a Book")
	@PostMapping("/add")
	public ResponseEntity<List<Book>> createBorrowHistory(@RequestBody BorrowHistoryDTO borrowHistoryDTO) {

		StandardValidationHelper validationHelper = new StandardValidationHelper();

		validationHelper.objectNotNull("borrow", borrowHistoryDTO, "Blank request not allowed");

		validationHelper.objectNotNull("userToken", borrowHistoryDTO.getUserToken(), "User Token cannot be null."); 

		validationHelper.objectNotNull("bookId", borrowHistoryDTO.getBookId(), "Book selection is mandatory."); 

		if (borrowHistoryDTO.getBookId() != null)
			validationHelper.isNumeric("bookId",  String.valueOf(borrowHistoryDTO.getBookId()));

		if (validationHelper.hasValidationErrors()) {
			return new ResponseEntity(validationHelper.getValidationErrors(), HttpStatus.BAD_REQUEST);
		}

		Book book = bookService.findById(borrowHistoryDTO.getBookId());
		 
		if (book == null) {
 			return new ResponseEntity(
					new CustomErrorType("Book not found, Unable to add book ID: " + borrowHistoryDTO.getBookId() + " to your Borrow list"),
					HttpStatus.NOT_FOUND);
		}

		BorrowHistory borrowHistory = service.findByUserToken(borrowHistoryDTO.getUserToken());
 
		if(borrowHistory!= null && borrowHistory.getBooks().contains(book)) {
 			return new ResponseEntity(
					new CustomErrorType("Book is already in the borrowed list, Unable to add book ID: " + borrowHistoryDTO.getBookId() + " to your Borrow list"),
					HttpStatus.CONFLICT);
		}
		
		if(borrowHistory!= null && borrowHistory.getBooks().size()>1) {
 			return new ResponseEntity(
					new CustomErrorType("Maximum two books/user"),
					HttpStatus.BAD_REQUEST);
		}
		
		if(book.getCopies()==0) {
 			return new ResponseEntity(
					new CustomErrorType(book.getName()+ " is out of stock"),
					HttpStatus.BAD_REQUEST);
		}
		
		if(borrowHistory == null) { 
			borrowHistory=  new BorrowHistory(borrowHistoryDTO.getUserToken(), null);
		}

		Set<Book> books = borrowHistory.getBooks(); 
		books.add(book);
		 

		borrowHistory.setBooks(books); 
	    service.saveOrUpdate(borrowHistory);
	    book.setCopies(book.getCopies()-1);
	    bookService.save(book);
	    return new ResponseEntity(borrowHistory, HttpStatus.CREATED); 
	}

}
