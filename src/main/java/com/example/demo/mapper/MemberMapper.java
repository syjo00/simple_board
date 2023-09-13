package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;


import com.example.demo.DTO.MemberDTO;

@Mapper
public interface MemberMapper {
    
    //회원가입
    public void entrMember(MemberDTO memberDTO);

    //로그인체크
    public int logInCheck(MemberDTO memberDTO);

    public MemberDTO getMember(MemberDTO memberDTO);

    //회원가입체크.
    public int entrCheck(MemberDTO memberDTO);

}
