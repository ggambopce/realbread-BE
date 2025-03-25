package com.jino.realbread.naverapi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bakery")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bakery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String link;
    private String mapx;
    private String mapy;
    private String description;
}
