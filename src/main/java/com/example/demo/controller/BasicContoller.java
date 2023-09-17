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
   * 
   * @param session
   * @param model
   * @return
   * @throws UnsupportedEncodingException
   * @throws UnknownHostException
   */
  @RequestMapping("/")
  public String testNaver(HttpSession session, Model model) throws UnsupportedEncodingException, UnknownHostException {
    String redirectURI = URLEncoder.encode("http://localhost:8080/board/naverCallback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s",
        Common.NAVER_CLIENT_ID, redirectURI, state);
    session.setAttribute("state", state);
    model.addAttribute("apiURL", apiURL);
    return "/index";
  }

  @RequestMapping("/board/naverCallback")
  public String naverCallback(HttpSession session, Model model)
      throws UnsupportedEncodingException, UnknownHostException {
    String redirectURI = URLEncoder.encode("http://localhost:8080/board/list", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s",
        Common.NAVER_CLIENT_ID, redirectURI, state);
    session.setAttribute("state", state);
    model.addAttribute("apiURL", apiURL);
    return "/board/list";
  }

  public String showMessageAndRedirect(final MessageDTO params, Model model) {
    model.addAttribute("params", params);
    model.addAttribute("message", params.getMessage());

    System.out.println("showMessageAndRedirect + " + model);

    return "fragments/messageRedirect";
  }

}
