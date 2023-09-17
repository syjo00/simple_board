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
     * 게시글 정보 (제목, 작성자, 게시글)은 화면에서, userId는 세션에서 생성 후 합치고, insert 실행.
     */
    public boolean save(BoardWriteDTO boardWriteDTO, String userId) {

        boardWriteDTO.setUserId(userId);
        try {
            boardMapper.saveBoard(boardWriteDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
