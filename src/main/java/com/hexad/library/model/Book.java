package com.hexad.library.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Book")
@Table(name = "book")
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b WHERE b.copies>0 ORDER BY name ASC")
public class Book extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "NO_OF_COPIES")
	private int copies; 
    
	@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "books")
    private Set<BorrowHistory> borrowHistories = new HashSet<>();
	
	public Book() {

	}

	public Book(String name, String author) {
		super();
		this.name = name;
		this.author = author;
	}

	public Book(String name, String author, int copies) {
		super();
		this.name = name;
		this.author = author;
		this.copies = copies;
	}

	public Long getId() {
		return id;
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

	public Set<BorrowHistory> getBorrowHistories() {
		return borrowHistories;
	}

	public void setBorrowHistories(Set<BorrowHistory> borrowHistories) {
		this.borrowHistories = borrowHistories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, author, copies);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (copies != other.copies)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", copies=" + copies + "]";
	}
}
