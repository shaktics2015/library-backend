package com.hexad.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hexad.library.model.BorrowHistory;

@RepositoryRestResource(exported = false)
public interface BorrowHistoryRepository extends CrudRepository<BorrowHistory, Long> {
	@Override
	@Query
	List<BorrowHistory> findAll();

	BorrowHistory findByUserToken(String userToken);
}
