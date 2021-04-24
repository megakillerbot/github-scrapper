package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesByExtensionDTO {

    private String extension;
    private Long count;
    private Long lines;
    private Long bytes;


    public void addLines(Long lines){
        this.lines += lines;
    }

    public void addBytes(Long bytes){
        this.bytes += bytes;
    }
    public void increment(){
        ++this.count;
    }
}
