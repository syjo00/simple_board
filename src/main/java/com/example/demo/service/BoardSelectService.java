package com.example.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Common;
import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.DTO.BoardSearchDTO;
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
    public List<BoardSearchAllDTO> getAllBoard(Integer pageNum){

        BoardSearchDTO boardSearchDTO = new BoardSearchDTO(pageNum);

        return boardMapper.selectAllBoard(boardSearchDTO);

    }
    
     /*
     * 게시글 검색
     */
     public List<BoardSearchAllDTO> getSearch(@Param("searchType") String searchType, @Param("keyword")  String keyword, Integer pageNum){

        BoardSearchDTO boardSearchDTO = new BoardSearchDTO(pageNum,searchType,keyword);

        System.out.println("=====BoardSelectService.java=====");
        System.out.println("searchType : " +searchType +" keyword : " +keyword);

        return boardMapper.getSearch(boardSearchDTO);
    }

    /*
     * 페이지 몇개인지 체크
     */
    public Integer[] getPageList(String searchType, String keyword) {

        int allPage;
        BoardSearchDTO boardSearchDTO = new BoardSearchDTO(searchType,keyword);

        // 검색조건, 검색어 둘중 하나라도 null일시 전체건으로 체크
        if(searchType == null || keyword == null){
            //전체건 조회
            allPage = boardMapper.countAllBoard();
        }
        else{
             // 검색건 조회.
            allPage = boardMapper.countSearchBoard(boardSearchDTO);
        }

        if((allPage % Common.PAGERECORDSIZE) == 0 ){
            allPage = allPage / Common.PAGERECORDSIZE;
        } else{
            allPage = (allPage / Common.PAGERECORDSIZE) + 1;
        }

        Integer[] result = new Integer[allPage];

        for (int i = 0; i < result.length; i++) {
            result[i] = i + 1;
        }

        
        return result;
    }
}