package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Common;
import com.example.demo.DTO.BoardFileDTO;
import com.example.demo.DTO.BoardSearchAllDTO;
import com.example.demo.DTO.BoardSearchDTO;
import com.example.demo.DTO.BoardUpdateDTO;
import com.example.demo.DTO.BoardWriteDTO;
import com.example.demo.DTO.MessageDTO;
import com.example.demo.service.BoardDeleteService;
import com.example.demo.service.BoardDetailService;
import com.example.demo.service.BoardInsertService;
import com.example.demo.service.BoardSelectService;
import com.example.demo.service.BoardUpdateService;
import com.example.demo.service.FileUploadService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("board")
public class BoardController {

    private BoardSelectService boardSelectService;
    private BoardInsertService boardInsertService;
    private BoardDeleteService boardDeleteService;
    private BoardDetailService boardDetailService;
    private BoardUpdateService boardUpdateService;
    private BasicContoller basicContoller;
    private FileUploadService fileUploadService;

    // 게시판

    // 게시글 목록
    // list 경로로 GET 메서드 요청이 들어올 경우 list 메서드와 맵핑시킴
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.

    @GetMapping({ "", "/list" })
    public String list(Model model,
            @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword) {
        // required = false는 파라미터가 필수가 아니어도 요청처리 가능.
        // 게시판 전체 조회
        List<BoardSearchAllDTO> boardList;
        ArrayList<String> pageList;
        BoardSearchDTO pageInfo = new BoardSearchDTO(pageNum, searchType, keyword);

        // 페이징.
        pageList = boardSelectService.getPageList(searchType, keyword, pageNum);

        if (!Common.STRING_NULL_CHECK(searchType) && !Common.STRING_NULL_CHECK(keyword)) {
            // 검색결과 조회
            boardList = boardSelectService.getSearch(searchType, keyword, pageNum);
        } else {
            // 게시판 전체 조회
            boardList = boardSelectService.getAllBoard(pageNum);
            
        }

        // 검색한거 남길때 null로 그대로 들어가는 경우가 있어. null로 닦아주기.
        if (Common.STRING_NULL_CHECK(searchType)) {
            pageInfo.setSearchType(null);
        }
        if (Common.STRING_NULL_CHECK(keyword)) {
            pageInfo.setKeyword(null);
        }

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        model.addAttribute("pageInfo", pageInfo);

        return "board/list";
    }

    @GetMapping("/post")
    public String write(@SessionAttribute(name = "name", required = false) String name, Model model) {

        // 세션 (로그인 안했을시) 로그인 후 이용 메세지 출력.
        if (Common.STRING_NULL_CHECK(name)) {
            MessageDTO message = new MessageDTO(Common.DOLOGIN, "/", null, null);
            return basicContoller.showMessageAndRedirect(message, model);
        }
        model.addAttribute("name", name);
        return "board/write";
    }

    // 글을 쓴 뒤 POST 메서드로 글 쓴 내용을 DB에 저장
    // 그 후에는 /list 경로로 리디렉션해준다.
    @PostMapping("/post")
    public String write(@SessionAttribute(name = "userId", required = true) int userId,
            @RequestParam("uploadFile") List<MultipartFile> uploadFile,
            BoardWriteDTO boardWriteDTO,
            BoardFileDTO boardFileDTO,
            Model model) {

        MessageDTO message;
        System.out.println("boardWriteDTO 출력 : " + boardWriteDTO);

        if (!isBoardDataValid(boardWriteDTO)) {

            if (boardInsertService.save(boardWriteDTO, userId)) {

                message = new MessageDTO(Common.SAVESUCCES01, "/board/list", RequestMethod.GET, null);
            } else {
                message = new MessageDTO(Common.FAIL01, "/board/list", RequestMethod.GET, null);
            }

        } else {

            message = new MessageDTO(Common.BOARDWRITE, "/board/post", RequestMethod.POST, null);

            System.out.println(message);

        }

        // 파일저장

        UUID uuID = UUID.randomUUID();

        String uuid = uuID.toString();

        System.out.println("uuid : " + uuid);

        String uploadFolder = "C:\\my_directory"; // 생성할 폴더 경로

        Path directory = Paths.get(uploadFolder);

        for (MultipartFile multipartFile : uploadFile) {
            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

            String filename = saveFile.getAbsolutePath();

            fileUploadService.fileupload(boardFileDTO, boardWriteDTO, uploadFile, uuid, filename);

            try {

                // 폴더가 존재하지 않으면 생성
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                    multipartFile.transferTo(saveFile);

                    System.out.println("폴더가 생성되었습니다.");
                } else {
                    System.out.println("폴더가 이미 존재합니다.");
                    multipartFile.transferTo(saveFile);
                }

            } catch (IOException e) {
                System.err.println("폴더 생성 실패: " + e.getMessage());
            }

        } // end for

