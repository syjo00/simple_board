package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardUpdateDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardUpdateService {
    
 
    @Autowired
    private static BoardMapper boardMapper;
    
       
  

    @Autowired
    public BoardUpdateService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }



    

    /*정상작동 */
   
    public static void updateBoard(BoardUpdateDTO updatedto) {

      boardMapper.updateBoard(updatedto);
    }

    
  }


