package com.jino.realbread.domain.bakery.repository.resultSet;

public interface GetBakeryResultSet {

    Integer getBakeryNumber();
    String getTitle();
    String getRoadAddress();
    Integer getFavoriteCount();
    Integer getCommentCount();

    Integer getMenuNumber();
    String getMenuName();
    String getPrice();
    String getImageUrl();
    String getDescription();

}
