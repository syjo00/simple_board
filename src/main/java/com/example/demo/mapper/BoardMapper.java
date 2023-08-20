package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.BoardDTO;

@Mapper
public interface BoardMapper {
    public List<BoardDTO> selectAllBoard();

}
