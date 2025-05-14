package com.jino.realbread.domain.favorite.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteListItem {

    private String email;
    private String nickname;
    private String profileImage;

    public FavoriteListItem(GetFavoriteListResultSet resultSet) {
        this.email = resultSet.getEmail();
        this.nickname = resultSet.getNickname();
        this.profileImage = resultSet.getProfileImage();
    }

    public static List<FavoriteListItem> copyList(List<GetFavoriteListResultSet> resultSets) {
        List<FavoriteListItem> list = new ArrayList<>();
        for (GetFavoriteListResultSet resultSet : resultSets) {
            FavoriteListItem favoriteListItem = new FavoriteListItem(resultSet);
            list.add(favoriteListItem);
        }
        return list;
    }
}