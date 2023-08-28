package com.example.demo.DTO;

import lombok.Data;

@Data
public class BoardUpdateDTO {
    private String id;
    private String title;
    private String content;
    private String writer;
    
}
