package me.buildup.foodrecommendation.domain;

import me.buildup.foodrecommendation.controller.web.dto.CommentResponseDto;
import me.buildup.foodrecommendation.repo.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void CommentCall() {
        //given
        Integer grade= 5;
        String content = "food was good.";

        commentRepository.save(Comment.builder()
                .grade(5)
                .content(content)
                .userName("Yechance")
                .build());

        //when
        List<Comment> commentList = commentRepository.findAll();

        //then
        Comment comment = commentList.get(0);
        assertThat(comment.getGrade()).isEqualTo(grade);
        assertThat(comment.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntityTest() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 1, 22, 0, 0, 0);
        commentRepository.save(Comment.builder()
                .grade(5)
                .content("content")
                .userName("Yechance")
                .build());
        //when
        List<Comment> commentList = commentRepository.findAll();

        //then
        Comment comment = commentList.get(0);

        System.out.println(">>>>>>>>> createDate=" + comment.getCreatedDate() + ", modifiedDate=" + comment.getModifiedDate());

        assertThat(comment.getCreatedDate()).isAfter(now);
        assertThat(comment.getModifiedDate()).isAfter(now);
    }
}
