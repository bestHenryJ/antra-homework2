package com.example.search.service;

import com.example.search.entity.Student;
import com.example.search.entity.University;

import java.util.Map;

public interface SearchService {
    Map<String, Object> searchInfoOfStudentAndUniversity(long id, String name);
}
