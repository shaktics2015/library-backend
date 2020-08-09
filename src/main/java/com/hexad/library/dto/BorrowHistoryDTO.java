package com.hexad.library.dto;

import javax.validation.constraints.NotNull;

public class BorrowHistoryDTO { 
 
	private String userToken;  
 
	private Long bookId;

	public BorrowHistoryDTO(String userToken, @NotNull Long bookId) {
		super();
		this.userToken = userToken;
		this.bookId = bookId; 
	}
 
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	} 

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode()); 
		result = prime * result + ((userToken == null) ? 0 : userToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BorrowHistoryDTO other = (BorrowHistoryDTO) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false; 
		if (userToken == null) {
			if (other.userToken != null)
				return false;
		} else if (!userToken.equals(other.userToken))
			return false;
		return true;
	}

}
