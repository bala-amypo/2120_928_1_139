package com.example.demo.service;
import java.util.List;
import com.example.demo.entity.University;


public interface UniversityService {
University save(University u);
University get(Long id);
List<University> getAll();
void delete(Long id);
}