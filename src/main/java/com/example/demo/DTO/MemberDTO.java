package com.example.demo.DTO;

import lombok.Data;

@Data
public class MemberDTO {    
    
    private String id;
    private String pw;
    private String name;
    private String email;
    private String creation_time;
    private String creator;
    private String update_time;
    private String updater;

}
