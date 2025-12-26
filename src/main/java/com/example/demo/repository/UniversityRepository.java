package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.example.demo.entity.*;

public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByName(String name);
}
