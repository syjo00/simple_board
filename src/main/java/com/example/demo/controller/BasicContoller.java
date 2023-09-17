package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

  /*
   * 네이버 로그인 콜백처리.
   * 1. 네이버 로그인 api 방식
   *    a. 네이버 로그인 버튼 클릭시 위 apiURL로 호출 진행. 
   *      (헤더 정보 client_id : 개인 어플리케이션 발급당시 아이디 , redirect_uri : 로그인 처리 후 돌아올 URL, state : 네이버에서 요구하는 임의의 상태값. 다들 랜덤으로 하더라구) 
   *    b. a의 url에서 네이버 로그인 진행 후 콜백시 제공 정보엔 두 토큰 값 제공 (access_token, refresh_token )
   *    c. 해당 코드값 + 어플리케이션 발급당시 아이디, 비번등으로 토큰발급 api 발송. (네이버에선 access_token, Authorization, code등으로 표현. 같은 의미인듯.)
   *    d. 발급 받은 토큰으로 회원 프로필 정보 조회 api 호출.
   */
  @RequestMapping("/entr/naverCallback")
  public String naverCallback1(HttpSession session, HttpServletRequest request, Model model)
      throws IOException, ParseException {
    System.out.println("들어왔냐?");
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String redirectURI = URLEncoder.encode("http://localhost:8080/entr/naverCallback", "UTF-8");
    String apiURL;
    /* c. 토큰 발급 url (참고로 해당 url에 grant_type 변경으로 토큰 갱신, 삭제도 가능.
                        1. 발급:'authorization_code'
                        2. 갱신:'refresh_token'
                        3. 삭제: 'delete')
    */
    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
    apiURL += "client_id=" + Common.NAVER_CLIENT_ID;
    apiURL += "&client_secret=" + Common.NAVER_CLIENT_SECRET;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&code=" + code;
    apiURL += "&state=" + state;
    // 해더정보는 필요 없어서 2번째 껀 빈값으로. 이후 값은 JSON 형태로 String 형으로 주는거 같음. 아님말구.
    String res = requestToServer(apiURL, "");
    if (!Common.STRING_NULL_CHECK(res)) {
      JSONObject parsedJson = (JSONObject)new JSONParser().parse(res);
      System.out.println(parsedJson);
      // 세션에 담을 필욘 없어보임.
      String token = (String)parsedJson.get("access_token");
      /*
      session.setAttribute("currentUser", res);
      session.setAttribute("currentAT", parsedJson.get("access_token"));
      session.setAttribute("currentRT", parsedJson.get("refresh_token"));
      */
      apiURL = null;
      res = null;
      parsedJson.clear();
      // 회원 프로필 정보조회 api 호출.
      apiURL = "https://openapi.naver.com/v1/nid/me";
      String headerStr = "Bearer " + token; // 헤더에 토큰정보 입력, Bearer 다음에 공백 추가
      res = requestToServer(apiURL, headerStr);
      // 동일하게 JSON 형태의 string 이나 json 안에 json형태로 프로필이 들어가 있어 뺴줘야함.
      // {"resultcode":"00","message":"success","response":{"id":"Ua78UAvcQXGAUW81BAlru5AKFGasfF6jMj5TyUOmWbI","email":"qjarms568@naver.com","name":"\ubc15\ubc94\uadfc","birthday":"08-14"}}
      JSONObject jsonObject = (JSONObject)new JSONParser().parse(res);
      JSONObject jsonObject2 = (JSONObject)jsonObject.get("response");
      
      session.setAttribute("userId", jsonObject2.get("id"));
      session.setAttribute("name", jsonObject2.get("name"));
      
    } else {
      model.addAttribute("res", "Login failed!");
    }
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

  /**
   * 서버 통신 메소드
   * 잘 몰라서 복붙함.
   * @param apiURL
   * @param headerStr
   * @return
   * @throws IOException
   */
  private String requestToServer(String apiURL, String headerStr) throws IOException {
    URL url = new URL(apiURL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    System.out.println("header Str: " + headerStr);
    if (headerStr != null && !headerStr.equals("")) {
      con.setRequestProperty("Authorization", headerStr);
    }
    int responseCode = con.getResponseCode();
    BufferedReader br;
    System.out.println("responseCode=" + responseCode);
    if (responseCode == 200) { // 정상 호출
      br = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else { // 에러 발생
      br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    String inputLine;
    StringBuffer res = new StringBuffer();
    while ((inputLine = br.readLine()) != null) {
      res.append(inputLine);
    }
    br.close();
    if (responseCode == 200) {
      return res.toString();
    } else {
      return null;
    }
  }

}
