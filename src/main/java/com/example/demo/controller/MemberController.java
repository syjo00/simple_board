package com.example.demo.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Common;
import com.example.demo.DTO.MemberDTO;
import com.example.demo.DTO.MessageDTO;
import com.example.demo.service.MemberEntrService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("entr")
public class MemberController {

    private MemberEntrService memberEntrService;
    BasicContoller basicContoller;

    @GetMapping({ "/entr" })
    public String entr() {
        return "entr/entr";
    }

    @PostMapping("/entr")
    public String write(MemberDTO MemberDTO, Model model) {

        MessageDTO message;

        if (!isMemberDataValid(MemberDTO)) {

            if (memberEntrService.entr(MemberDTO)) {
                message = new MessageDTO(Common.SAVESUCCES01, "/", RequestMethod.GET, null);
            } else {

                message = new MessageDTO(Common.FAIL01, "/", RequestMethod.GET, null);
            }

        } else {

            message = new MessageDTO(Common.MEMBERINFO, "/entr/entr", RequestMethod.GET, null);

        }

        return basicContoller.showMessageAndRedirect(message, model);

    }// write();

    /*
     * 네이버 로그인 콜백처리.
     * a. 네이버 로그인 진행 후 콜백시 제공 정보엔 코드값 제공 (code)
     * b. 해당 코드값 + 어플리케이션 발급당시 아이디, 비번등으로 토큰발급 api 발송. (네이버에선 access_token,
     * Authorization, 등으로 표현. 같은 의미인듯.)
     * c. 발급 받은 토큰으로 회원 프로필 정보 조회 api 호출.
     */
    @RequestMapping("/naverCallback")
    public String naverCallback1(HttpSession session, HttpServletRequest request, Model model)
            throws IOException, ParseException {
        System.out.println("로그인 콜백 들어왔냐?");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String redirectURI = URLEncoder.encode("http://localhost:8080/entr/naverCallback", "UTF-8");
        String apiURL;
        /*
         * b. 토큰 발급 url (참고로 해당 url에 grant_type 변경으로 토큰 갱신, 삭제도 가능.
         * 1. 발급:'authorization_code'
         * 2. 갱신:'refresh_token'
         * 3. 삭제: 'delete')
         */
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + Common.NAVER_CLIENT_ID;
        apiURL += "&client_secret=" + Common.NAVER_CLIENT_SECRET;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        // 해더정보는 필요 없어서 2번째 껀 빈값으로. 이후 값은 JSON 형태로 String 형으로 주는거 같음. 아님말구.
        String res = Common.requestToServer(apiURL, "");
        if (!Common.STRING_NULL_CHECK(res)) {
            JSONObject parsedJson = (JSONObject) new JSONParser().parse(res);
            // 세션에 담을 필욘 없어보임.
            String token = (String) parsedJson.get("access_token");
            /*
             * session.setAttribute("currentUser", res);
             * session.setAttribute("currentAT", parsedJson.get("access_token"));
             * session.setAttribute("currentRT", parsedJson.get("refresh_token"));
             */

            // 최기화 후 다음 api 호출 진행.
            apiURL = null;
            res = null;
            // 회원 프로필 정보조회 api 호출.
            apiURL = "https://openapi.naver.com/v1/nid/me";
            String headerStr = "Bearer " + token; // 헤더에 토큰정보 입력, Bearer 다음에 공백 추가
            res = Common.requestToServer(apiURL, headerStr);
            // 동일하게 JSON 형태의 string 이나 json 안에 json형태로 프로필이 들어가 있어 뺴줘야함.
            // {"resultcode":"00","message":"success","response":{"id":"Ua78UAvcQXGAUW81BAlru5AKFGasfF6jMj5TyUOmWbI","email":"qjarms568@naver.com","name":"\ubc15\ubc94\uadfc","birthday":"08-14"}}
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(res);
            JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");

            session.setAttribute("userId", jsonObject2.get("id"));
            session.setAttribute("name", jsonObject2.get("name"));

        } else {
            model.addAttribute("res", "Login failed!");
        }
        return "/index";
    }

    private boolean isMemberDataValid(MemberDTO memberDTO) {

        return (memberDTO.getName() == "" ||
                memberDTO.getPw() == "" ||
                memberDTO.getEmail() == "");

    }
}// Controller
