package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.DTO.BoardUpdateDTO;
import com.example.demo.DTO.BoardWriteDTO;
import com.example.demo.service.BoardDeleteService;
import com.example.demo.service.BoardDetailService;
import com.example.demo.service.BoardInsertService;
import com.example.demo.service.BoardSelectService;
import com.example.demo.service.BoardUpdateService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("board")
public class BoardController {
    
   
    private BoardSelectService boardSelectService;
    private BoardInsertService boardInsertService;
    private BoardDeleteService boardDeleteService;
    private BoardDetailService boardDetailService;
    private BoardUpdateService boardUpdateService;

    // 게시판

    // 게시글 목록
    // list 경로로 GET 메서드 요청이 들어올 경우 list 메서드와 맵핑시킴
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.
    
    @GetMapping({"", "/list"})
    public String list(Model model, 
                       @RequestParam(value="page", defaultValue = "1") Integer pageNum,
                       @RequestParam(value="searchType",required = false) String searchType,
                       @RequestParam(value="keyword",required = false) String keyword) {
                       //required = false는 파라미터가 필수가 아니어도 요청처리 가능.
                               
        
        //게시판 전체 조회
        List<BoardSearchAllDTO> boardList;
        //페이징 처리 
        Integer[] pageList = new Integer[10];

        if(searchType!=null && keyword !=null){
            //검색결과 조회
            boardList =  boardSelectService.getSearch(searchType,keyword);
        
        }else{         
            //게시판 전체 조회
            boardList = boardSelectService.getAllBoard();
        }

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }


    @GetMapping("/post")
    public String write() {
        return "board/write";
    }


    

    // 글을 쓴 뒤 POST 메서드로 글 쓴 내용을 DB에 저장
    // 그 후에는 /list 경로로 리디렉션해준다.    
    @PostMapping("/post")
    public String write(BoardWriteDTO boardWriteDTO) {
        boardInsertService.save(boardWriteDTO);
        return "redirect:/board/list";
    }

    // 게시물 삭제는 PostMapping 메서드를 사용하여 간단하게 삭제할 수 있다.
    @PostMapping("/post/{no}")
    public String delete(@PathVariable("no") String no) {
        boardDeleteService.delete(no);

        return "redirect:/board/list";
    }


   
    /*정상작동 */
    // 게시물 수정 페이지이며, {id}로 페이지 넘버를 받는다.   
     
    @GetMapping("/edit/update/{id}")
    public String edit(@PathVariable("id") String id,Model model) throws Exception {

        

        System.out.println("받은 업데이트 데이터: " + id);
            //id를 기반으로 해당 게시물 정보를 가져와서 수정 화면에 뿌려준다는 가정하에...
            //BoardUpdateDTO boardUpdateDTO = new BoardUpdateDTO(); // 또는 해당 DTO 생성 방식으로 초기화
            //boardSearchAllDTO.setId(id); // DTO에 id 설정
            System.out.println("==============수정하기1(BoardController.java)==============");
          
            BoardSearchAllDTO boardDTO = boardDetailService.getBoardById(id);

            model.addAttribute("board", boardDTO);     
            //board라는 이름으로 boardDTO 객체를 모델에 추가함.
            
            System.out.println("board:" + boardDTO);   

            
        return "board/update";
    }
    



    // 위는 GET 메서드이며, PUT 메서드를 이용해 게시물 수정한 부분에 대해 적용

    /*정상작동 버전*/
    @PostMapping("/post/update/{id}")
    //public String resave(BoardWriteDTO boardWriteDTO) {
    public String resave(BoardUpdateDTO boardUpdateDTO) {    
        
        System.out.println("==============수정 후 저장 (BoardController.java)==============");

        System.out.println("boardUpdateDTO : " +boardUpdateDTO);
         boardUpdateService.updateBoard(boardUpdateDTO);
        System.out.println("boardwritedto :" +boardUpdateDTO);
        return "redirect:/board/list";
    }



    
}





