package com.jino.realbread.domain.comment.service;

import com.jino.realbread.domain.comment.dto.request.PostCommentRequestDto;
import com.jino.realbread.domain.comment.dto.response.GetCommentListResponseDto;
import com.jino.realbread.domain.comment.dto.response.PostCommentResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer bakeryNumber,
            Integer userId);

    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer bakeryNumber);

}
