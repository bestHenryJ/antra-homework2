package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}
