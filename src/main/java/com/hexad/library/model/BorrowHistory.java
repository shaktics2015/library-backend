package com.hexad.library.model;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexad.library.util.CustomerDateAndTimeDeserialize;

@Entity(name = "BorrowHistory")
@Table(name = "borrow_history")
@NamedQuery(name = "BorrowHistory.findAll", query = "SELECT bh FROM BorrowHistory bh ORDER BY userToken ASC")
@NamedQuery(name = "BorrowHistory.findByUserToken", query = "SELECT b FROM BorrowHistory b WHERE b.userToken = :userToken")
public class BorrowHistory extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USER_TOKEN", unique = true)
	@NotNull
    @Size(max = 100)
	private String userToken; 

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RETURNED_DATE", columnDefinition = "DATETIME")
	@JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
	private Date returnedDate;     
 
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            }) 
    @JoinTable(name = "borrow_history_book",
            joinColumns = { @JoinColumn(name = "borrow_history_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") })
    private Set<Book> books = new HashSet<>();
     

	public BorrowHistory() {

	}
	public BorrowHistory(String userToken, Date returnedDate, Set<Book> books) {
		super();
		this.userToken = userToken;
		this.returnedDate = returnedDate;
		this.books = books;
	}

	public BorrowHistory(String userToken, Date returnedDate) {
		super();
		this.userToken = userToken;
		this.returnedDate = returnedDate; 
	}

	public Long getId() {
		return id;
	} 

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books; 
	}
	
	@Override
	public String toString() {
		return "BorrowHistory [id=" + id + ", userToken=" + userToken + ", returnedDate=" + returnedDate + "]";
	} 
 
 
}
