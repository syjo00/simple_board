package com.example.demo.service;

import java.util.ArrayList;
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
    public ArrayList<String> getPageList(String searchType, String keyword, int pageNum) {

        int allPage;
        BoardSearchDTO boardSearchDTO = new BoardSearchDTO(searchType,keyword);
        // 전체, 조회 나누기.
        if(!Common.STRING_NULL_CHECK(searchType) && !Common.STRING_NULL_CHECK(keyword)){
            //검색건 조회
            allPage = boardMapper.countSearchBoard(boardSearchDTO);
        }
        else{
             //전체건 조회.
            allPage = boardMapper.countAllBoard();
        }

        if((allPage % Common.PAGERECORDSIZE) == 0 ){
            allPage = allPage / Common.PAGERECORDSIZE;
        } else{
            allPage = (allPage / Common.PAGERECORDSIZE) + 1;
        }

        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < allPage; i++) {
            result.add(Integer.toString(i + 1));
        }


        if(pageNum != 1){
            result.add(0, "이전");
        }
        if(pageNum != allPage){
            result.add("다음");
        }

        return result;
    }
}