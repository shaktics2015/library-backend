package com.hexad.library.dto;

public class BookDTO { 

	private String name;

	private String author = "SYSTEM_ADMIN";

	private int copies = 5;

	public BookDTO() {
	}
 
	public BookDTO(String name, String author, int copies) {
		super();
		this.name = name;
		this.author = author;
		this.copies = copies;
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

}
