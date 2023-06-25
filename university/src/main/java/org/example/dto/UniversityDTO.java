package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDTO {
    private String country;
    private String name;
    private String code;
    private List<String> webpages;
}
