package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.StudentDTO;
import org.example.entity.Student;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.StudentDAO;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public StudentDTO getById(long id) {
        log.info("Get info successful");
        Student student = studentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        return studentToDTO(student);
    }

    @Override
    public void addStudent(StudentDTO studentDTO) {
        studentDAO.save(DTOTostudent(studentDTO));
    }

    @Override
    @Transactional
    public void updateStudent(long id, StudentDTO studentDTO) {
        Student student = studentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        log.info("student" + student.getId() + " is exsited and updated");
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setAddress(studentDTO.getAddress());
        studentDAO.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(long id) {
        Student student = studentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        log.info("student " + student.getId() + "is successfully deleted");
        studentDAO.deleteById(student.getId());
    }
    public StudentDTO studentToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setAddress(student.getAddress());
        return dto;
    }
    public Student DTOTostudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setAddress(studentDTO.getAddress());
        return student;
    }
}
