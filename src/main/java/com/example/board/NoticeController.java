package com.example.board;

import com.example.board.service.CommentService;
import com.example.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final CommentService commentService;
    String[] boardName = {"자유 게시판", "개발 게시판", "일상 게시판", "사건사고 게시판"};
    String[] boardType = {"free", "develop", "daily", "event"};

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/create-view")
    public String creatView() {
        return "create";
    }

    @PostMapping("/create")
    public String create(
            @RequestParam("title")
            String title,
            @RequestParam("type")
            String type,
            @RequestParam("text")
            String text,
            @RequestParam("password")
            String password
    ) {
        noticeService.create(title, type, text, password);
        return "redirect:/home";
    }

    @GetMapping("/home/{type}")
    public String typeBoard(
            @PathVariable("type")
            String type,
            Model model
    ) {
        if (type.equals("all")) {
            model.addAttribute("boardName", "전체 게시판");
            model.addAttribute("notices", noticeService.readAll());
        }
        else {
            model.addAttribute("boardName", type);
            model.addAttribute("notices", noticeService.findByType(type));
        }
        return "board";
    }

    @GetMapping("/read/{id}")
    public String read(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("notice", noticeService.readOne(id));
        model.addAttribute("comments", commentService.readByNoticeId(id));
        return "read";
    }

    @GetMapping("/update-view/{id}")
    public String updateView(
            @PathVariable("id")
            Long id,
            Model model) {
        model.addAttribute("notice", noticeService.readOne(id));
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id")
            Long id,
            @RequestParam("title")
            String title,
            @RequestParam("type")
            String type,
            @RequestParam("text")
            String text,
            @RequestParam("password")
            String password
    ){
        noticeService.update(id, title, type, text, password);

        return String.format("redirect:/read/%d", id);
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable("id")
            Long id,
            @RequestParam("notice_password")
            String password
    ){
        if (password.equals(noticeService.readOne(id).getPassword())) {
            commentService.deleteByNoticeId(id);
        }
        noticeService.delete(id, password);

        return "redirect:/home";
    }
}
