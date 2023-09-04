package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Common;
import com.example.demo.DTO.MemberDTO;
import com.example.demo.DTO.MessageDTO;
import com.example.demo.service.MemberEntrService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("board")
public class MemberController {

   private MemberEntrService memberEntrService;
   

    @GetMapping({"/entr"})
     public String entr() {
         return "board/entr";
    }

    
    @PostMapping("/entr")
    public String write(MemberDTO MemberDTO, Model model) {
        
        MessageDTO message;

        if(!isMemberDataValid(MemberDTO)){

            if(memberEntrService.entr(MemberDTO)){
                message = new MessageDTO(Common.SAVESUCCES01, "/", RequestMethod.GET, null);
            }
            else{
                message = new MessageDTO(Common.FAIL01, "/", RequestMethod.GET, null);
            }            

        }else{

            message = new MessageDTO(Common.MEMBERINFO, "/", RequestMethod.GET, null);
            
        }    
        
            return showMessageAndRedirect(message, model);

    }//write(); 

        private boolean isMemberDataValid(MemberDTO memberDTO) {

            return !(
                memberDTO.getName()=="" ||
                memberDTO.getPw()=="" ||
                memberDTO.getEmail()=="" ||
                memberDTO.getMember_no()=="" ||
                memberDTO.getUpdater() == null ||
                memberDTO.getCreator() == null ||
                memberDTO.getUpdater() == null ||
                memberDTO.getUpdate_time() == null
            );

        }



    //메시지 출력용 화면.
    private String showMessageAndRedirect(final MessageDTO params, Model model) {
        model.addAttribute("params", params);
        return "fragments/messageRedirect";
    }
    
}//Controller

    


