package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BoardMapper;



@Service
public class BoardDeleteService {

    @Autowired
    private BoardMapper boardMapper;


    public boolean delete(String board_id) {
        try {
            boardMapper.deleteBoard(board_id);
        } catch (Exception e) {
         
            e.printStackTrace();
           System.out.println("삭제실패");
        }

        return true;
    }
}
