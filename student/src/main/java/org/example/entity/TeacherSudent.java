package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachersutdent")
public class TeacherSudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "teacher_id")
    private Teacher teacher;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false, name = "student_id")
    private Student student;
}
