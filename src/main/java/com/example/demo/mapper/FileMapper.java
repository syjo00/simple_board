package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.BoardFileDTO;

@Mapper
public interface FileMapper {
  
    //첨부파일 저장
    public void saveFile(BoardFileDTO boardFileDTO);

    
}
