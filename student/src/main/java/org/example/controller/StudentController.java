package org.example.controller;

import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.example.service.TeacherStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final TeacherStudentService teacherStudentService;
    @Autowired
    public StudentController(StudentService studentService, TeacherStudentService teacherStudentService) {
        this.studentService = studentService;
        this.teacherStudentService = teacherStudentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable long id) {
        StudentDTO studentDTO = studentService.getById(id);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addNewStudent(@RequestBody StudentDTO studentDTO) {
        studentService.addStudent(studentDTO);
        return new ResponseEntity<>("create a new student", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable long id, @RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(id, studentDTO);
        return new ResponseEntity<>("Update student " + id +" successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("delete successfully!", HttpStatus.OK);
    }
    @GetMapping("/{id}/teachers")
    public ResponseEntity<List<TeacherDTO>> searchTeachersByStudentId(@PathVariable long id) {
        return new ResponseEntity<>(teacherStudentService.searchTeachersByStudentId(id), HttpStatus.OK);
    }
}
