package com.jino.realbread.domain.bakery.repository;

import com.jino.realbread.domain.bakery.entity.Bakery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BakeryRepository extends JpaRepository<Bakery, Long> {

    boolean existsByAddress(String address);
}
