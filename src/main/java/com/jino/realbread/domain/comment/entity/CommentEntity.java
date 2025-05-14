package com.jino.realbread.domain.comment.entity;

import com.jino.realbread.domain.comment.dto.request.PostCommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNumber;
    private String choiceMenu;
    private String content;
    private String writeDatetime;
    private String userEmail;
    private int bakeryNumber;

    public CommentEntity(PostCommentRequestDto dto, Integer bakeryNumber, String email) {

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.choiceMenu = dto.getChoiceMenu();
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.userEmail = email;
        this.bakeryNumber = bakeryNumber;

    }
}
