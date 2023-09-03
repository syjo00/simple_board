package com.example.demo;

public class Common {
    public static final int PAGERECORDSIZE = 5;



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
