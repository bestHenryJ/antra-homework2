package org.example.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.example.dto.UniversityDTO;
import org.example.entity.University;
import org.example.exception.RecourseNotFoundException;
import org.example.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.utils.Constants.UNIVERSITY_API_URL;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final RestTemplate restTemplate;

    @Autowired
    public UniversityServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UniversityDTO> getByCountry(String country) {
        String url = UNIVERSITY_API_URL + "/search?country=" + country;
        ArrayList<University> universityList = restTemplate.getForObject(url, ArrayList.class);
        if (universityList.isEmpty()) {
            throw new RecourseNotFoundException("request info", "country", country);
        }
        return universityList.stream().map(university -> mapToDTO(university)).collect(Collectors.toList());
    }

    @Override
    @HystrixCommand(fallbackMethod = "nameFallBack",
            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "30"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public String getByName(String name) {
        String url = UNIVERSITY_API_URL + "/search?name=" + name;
        List<University> universityList = restTemplate.getForObject(url, List.class);
        if (universityList.isEmpty()) {
            throw new RecourseNotFoundException("request info", "name", name);
        }
        return universityList.toString();
    }
    public UniversityDTO mapToDTO(University university) {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setName(university.getName());
        universityDTO.setCode(university.getCode());
        universityDTO.setCountry(university.getCountry());
        universityDTO.setWebpages(university.getWebPages());
        return universityDTO;
    }
    public String nameFallBack(String name){
        return "The System is busy ! Please try again later";
    }
}
