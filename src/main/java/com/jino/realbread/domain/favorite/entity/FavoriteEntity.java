package com.jino.realbread.domain.favorite.entity;

import com.jino.realbread.domain.favorite.entity.primaryKey.FavoritePK;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name = "favorite")
@IdClass(FavoritePK.class)
public class FavoriteEntity {

    @Id
    private String userId;
    @Id
    private int bakeryNumber;
}
