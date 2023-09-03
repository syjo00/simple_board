package com.example.demo;

//공통 제어할꺼들.
public class Common {
    //페이지 갯수.
    public static final int PAGERECORDSIZE = 5;





    //메시지 문구 정리
    public static final String SAVESUCCES01 = "저장되었습니다.";
    public static final String DELETESUCCES01 = "삭제되었습니다.";
    public static final String UPDATESUCCESEE01 = "수정되었습니다.";
    public static final String FAIL01 = "처리중오류가 발생되었습니다.";
    public static final String DOLOGIN = "로그인후 부탁드립니다.";
    


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
