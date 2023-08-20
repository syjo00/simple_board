package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {

    @Autowired
    private BoardMapper m;


    public List<BoardDTO> getAllBoard(){

        System.out.println("qjarms568===========================================");

        List<BoardDTO> asdf = m.getAllBoard();

        for (BoardDTO boardDAO : asdf) {
            System.out.println("boardDao = " + boardDAO );
        }


        return asdf;
    }

    public void insertid(){
        m.insertid();
    }
}
