package com.hexad.library.service;

import java.util.List;

import com.hexad.library.model.BorrowHistory;

public interface IBorrowHistoryService {
	List<BorrowHistory> findAll();

	BorrowHistory saveOrUpdate(BorrowHistory borrowHistory);

	BorrowHistory findByUserToken(String userToken); 
}
