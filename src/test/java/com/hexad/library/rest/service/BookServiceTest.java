package com.hexad.library.rest.service;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hexad.library.controller.BookController;

@SpringBootTest
@DisplayName("Book rest API test collections")
class BookServiceTest {

	private MockMvc mockMvc;

	@Autowired
	BookController controller;

	String bookJson = "{\n" + "  \"name\": \"BN4\",\n" + "  \"author\": \"BAN4\",\n" + "  \"copies\": 3\n" + "}";

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.controller).build();
	}

	@Test
	@DisplayName("[SUCCESS] Create Book")
	public void createBook() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/create").contextPath("/api")
				.accept(MediaType.APPLICATION_JSON).content(bookJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	@DisplayName("[FAILURE] Create Book, Reason: Book name is missing")
	public void createBookError() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/book/create").contextPath("/api")
				.accept(MediaType.APPLICATION_JSON).content("{}").contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("[SUCCESS] Get all Books")
	public void getAllBooks() throws Exception {
		mockMvc.perform(get("/api/book/all").contextPath("/api").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.*.name", hasItem(is("Patterns of Software"))))
				.andExpect(jsonPath("$.*.name", hasItem(is("The Java Programming Language"))));
	}

}
