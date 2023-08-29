package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.DTO.BoardSearchDTO;
import com.example.demo.DTO.BoardUpdateDTO;
import com.example.demo.DTO.BoardWriteDTO;

@Mapper
public interface BoardMapper {
    // 각 메서드의 이름과 인자에 따라서 MyBatis를 통해 적절한 SQL 쿼리를 실행하여 데이터베이스 조작을 수행

    // 게시판 전체글 조회.
    public List<BoardSearchAllDTO> selectAllBoard(BoardSearchDTO boardSearchDTO);

    // 게시판 아이디 시퀀스 조회.
    public String selectIdMaxPlusOne();

    //게시글 저장.
    public void saveBoard(BoardWriteDTO boardWriteDTO);


    //id를 통해 게시물 정보를 조회하는 메서드 추가(상세조회)
    BoardSearchAllDTO getBoardById(String id);
    

    //검색기능 
    List<BoardSearchAllDTO> getSearch(BoardSearchDTO boardSearchDTO);

    // 게시글 삭제.
    public void beleteBoard(String id);

    // 게시글 수정
    public void updateBoard(BoardUpdateDTO updatedto);

    public int countAllBoard(BoardSearchDTO boardSearchDTO);

}
