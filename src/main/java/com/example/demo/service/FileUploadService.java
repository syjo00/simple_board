package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.BoardFileDTO;
import com.example.demo.DTO.BoardWriteDTO;
import com.example.demo.mapper.FileMapper;

@Service
public class FileUploadService{

    @Autowired
    private static FileMapper filedMapper;

   
    public static  boolean fileupload(BoardFileDTO boardFileDTO, BoardWriteDTO boardWriteDTO, List<MultipartFile> uploadFile, String uuid,String filename) {

      
        System.out.println("=================== FileUploadService 출력 ====================");
        System.out.println("boardFileDTO :  " + boardFileDTO);
        System.out.println("BoardWriteDTO :  " + boardWriteDTO);
        System.out.println("uploadFile :  " + uploadFile);
        System.out.println("filename :  " + filename);
        System.out.println("uuid  :  " + uuid);


        boardFileDTO.setUuid(uuid); //파일 고유 번호
        boardFileDTO.setBoard_id(boardWriteDTO.getId()); //게시글 번호
        boardFileDTO.setFile_name(filename);
        boardFileDTO.setCreator(boardWriteDTO.getWriter()); //creator
        
         System.out.println("=================== FileUploadService 출력 ====================");
         System.out.println("boardFileDTO 출력 :  " + boardFileDTO);
        System.out.println("=================== FileUploadService 출력 ====================");

        try {
          filedMapper.saveFile(boardFileDTO);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    
}