        return basicContoller.showMessageAndRedirect(message, model);

    }// write();



    private boolean isBoardDataValid(BoardWriteDTO boardWriteDTO) {
        System.out.println("boardWriteDTO-boolean 출력 : " + boardWriteDTO);
        return (boardWriteDTO.getTitle() == "" ||
                boardWriteDTO.getContent() == "" ||
                boardWriteDTO.getContent() == "");
    }



    // 게시물 삭제는 PostMapping 메서드를 사용하여 간단하게 삭제할 수 있다.
    @PostMapping("/post/{board_id}")
    public String delete(@PathVariable("board_id") String board_id,Model model) {

        MessageDTO message;
        System.out.println("삭제 board_id"+board_id);      

        if (boardDeleteService.delete(board_id)) {
            message = new MessageDTO(Common.DELETESUCCES01, "/board/list", RequestMethod.GET, null);
             System.out.println("삭제 메세지 : " + message);

        } else {
            message = new MessageDTO(Common.FAIL01, "/board/list", RequestMethod.GET, null);
             System.out.println("삭제 메세지 : " + message);
        }
          return basicContoller.showMessageAndRedirect(message,model);
    }



    /* 정상작동 */
    // 게시물 수정 페이지이며, {id}로 페이지 넘버를 받는다.

    @GetMapping("/edit/update/{id}")
    public String edit(@PathVariable("id") String board_id, Model model) throws Exception {

        System.out.println("받은 업데이트 데이터: " + board_id);
        // id를 기반으로 해당 게시물 정보를 가져와서 수정 화면에 뿌려준다는 가정하에...
        // BoardUpdateDTO boardUpdateDTO = new BoardUpdateDTO(); // 또는 해당 DTO 생성 방식으로
        // 초기화
        // boardSearchAllDTO.setId(id); // DTO에 id 설정

        BoardSearchAllDTO boardDTO = boardDetailService.getBoardById(board_id);

        System.out.println("boardDTO - boardDetailService에서 받은 데이터 " +  boardDTO);

        model.addAttribute("board", boardDTO);
        // board라는 이름으로 boardDTO 객체를 모델에 추가함.

        System.out.println("update할 데이터를 model에 담음." + model);

        return "board/update";
    }

    // 위는 GET 메서드이며, PUT 메서드를 이용해 게시물 수정한 부분에 대해 적용

    /* 정상작동 버전 */
    @PostMapping("/post/update/{id}")
    // public String resave(BoardWriteDTO boardWriteDTO) {
    public String resave(BoardUpdateDTO boardUpdateDTO, Model model) {
        
        System.out.println("==============수정 후 저장 (BoardController.java)==============");
        
        MessageDTO message;
        
        if (boardUpdateService.updateBoard(boardUpdateDTO)) {

            System.out.println("업데이트 boardUpdateDTO 출력" + boardUpdateDTO);

            message = new MessageDTO(Common.UPDATESUCCESEE01, "/board/list", RequestMethod.GET, null);
           
            System.out.println("업데이트 메세지 : " + message);

        } else {
            message = new MessageDTO(Common.FAIL01, "/board/list", RequestMethod.GET, null);

            System.out.println("업데이트 메세지 : " + message);
        }
         
        return basicContoller.showMessageAndRedirect(message, model);
       
        
    }


    @GetMapping("/detail")
    public String viewBoardDetail(@SessionAttribute(name = "userId", required = false) String userId,
            @RequestParam("id") String id, Model model) throws Exception {

        System.out.println("=====================DetailController.java<상세보기>=======================");
        BoardSearchAllDTO boardDTO = boardDetailService.getBoardById(id);

        model.addAttribute("board", boardDTO);
        // board라는 이름으로 boardDTO 객체를 모델에 추가함.
        model.addAttribute("userId", userId);

        System.out.println("model:" + model);
        System.out.println("======================DetailController.java<상세보기 끝> =====================");
        return "board/detail";
    }

}