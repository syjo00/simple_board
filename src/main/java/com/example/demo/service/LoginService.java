package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.mapper.MemberMapper;

@Service
public class LoginService {

    @Autowired
    MemberMapper memberMapper;

    public boolean logInCheck(MemberDTO memberDTO) {

        int count;

        try {
            count = memberMapper.logInCheck(memberDTO);
        } catch (Exception e) {
            return false;
        }

        // count가 1일 경우에만 로그인 정상 성공. 그 외에 경우엔 전부 로그인 실패 처리..
        if(count == 1){
            return true;
        }

        return false;
    }

    public MemberDTO getMember(MemberDTO memberDTO) {
        return memberMapper.getMember(memberDTO);
    }
    
}
