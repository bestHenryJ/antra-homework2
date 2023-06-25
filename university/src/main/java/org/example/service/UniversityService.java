package org.example.service;

import org.example.dto.UniversityDTO;

import java.util.List;

public interface UniversityService {
    List<UniversityDTO> getByCountry(String country);
    String getByName(String name);
}
