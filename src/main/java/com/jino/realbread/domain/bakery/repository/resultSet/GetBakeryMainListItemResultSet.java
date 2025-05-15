package com.jino.realbread.domain.bakery.repository.resultSet;

public interface GetBakeryMainListItemResultSet {

    Integer getBakeryNumber();

    String getTitle();

    String getRoadAddress();

    Integer getFavoriteCount();

    Integer getCommentCount();

    String getLink();

    String getMapx();

    String getMapy();

    Integer getMenuNumber();

    String getImageUrl();
}
