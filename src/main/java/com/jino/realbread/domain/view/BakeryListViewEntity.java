package com.jino.realbread.domain.view;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Entity(name = "bakery_list_view")
@Table(name = "bakery_list_view")
public class BakeryListViewEntity {

    @Id
    @Column(name = "bakery_list_view_id") // ← 새로 만든 row 번호
    private Integer id;

    // Bakery 정보
    @Column(name = "bakery_id")
    private Integer bakeryId;

    @Column(name = "bakery_title")
    private String bakeryTitle;

    @Column(name = "bakery_category")
    private String bakeryCategory;

    @Column(name = "bakery_address")
    private String bakeryAddress;

    @Column(name = "bakery_road_address")
    private String bakeryRoadAddress;

    @Column(name = "bakery_link")
    private String bakeryLink;

    @Column(name = "bakery_mapx")
    private String bakeryMapx;

    @Column(name = "bakery_mapy")
    private String bakeryMapy;

    @Column(name = "bakery_favorite_count")
    private int bakeryFavoriteCount;

    @Column(name = "bakery_comment_count")
    private int bakeryCommentCount;

    // Menu 정보
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private String menuPrice;

    @Column(name = "menu_image_url")
    private String menuImageUrl;

    @Column(name = "menu_description", length = 2000)
    private String menuDescription;

}
