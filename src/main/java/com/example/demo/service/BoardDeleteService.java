package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BoardMapper;

@Service
public class BoardDeleteService {

    @Autowired
    private BoardMapper boardMapper;


    public void delete(String id) {
        boardMapper.beleteBoard(id);
    }
}
