package com.jino.realbread.menu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long menuNumber;

    private String menuName;
    private String price;
    @Column(length = 2000)
    private String imageUrl;
    private String description;

    @Column(name = "bakery_number", nullable = false)
    private Long bakeryId;
}
