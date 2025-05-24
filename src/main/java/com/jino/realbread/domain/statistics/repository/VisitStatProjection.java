package com.jino.realbread.domain.statistics.repository;

import java.time.LocalDate;

public interface VisitStatProjection {
    LocalDate getDate();

    Long getUniqueVisitCount();
}
