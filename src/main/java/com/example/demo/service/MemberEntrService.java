package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;

@Service
public class MemberEntrService {
    
    @Autowired
    private static MemberMapper memberMapper;
  
    

}
