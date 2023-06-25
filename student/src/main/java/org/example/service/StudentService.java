package org.example.service;

import org.example.dto.StudentDTO;
import org.example.entity.Student;

public interface StudentService {
    StudentDTO getById(long id);
    void addStudent(StudentDTO studentDTO);
    void updateStudent(long id, StudentDTO studentDTO);
    void deleteStudent(long id);
}
