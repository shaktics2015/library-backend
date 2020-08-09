package com.hexad.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexad.library.TestProps;
import com.hexad.library.model.Book;
import com.hexad.library.repository.BookRepository;

class BookServiceTest implements TestProps {
	@Mock
	private BookRepository repository;

	@InjectMocks
	private BookService service;

	private Book book;

	private List<Book> books;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockBook();
	}

	private void mockBook() {
		books = new ArrayList<Book>();
		book = new Book();
		book.setName(BOOK_NAME);
		book.setAuthor(AUTHOR_NAME);
		book.setCopies(3);
		books.add(book);
	}

	@Test
	public void findAll() {
		doReturn(books).when(repository).findAll();
		List<Book> response = service.findAll();
		assertNotNull(response);
		assertEquals(1, response.size());
		assertBook(response.get(0));

	}

	@Test
	public void findById() {
		doReturn(Optional.ofNullable(book)).when(repository).findById(any(Long.class));
		Book response = service.findById(1L);
		assertNotNull(response);
		assertBook(response);
	}

	@Test
	public void findByName() {
		doReturn(book).when(repository).findByName(any(String.class));
		Book response = service.findByName(BOOK_NAME);
		assertNotNull(response);
		assertBook(response);
	}

	@Test
	public void save() {
		doReturn(book).when(repository).save(any(Book.class));
		Book response = service.save(book);
		assertNotNull(response);
		assertBook(response);
	}

	private void assertBook(Book response) {
		assertEquals(book.getName(), response.getName());
	}
}
