package com.cleverit.springboottest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleverit.springboottest.model.LicensePlate;

@Repository
public interface LicensePlateRepository extends JpaRepository<LicensePlate, String>  {
	
	
}
