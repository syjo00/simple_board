package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BoardMapper;

@Service
public class BoardDeleteService {

    @Autowired
    private BoardMapper boardMapper;


    public boolean delete(String id) {
        try {
            boardMapper.beleteBoard(id);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;
    }
}
