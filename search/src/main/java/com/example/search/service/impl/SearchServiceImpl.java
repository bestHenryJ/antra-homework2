package com.example.search.service.impl;

import com.example.search.entity.Student;
import com.example.search.entity.University;
import com.example.search.exception.ResourceNotFoundException;
import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchServiceImpl implements SearchService {

    private final RestTemplate restTemplate;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Object> searchInfoOfStudentAndUniversity(long id, String name) {
        Map<String, Object> map = new HashMap<>();
        CompletableFuture<Void> searchStudent = CompletableFuture.supplyAsync(()->{
            String url = "http://localhost:8200/students/" + id;
            Student student = restTemplate.getForObject(url, Student.class);
            System.out.println(student);
            if (student == null) throw new ResourceNotFoundException("student", "id", String.valueOf(id));
            return student;
        }).thenAccept((result) -> {
            map.put("Student " + id, result);
        }).exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        CompletableFuture<Void> searchUniversity = CompletableFuture.supplyAsync(()-> {
            String url = "http://localhost:8200/university?name=" + name;
            String university = restTemplate.getForObject(url, String.class);
            if (university == null) throw new ResourceNotFoundException("university", "name", name);
            return university;
        }).thenAccept((result)->{
            map.put("university " + name, result);
        }).exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        CompletableFuture<Void> allQuery = CompletableFuture.allOf(searchStudent,searchUniversity);
        CompletableFuture<Map<String, Object>> future = allQuery.thenApply((result) -> {
            return map;
        }).exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        future.join();

        return map;
    }
}
