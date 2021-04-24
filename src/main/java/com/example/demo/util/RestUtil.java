package com.example.demo.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestUtil {

    private RestUtil(){}

    public static ResponseEntity<String> get(String url){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }

}
