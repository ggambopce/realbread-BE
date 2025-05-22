package com.jino.realbread.domain.statistics.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "visit_stat")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VisitStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bakeryId;

    private LocalDate date;

    private Long totalVisitCount;

    private Long uniqueVisitCount;

}
