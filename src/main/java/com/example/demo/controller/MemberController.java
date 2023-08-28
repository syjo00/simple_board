package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.service.MemberEntrService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("board")
public class MemberController {

   private MemberEntrService memberEntrService;
  

    @GetMapping({"/entr"})
     public String entr() {
         return "board/entr";
    }




}

    

