package org.example.controller;

import org.example.dto.UniversityDTO;
import org.example.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService universityService;
    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping(params = "name")
    public ResponseEntity<String> searchByName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(universityService.getByName(name), HttpStatus.OK);
    }
    @GetMapping(params = "country")
    public ResponseEntity<List<UniversityDTO>> searchByCountry(@RequestParam(value = "country") String country) {
        return new ResponseEntity<>(universityService.getByCountry(country), HttpStatus.OK);
    }
}
