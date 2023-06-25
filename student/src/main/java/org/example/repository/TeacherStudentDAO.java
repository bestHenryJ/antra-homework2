package org.example.repository;

import org.example.entity.Teacher;
import org.example.entity.TeacherSudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherStudentDAO extends JpaRepository<TeacherSudent, Long> {
    @Query("select ts.teacher from TeacherSudent ts where ts.student.id = ?1")
    List<Teacher> findTeachersByStudentId(long id);
}
