package com.jino.realbread.domain.favorite.entity.primaryKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePK implements Serializable {

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "board_number")
    private int boardNumber;

}
