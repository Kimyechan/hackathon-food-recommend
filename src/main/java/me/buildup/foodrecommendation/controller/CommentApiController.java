package me.buildup.foodrecommendation.controller;

import lombok.RequiredArgsConstructor;
import me.buildup.foodrecommendation.controller.web.dto.CommentListResponseDto;
import me.buildup.foodrecommendation.controller.web.dto.CommentResponseDto;
import me.buildup.foodrecommendation.controller.web.dto.CommentSaveRequestDto;
import me.buildup.foodrecommendation.controller.web.dto.CommentUpdateRequestDto;
import me.buildup.foodrecommendation.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/save")
    public Long save(@RequestBody CommentSaveRequestDto requestDto) {
        return commentService.save(requestDto);
    }

    @PutMapping("/update")
    public Long update(@PathVariable Long id, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.update(id, requestDto);
    }

    @DeleteMapping("/delete")
    public Long delete(@PathVariable Long id) {
        commentService.delete(id);
        return id;
    }
    //Todo
    // Designate the proper path for Mapping.
    @GetMapping("/")
    public CommentResponseDto findById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @GetMapping("/list")
    public List<CommentListResponseDto> findAll() {
        return commentService.findAllDesc();
    }
}
