package com.example.demo.controller;

import com.example.demo.dto.RequestDTO;
import com.example.demo.service.GithubScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapperController {

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

}
