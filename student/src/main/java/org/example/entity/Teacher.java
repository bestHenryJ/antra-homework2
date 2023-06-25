package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, name = "firstname")
    @NotBlank(message = "name is required")
    private String firstName;
    @Column(nullable = false, name = "lastname")
    @NotBlank(message = "name is required")
    private String lastName;
    @Column(name = "classname")
    private String classname;
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<TeacherSudent> teacherSudent = new ArrayList<>();
}
