package com.example.demo.DTO;

import lombok.Data;

@Data
public class BoardWriteDTO {
    private int board_id;
    private String title;
    private String content;
    private String writer;
    private int UserId;
    private String creation_time;
    private String update_time;


    public int getBoard_Id() {
        return this.board_id;
    }

    public void setId(int id) {
        this.board_id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return this.writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getUserId() {
        return this.UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getCreation_time() {
        return this.creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}

