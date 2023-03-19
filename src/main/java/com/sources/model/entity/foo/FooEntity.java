package com.sources.model.entity.foo;

import com.sources.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="uniqueFooEntity")
@Table(name = "foos")
public class FooEntity extends BaseEntity {

    @Column(nullable = false)
    private String fooContent;

    @Override
    public String toString() {
        return "FooEntity{" +
                "id='" + getId() + "', " +
                "'fooContent='" + fooContent + '\'' +
                '}';
    }
}