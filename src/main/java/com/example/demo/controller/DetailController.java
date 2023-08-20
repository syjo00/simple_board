package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.service.BoardDetailService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor

public class DetailController {


    @Autowired
    private BoardDetailService BoardDetailService;

    @RequestMapping("/detail/{id}") 
    public String viewPost(@PathVariable String id, Model model) {
    BoardSearchAllDTO boardDTO = BoardDetailService.getBoardById(id); // BoardDetailService의 메서드를 호출
    model.addAttribute("post", boardDTO);
    return "redirect:/board/detail"; 
    }
    
}
