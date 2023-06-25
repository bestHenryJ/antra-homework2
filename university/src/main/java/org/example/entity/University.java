package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class University implements Serializable {
    private String country;
    @JsonProperty(value = "alpha_two_code")
    private String code;
    private String name;
    @JsonProperty(value = "state-province")
    private String stateProvince;
    private List<String> domains;
    @JsonProperty(value = "web_pages")
    private List<String> webPages;
}
