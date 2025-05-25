package com.jino.realbread.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jino.realbread.domain.comment.dto.request.PostCommentRequestDto;
import com.jino.realbread.domain.comment.dto.response.GetCommentListResponseDto;
import com.jino.realbread.domain.comment.dto.response.PostCommentResponseDto;
import com.jino.realbread.domain.comment.service.CommentService;
import com.jino.realbread.global.security.auth.PrincipalDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bakery")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{bakeryNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(
            @RequestBody @Valid PostCommentRequestDto requestDto, @PathVariable("bakeryNumber") Integer bakeryNumber,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ResponseEntity<? super PostCommentResponseDto> response = commentService.postComment(requestDto, bakeryNumber,
                principalDetails);
        return response;
    }

    @GetMapping("/{bakeryNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
            @PathVariable("bakeryNumber") Integer bakeryNumber) {
        ResponseEntity<? super GetCommentListResponseDto> response = commentService.getCommentList(bakeryNumber);

        return response;
    }

}
