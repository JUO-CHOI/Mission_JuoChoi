package com.example.board;

import com.example.board.entity.Comment;
import com.example.board.service.CommentService;
import com.example.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final NoticeService noticeService;

    @PostMapping("/{id}/add-comment")
    public String addComment(
            @PathVariable("id")
            Long id,
            @RequestParam("comment_text")
            String text,
            @RequestParam("comment_password")
            String password
    ) {
        commentService.create(text, password, noticeService.readOne(id));
        return String.format("redirect:/read/%d", id);
    }

    @PostMapping("{id}/delete-comment")
    public String deleteComment(
            @PathVariable("id")
            Long id,
            @RequestParam("comment_password")
            String password
    ){
        Comment comment = commentService.read(id);
        Long noticeId = comment.getNotice().getId();
        commentService.delete(id, password);
        return String.format("redirect:/read/%d", noticeId);
    }
}
