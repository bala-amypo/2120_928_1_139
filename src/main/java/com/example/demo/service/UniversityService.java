package com.example.demo.service;

import com.example.demo.entity.University;

import java.util.List;

public interface UniversityService {

    University createUniversity(University university);

    University updateUniversity(Long id, University university);

    University getUniversityById(Long id);

    void deactivateUniversity(Long id);

    List<University> getAll();
}
