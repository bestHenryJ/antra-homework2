package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.StudentDAO;
import org.example.repository.TeacherStudentDAO;
import org.example.service.TeacherStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeacherStudentImpl implements TeacherStudentService {
    private final TeacherStudentDAO teacherStudentDAO;
    private final StudentDAO studentDAO;
    @Autowired
    public TeacherStudentImpl(TeacherStudentDAO teacherStudentDAO, StudentDAO studentDAO) {
        this.teacherStudentDAO = teacherStudentDAO;
        this.studentDAO = studentDAO;
    }

    @Override
    @Transactional
    public List<TeacherDTO> searchTeachersByStudentId(long id) {
        Student student = studentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        List<Teacher> teachers = teacherStudentDAO.findTeachersByStudentId(student.getId());
        return teachers.stream().map(teacher -> studentToDTO(teacher)).collect(Collectors.toList());
    }
    public TeacherDTO studentToDTO(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setClassname(teacher.getClassname());
        return dto;
    }
}
