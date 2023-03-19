package com.sources.repository.bar;

import com.sources.model.entity.bar.BarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarRepository extends JpaRepository<BarEntity, Long> {
}
