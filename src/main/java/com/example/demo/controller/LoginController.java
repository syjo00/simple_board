package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.DTO.MessageDTO;
import com.example.demo.service.LoginService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("login")
public class LoginController {
    LoginService loginService;

    /*
     * 로그인 실행 컨트롤러
     * 1. 로그인 체크
     * 2. 세션 생성
     * 순으로 생성 필요할듯.
     */
    @PostMapping("")
    public String login(MemberDTO memberDTO, Model model, HttpServletRequest request) {
        MessageDTO message;

        //1. 로그인 체크 서비스 호출.
        boolean logInCheck = loginService.logInCheck(memberDTO);

        if(logInCheck){

            MemberDTO sessionMember = loginService.getMember(memberDTO);

            //2. 세션 생성 후 이름 넣기.
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", sessionMember.getMember_no());
            session.setAttribute("name", sessionMember.getName());
            System.out.println( "qjarms568 세션 시간 : " +session.getMaxInactiveInterval());
            
            Map<String,Object> data = new HashMap<>();
            data.put("name", session.getAttribute("name"));
            message = new MessageDTO(sessionMember.getName() + " 로그인성공.", "/", RequestMethod.GET,data);
        }else{
            message = new MessageDTO("로그인실패.", "/", RequestMethod.GET, null);
        }

        return showMessageAndRedirect(message,model);
    }

        private String showMessageAndRedirect(final MessageDTO params, Model model) {
        model.addAttribute("params", params);
        return "fragments/messageRedirect";
    }
}
