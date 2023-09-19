package com.example.demo.DTO;

import lombok.Data;


@Data
public class BoardFileDTO {
    private int id; //파일 id 
    private String uuid; //파일 고유 번호
    private int board_id; //게시글 번호
    private String file_name; //파일명
    private String creation_time; //날짜
    private String creator;
    private String update_time;
    private String updater;

}

    
