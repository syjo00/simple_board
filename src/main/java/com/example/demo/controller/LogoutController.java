package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.service.LoginService;

@Controller
@RequestMapping("logout")
public class LogoutController {

    LoginService loginService;
    MessageDTO message;
    BasicContoller basicContoller;

    @PostMapping("")
    public String logout(HttpServletRequest request, Model model) {
        // 현재 세션 가져오기 (세션이 없을 경우 생성하지 않음)
        HttpSession session = request.getSession(false);

        System.out.println("로그아웃 세션 session:" + session);

        // 세션이 있을 경우 세션을 무효화시킴
        if (session != null) {
            session.invalidate();
        }

        message = new MessageDTO(" 로그아웃되었습니다.", "/board", RequestMethod.GET, null);

        System.out.println("로그아웃 메세지 message :" + message);

        return basicContoller.showMessageAndRedirect(message, model);
        // 로그아웃 후 리다이렉트할 페이지를 반환합니다. (예: 로그인 페이지)
        // return "redirect:/login"; // 로그인 페이지 경로로 변경해야 할 수 있습니다.
    }
}
