package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findByName(String name);
}
