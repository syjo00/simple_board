package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
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
    
     /*
     * 게시글 검색
     */
     public List<BoardSearchAllDTO> getSearch(@Param("searchType") String searchType, @Param("keyword")  String keyword){

        HashMap<String,String> search= new HashMap<String,String>();

        search.put("searchType",searchType);
        search.put("keyword",keyword);

        System.out.println("=====BoardSelectService.java=====");
        System.out.println("searchType : " +searchType +" keyword : " +keyword);

        return boardMapper.getSearch(search);
    }

    /*
     * 게시글 ID 맥스값 + 1 조회
     */
    public String selectIdMaxPlusOne() {
        String selectIdMaxPlusOne = boardMapper.selectIdMaxPlusOne();
        // 게시글 최초 작성인 경우 1로 시작.
        if (selectIdMaxPlusOne == null) {
            return "1";
        }

        return selectIdMaxPlusOne;
    }
}
