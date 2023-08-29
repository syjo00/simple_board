package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.MemberDTO;
import com.example.demo.mapper.MemberMapper;

@Service
public class MemberEntrService {
    
    @Autowired
    private  MemberMapper memberMapper;

    public void entr(MemberDTO memberDTO) {
        
        System.out.println("memberDTO - MemberEntrService :" + memberDTO);

         memberMapper.entrMember(memberDTO);

    }
  
}





 