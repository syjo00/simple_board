package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//공통 제어할꺼들.
public class Common {
    // 페이지 갯수.
    public static final int PAGERECORDSIZE = 5;

    // 메시지 문구 정리
    public static final String SAVESUCCES01 = "저장되었습니다.";
    public static final String DELETESUCCES01 = "삭제되었습니다.";
    public static final String UPDATESUCCESEE01 = "수정되었습니다.";
    public static final String FAIL01 = "처리중 오류가 발생되었습니다.";
    public static final String DOLOGIN = "로그인 후 진행하세요.";
    public static final String MEMBERINFO = "회원정보를 모두 입력하세요.";
    public static final String BOARDWRITE = "게시글 내용을 모두 입력하세요";

    // 네이버 api 계정.
    public static String NAVER_CLIENT_ID = "4mJ7gqISlayBgvXf6RnU"; // 애플리케이션 클라이언트 아이디값";
    public static String NAVER_CLIENT_SECRET = "ZRLcefd8hi"; // 애플리케이션 클라이언트 시크릿값";

    /*
     * 스트링 타입 널 체크! 가끔씩 화면에서 찐 null이 아닌 문자 "null"을 보내주는 경우가 있음.
     * 대충 null일시 true
     */
    public static boolean STRING_NULL_CHECK(String s) {
        if (s == null) {
            return true;
        } else if (s != null) {
            if (s.equals("null")) {
                return true;
            } else if (s.equals("NULL")) {
                return true;
            } else if (s.equals("Null")) {
                return true;
            } else if (s.equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 서버 통신 메소드
     * 잘 몰라서 복붙함.
     * 
     * @param apiURL
     * @param headerStr
     * @return
     * @throws IOException
     */
    public static String requestToServer(String apiURL, String headerStr) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
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
