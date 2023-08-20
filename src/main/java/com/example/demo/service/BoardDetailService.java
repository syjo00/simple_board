package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardDetailService {

  @Autowired
    private BoardMapper boardMapper;

    
  public BoardSearchAllDTO getBoardById(String id) {
    
        return boardMapper.getBoardById(id); // boardById 메서드를 호출하도록 수정
    }

}
