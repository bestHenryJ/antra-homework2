package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String classname;
}
