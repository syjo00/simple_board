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

@Controller
public class BasicContoller {

    
  private String CLIENT_ID = "4mJ7gqISlayBgvXf6RnU"; //애플리케이션 클라이언트 아이디값";
  private String CLI_SECRET = "ZRLcefd8hi"; //애플리케이션 클라이언트 시크릿값";
      /**
   * 로그인 화면이 있는 페이지 컨트롤
   * @param session
   * @param model
   * @return
   * @throws UnsupportedEncodingException
   * @throws UnknownHostException 
   */
  @RequestMapping("/")
  public String testNaver(HttpSession session, Model model) throws UnsupportedEncodingException, UnknownHostException {
    String redirectURI = URLEncoder.encode("http://localhost:8080/board/list", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s",
        CLIENT_ID, redirectURI, state);
    session.setAttribute("state", state);
    model.addAttribute("apiURL", apiURL);
    return "/index";
  }
    
}
