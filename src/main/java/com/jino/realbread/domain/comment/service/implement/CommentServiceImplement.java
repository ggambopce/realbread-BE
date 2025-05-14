package com.jino.realbread.domain.comment.service.implement;

import com.jino.realbread.domain.comment.dto.request.PostCommentRequestDto;
import com.jino.realbread.domain.comment.dto.response.PostCommentResponseDto;
import com.jino.realbread.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService {
    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer bakeryNumber, Integer userId) {
        try {

            Bakery bakeryEntity = bakeryRepository.findByBakeryNumber(bakeryNumber);
            if (bakeryEntity == null)
                return PostCommentResponseDto.noExistBakery();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser)
                return PostCommentResponseDto.noExistUser();

            CommentEntity commentEntity = new CommentEntity(dto, bakeryNumber, email);
            commentRepository.save(commentEntity);

            bakeryEntity.increaseCommentCount();
            bakeryRepository.save(bakeryEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostCommentResponseDto.success();
    }
}
