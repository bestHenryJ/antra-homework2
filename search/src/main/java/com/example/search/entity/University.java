package com.example.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class University {
    private String country;
    private String name;
    private String code;
    private List<String> webpages;
}
