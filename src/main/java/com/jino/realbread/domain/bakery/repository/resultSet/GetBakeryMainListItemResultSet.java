package com.jino.realbread.domain.bakery.repository.resultSet;

public interface GetBakeryMainListItemResultSet {

    Integer getBakeryNumber();
    String getTitle();
    Integer getFavoriteCount();
    Integer getCommentCount();

    Integer getMenuNumber();
    String getImageUrl();
}
