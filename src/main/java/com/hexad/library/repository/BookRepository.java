package com.hexad.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hexad.library.model.Book;

@RepositoryRestResource(exported = false)
public interface BookRepository extends CrudRepository<Book, Long> {
	@Override
	@Query
	List<Book> findAll();
	
	Optional<Book> findById(Long id);

	Book findByName(String name);
}
