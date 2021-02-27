package com.jitendra.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jitendra.demo.entity.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

	@Query(value = "SELECT p FROM person p WHERE p.uid = :uid")
	public Optional<PersonEntity> findByUid(String uid);
	
	@Modifying
	@Query(value = "DELETE FROM person p WHERE p.uid = :uid")
	public void deleteByUid(String uid);
}
