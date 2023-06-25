package org.example.service;

import org.example.dto.TeacherDTO;

import java.util.List;

public interface TeacherStudentService {
    List<TeacherDTO> searchTeachersByStudentId(long id);
}
