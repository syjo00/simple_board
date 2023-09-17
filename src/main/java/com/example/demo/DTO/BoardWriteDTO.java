package com.example.demo.DTO;

import lombok.Data;

@Data
public class BoardWriteDTO {
    private String id;
    private String title;
    private String content;
    private String writer;
    private String UserId;
    private String creation_time;
    private String update_time;
}
