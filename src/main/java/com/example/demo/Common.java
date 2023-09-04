package com.example.demo;

//공통 제어할꺼들.
public class Common {
    //페이지 갯수.
    public static final int PAGERECORDSIZE = 5;



    //메시지 문구 정리
    public static final String SAVESUCCES01 = "저장되었습니다.";
    public static final String DELETESUCCES01 = "삭제되었습니다.";
    public static final String UPDATESUCCESEE01 = "수정되었습니다.";
    public static final String FAIL01 = "처리중 오류가 발생되었습니다.";
    public static final String DOLOGIN = "로그인 후 진행하세요.";
    public static final String MEMBERINFO ="회원정보를 모두 입력하세요.";
    public static final String BOARDWRITE = "게시글 내용을 모두 입력하세요";
    

 
    /*
     * 스트링 타입 널 체크! 가끔씩 화면에서 찐 null이 아닌 문자 "null"을 보내주는 경우가 있음.
     * 대충 null일시 true
     */
    public static boolean STRING_NULL_CHECK(String s){
        if(s == null){
            return  true;
        }
        else if(s != null){
            if(s.equals("null")){
                return true;
            }
            else if (s.equals("NULL")){
                return true;
            }
            else if (s.equals("Null")){
                return true;
            }
            else if (s.equals("")){
                return true;
            }
        }
        return false;
    }
}
