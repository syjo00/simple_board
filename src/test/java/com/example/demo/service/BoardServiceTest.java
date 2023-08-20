package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@MybatisTest
public class BoardServiceTest {
    
    @Autowired
    private BoardService boardService;

    @Test
    void testGetAllBoard() {

        boardService.getAllBoard();

        assertTrue(true);

    }
}
