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
            memberMapper.entrMember(memberDTO);
            
        } catch (Exception e) {
            return false;
        }

        return true;

    }
  
}





 