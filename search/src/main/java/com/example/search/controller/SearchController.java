package com.example.search.controller;

import com.example.search.entity.Student;
import com.example.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class SearchController {

    private final SearchService searchService;
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/weather/search")
    public ResponseEntity<?> getDetails() {
        //TODO
        return new ResponseEntity<>("this is search service", HttpStatus.OK);
    }

    @GetMapping(value = "/weather/search/info")
    public ResponseEntity<?> getInfoOfStudentAndUniversity (@RequestParam(value = "id") long id, @RequestParam(value = "universityName") String name) {
        Map<String, Object> result = searchService.searchInfoOfStudentAndUniversity(id, name);
        log.info("student info " + result.get(id).toString());
        log.info("university info is " +  result.get(name).toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
