package com.hexad.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexad.library.TestProps;
import com.hexad.library.model.Book;
import com.hexad.library.model.BorrowHistory;
import com.hexad.library.repository.BorrowHistoryRepository;

public class BorrowHistoryServiceTest implements TestProps {

	@Mock
	private BorrowHistoryRepository repository;

	@InjectMocks
	private BorrowHistoryService service;

	private List<BorrowHistory> borrowHistories;

	private BorrowHistory borrowHistory;

	private Book book;

	private Set<Book> books;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockBook();
		mockBorrowHistory();
	}

	private void mockBorrowHistory() {
		borrowHistories = new ArrayList<BorrowHistory>();
		borrowHistory = new BorrowHistory();
		borrowHistory.setUserToken(USER_TOKEN);
		borrowHistory.setBooks(books);
		borrowHistories.add(borrowHistory);
	}

	private void mockBook() {
		books = new HashSet<Book>();
		book = new Book();
		book.setName(BOOK_NAME);
		book.setAuthor(AUTHOR_NAME);
		book.setCopies(3);
		books.add(book);
	}

	@Test
	public void findAll() {
		doReturn(borrowHistories).when(repository).findAll();
		List<BorrowHistory> response = service.findAll();
		assertNotNull(response);
		assertEquals(1, response.size());
		assertBorrowHistory(response.get(0));
	}

	@Test
	public void save() {
		doReturn(borrowHistory).when(repository).save(any(BorrowHistory.class));
		BorrowHistory response = service.saveOrUpdate(borrowHistory);
		assertNotNull(response);
		assertBorrowHistory(response);
	}

	private void assertBorrowHistory(BorrowHistory response) {
		assertEquals(borrowHistory.getUserToken(), response.getUserToken());
		assertEquals(borrowHistory.getBooks(), response.getBooks());
	}
}
