package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.mapper.BoardMapper;


/*
 * 게시글 조회 관련 서비스
 */
@Service
public class BoardSelectService {

    @Autowired
    private BoardMapper boardMapper;

    /*  
     * 게시판 전체건 조회.
     * TO-DO : 페이징 추가 필요.
     */
    public List<BoardSearchAllDTO> getAllBoard(){
        return boardMapper.selectAllBoard();
    }
}
