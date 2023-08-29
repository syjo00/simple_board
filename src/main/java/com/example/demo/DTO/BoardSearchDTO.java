package com.example.demo.DTO;

import com.example.demo.Common;

import lombok.Data;

@Data
public class BoardSearchDTO{
    private int page;
    private int recordSize = Common.PAGERECORDSIZE;
    private String searchType;
    private String keyword;

    public int getPage(){
        return (page - 1) * recordSize;
    }
    public BoardSearchDTO() {
    }


    public BoardSearchDTO(int page, String searchType, String keyword) {
        this(searchType,keyword);
        this.page = page;
    }
    public BoardSearchDTO(String searchType, String keyword) {
        this();
        this.keyword = keyword;
        this.searchType = searchType;
    }
    public BoardSearchDTO(int page) {
        this();
        this.page = page;
    }

    
}
