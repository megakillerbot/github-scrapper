package com.example.demo.dto;

import lombok.Data;

@Data
public class FileInfoDTO {

    private String extension;
    private Long bytes;
    private Long lines;

    public FileInfoDTO(String extension, Long bytes, Long lines){
        this.extension = extension;
        this.bytes = bytes;
        this.lines = lines;
    }

}
