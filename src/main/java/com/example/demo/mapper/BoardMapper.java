package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.BoardSearchAllDTO;

@Mapper
public interface BoardMapper {
    
    // 게시판 전체글 조회.
    public List<BoardSearchAllDTO> selectAllBoard();

}
