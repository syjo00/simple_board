package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.service.BoardDetailService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("board")
public class DetailController {

    private BoardDetailService boardDetailService;

    @Autowired
    public void BoardController(BoardDetailService boardDetailService) {
        this.boardDetailService = boardDetailService;
    }

    @GetMapping("/detail")
    public String viewBoardDetail(@SessionAttribute(name = "userId", required = false) String userId,
            @RequestParam("id") String id, Model model) throws Exception {

        System.out.println("=====================DetailController.java<상세보기>=======================");
        BoardSearchAllDTO boardDTO = boardDetailService.getBoardById(id);

        model.addAttribute("board", boardDTO);
        // board라는 이름으로 boardDTO 객체를 모델에 추가함.
        model.addAttribute("userId", userId);

        System.out.println("model:" + model);
        System.out.println("======================DetailController.java<상세보기 끝> =====================");
        return "board/detail";
    }

}
