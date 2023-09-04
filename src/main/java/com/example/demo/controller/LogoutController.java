package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

public class LogoutController {

     @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        return "redirect:/"; // 로그아웃 후 홈페이지로 리다이렉션
    }

    
}
