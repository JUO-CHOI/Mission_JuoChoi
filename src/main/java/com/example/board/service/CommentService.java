package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.entity.Notice;
import com.example.board.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final NoticeService noticeService;
    private final CommentRepository commentRepository;

    public void create(
            String text,
            String password,
            Notice notice
    ) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setPassword(password);
        comment.setNotice(notice);
        commentRepository.save(comment);
    }

    public List<Comment> readAll() {
        return commentRepository.findAll();
    }
    public Comment read(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment
                .orElse(null);
    }

    public List<Comment> readByNoticeId(Long id) {
        Notice notice = noticeService.readOne(id);
        List<Comment> returnList = new ArrayList<>();
        for (Comment comment : readAll()) {
            if (comment.getNotice() != null && comment.getNotice().equals(notice)) {
                returnList.add(comment);
            }
        }
        return returnList;
    }

    public void delete(Long id, String password) {
        if (read(id).getPassword().equals(password)) {
            commentRepository.deleteById(id);
        }
    }

    public void deleteByNoticeId(Long id) {
        Notice notice = noticeService.readOne(id);
        for (Comment comment : readAll()) {
            if (comment.getNotice() != null && comment.getNotice().equals(notice)) {
                commentRepository.delete(comment);
            }
        }
    }
}
