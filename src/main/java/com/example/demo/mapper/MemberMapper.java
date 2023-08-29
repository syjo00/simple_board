package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;


import com.example.demo.DTO.MemberDTO;

@Mapper
public interface MemberMapper {
    
    //회원가입
    public void entrMember(MemberDTO memberDTO);


}
