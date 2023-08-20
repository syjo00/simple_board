package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.service.BoardService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("board")
public class BoardController {
    private BoardService boardService;

    // 게시판

    // 게시글 목록
    // list 경로로 GET 메서드 요청이 들어올 경우 list 메서드와 맵핑시킴
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.
    
    @GetMapping({"", "/list"})
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardSearchAllDTO> boardList = boardService.getAllBoard();
        Integer[] pageList = new Integer[10];

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }
}
