package com.jino.realbread.domain.comment.service.implement;

import com.jino.realbread.domain.bakery.entity.Bakery;
import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import com.jino.realbread.domain.comment.dto.request.PostCommentRequestDto;
import com.jino.realbread.domain.comment.dto.response.PostCommentResponseDto;
import com.jino.realbread.domain.comment.entity.CommentEntity;
import com.jino.realbread.domain.comment.repository.CommentRepository;
import com.jino.realbread.domain.comment.service.CommentService;
import com.jino.realbread.domain.user.repository.UserRepository;
import com.jino.realbread.global.dto.response.ResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService {

    private final BakeryRepository bakeryRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer bakeryNumber,
            Integer userId) {
        try {

            Bakery bakeryEntity = bakeryRepository.findByBakeryNumber(bakeryNumber);
            if (bakeryEntity == null)
                return PostCommentResponseDto.noExistBakery();

            boolean existedUser = userRepository.existsById(userId);
            if (!existedUser)
                return PostCommentResponseDto.noExistUser();

            CommentEntity commentEntity = new CommentEntity(dto, bakeryNumber, userId);
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
