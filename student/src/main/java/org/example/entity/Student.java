package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, name = "firstname")
    @NotBlank(message = "name is required")
    private String firstName;
    @Column(nullable = false, name = "lastname")
    @NotBlank(message = "name is required")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TeacherSudent> studentTeacher = new ArrayList<>();

}
