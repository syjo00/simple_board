package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardUpdateDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardUpdateService {    
 
    @Autowired
    private BoardMapper boardMapper;

      /*정상작동 */

      public boolean updateBoard(BoardUpdateDTO updatedto) {

        try{
          boardMapper.updateBoard(updatedto);
        }catch(Exception e){
          System.out.println(e);
        }

        return true;
      }

    
  }


