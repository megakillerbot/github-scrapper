package com.example.demo.controller;

import com.example.demo.dto.RequestDTO;
import com.example.demo.service.GithubScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ScrapperController {

    @Value("${EXTERNAL_INDEX}")
    private String index;

    @Autowired
    private GithubScrapperService githubScrapperService;

    @PostMapping("/")
    public ResponseEntity<String> repository(@RequestBody RequestDTO request) {
        if(githubScrapperService.validateRepo(request.getRepository())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(githubScrapperService.execute(request.getRepository(), request.isRefresh()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Oops, given repository url is invalid!");
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> index(){
        String html = new RestTemplate().getForObject(index, String.class);
        return ResponseEntity.status(HttpStatus.OK).body(html);
    }


}
