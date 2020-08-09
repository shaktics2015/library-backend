package com.hexad.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexad.library.model.BorrowHistory;
import com.hexad.library.repository.BorrowHistoryRepository;

@Service
public class BorrowHistoryService implements IBorrowHistoryService {

	@Autowired
	private BorrowHistoryRepository repository;

	@Override
	public List<BorrowHistory> findAll() {
		return repository.findAll();
	}

	@Override
	public BorrowHistory saveOrUpdate(BorrowHistory borrowHistory) {  
		return repository.save(borrowHistory);
	}

	@Override
	public BorrowHistory findByUserToken(String userToken) {  
		return repository.findByUserToken(userToken);
	}
}
