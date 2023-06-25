package com.example.search.controller;

import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(searchService.searchInfoOfStudentAndUniversity(id, name), HttpStatus.OK);
    }
}
