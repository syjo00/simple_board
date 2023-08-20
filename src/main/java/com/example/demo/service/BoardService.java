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

        System.out.println("qjarms568===========================================");

        List<BoardDTO> asdf = boardMapper.selectAllBoard();
        
        return asdf;
    }

    public void insertid(){
        boardMapper.insertid();
    }
}
