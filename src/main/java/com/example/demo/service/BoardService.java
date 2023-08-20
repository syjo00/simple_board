package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardDTO> getAllBoard(){
        return boardMapper.selectAllBoard();
    }
}
