package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.mapper.MemberMapper;

@Service
public class MemberEntrService {
    
    @Autowired
    private  MemberMapper memberMapper;

    public boolean entr(MemberDTO memberDTO) {
        
        System.out.println("memberDTO - MemberEntrService :" + memberDTO);
        try {

            // 동일 id 있을시 오류 발생.
            if (memberMapper.entrCheck(memberDTO) > 1){
                throw new RuntimeException("아이디중복");
            };

            memberMapper.entrMember(memberDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
  
}





 