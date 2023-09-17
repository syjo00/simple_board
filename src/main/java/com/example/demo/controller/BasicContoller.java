package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Common;
import com.example.demo.DTO.MessageDTO;

@Controller
public class BasicContoller {

  /**
   * 로그인 화면이 있는 페이지 컨트롤
   * 최초 index파일 실행시 해당 메소드 사용.
   * 네이버 로그인 버튼 클릭시 위 apiURL로 호출 진행.(헤더 정보 client_id : 개인 어플리케이션 발급당시 아이디 ,
   * redirect_uri : 로그인 처리 후 돌아올 URL,
   * state : 네이버에서 요구하는 임의의 상태값. 다들 랜덤으로 하더라구)
   * 
   * @param session
   * @param model
   * @return
   * @throws UnsupportedEncodingException
   * @throws UnknownHostException
   */
  @RequestMapping("/")
  public String testNaver(HttpSession session, Model model) throws UnsupportedEncodingException, UnknownHostException {
    String redirectURI = URLEncoder.encode("http://localhost:8080/entr/naverCallback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s",
        Common.NAVER_CLIENT_ID, redirectURI, state);
    // session.setAttribute("state", state);
    model.addAttribute("naverApiURL", apiURL);
    return "/index";
  }

  /**
   * 처리 후 메시지 출력용.
   * 
   * @param params
   * @param model
   * @return
   */
  public String showMessageAndRedirect(final MessageDTO params, Model model) {
    model.addAttribute("params", params);
    model.addAttribute("message", params.getMessage());

    System.out.println("showMessageAndRedirect + " + model);

    return "fragments/messageRedirect";
  }
}
