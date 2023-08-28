package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardWriteDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardInsertService {

    @Autowired
    private BoardMapper boardMapper;

    /*
     * 조회서비스에서 max+1 세팅 후 insert 실행.
     */
    public void save(BoardWriteDTO boardWriteDTO) {

        boardMapper.saveBoard(boardWriteDTO);
    }
}
