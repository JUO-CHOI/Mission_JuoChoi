package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.entity.Notice;
import com.example.board.repo.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void create(
            String title,
            String type,
            String text,
            String password
    ) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setType(type);
        notice.setText(text);
        notice.setPassword(password);

        noticeRepository.save(notice);
    }

    public List<Notice> readAll() {
        List<Notice> returnList = noticeRepository.findAll();
        Collections.reverse(returnList);
        return returnList;
    }

    public Notice readOne(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice
                .orElse(null);
    }

    public List<Notice> findByType(String type) {
        List<Notice> returnList = new ArrayList<>();
        for (Notice notice : readAll()) {
            if (notice.getType().equals(type)) {
                returnList.add(notice);
            }
        }
        return returnList;
    }

    public void update(
            Long id,
            String title,
            String type,
            String text,
            String password
    ) {
        Notice notice = readOne(id);
        if (!notice.getPassword().equals(password)) {
            System.out.println("비밀번호 틀림");
        }
        else {
            notice.setTitle(title);
            notice.setType(type);
            notice.setText(text);

            noticeRepository.save(notice);
        }
    }

    public void delete(Long id, String password) {
        if (password.equals(readOne(id).getPassword())) {
            noticeRepository.deleteById(id);
        }
    }

//    public List<Comment> comments(Long id) {
//        return readOne(id).getComment();
//    }
}
