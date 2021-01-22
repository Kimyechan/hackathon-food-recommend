package me.buildup.foodrecommendation.service;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.controller.web.dto.CommentListResponseDto;
import me.buildup.foodrecommendation.controller.web.dto.CommentResponseDto;
import me.buildup.foodrecommendation.controller.web.dto.CommentSaveRequestDto;
import me.buildup.foodrecommendation.controller.web.dto.CommentUpdateRequestDto;
import me.buildup.foodrecommendation.domain.Comment;
import me.buildup.foodrecommendation.repo.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto) {
        return commentRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found." + id));

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found." +  id));

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) {
        Comment entity = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found." + id));

        return new CommentResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<CommentListResponseDto> findAllDesc() {
        return commentRepository.findAllDesc().stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }

}
