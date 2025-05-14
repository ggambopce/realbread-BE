package com.jino.realbread.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.comment.entity.CommentEntity;
import com.jino.realbread.domain.comment.repository.resultSet.GetCommentListResultSet;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query(value = "SELECT " +
            "U.nickname AS nickname, " +
            "U.profile_image AS profileImage, " +
            "C.write_datetime AS writeDatetime, " +
            "C.choice_menu AS choiceMenu, " +
            "C.content AS content " +
            "FROM comment AS C " +
            "INNER JOIN user AS U " +
            "ON C.user_id = U.id " +
            "WHERE C.bakery_number = ?1 " +
            "ORDER BY writeDatetime DESC", nativeQuery = true)
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);

}
