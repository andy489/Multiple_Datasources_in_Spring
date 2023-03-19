package com.sources.repository.foo;

import com.sources.model.entity.foo.FooEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<FooEntity, Long> {
}
