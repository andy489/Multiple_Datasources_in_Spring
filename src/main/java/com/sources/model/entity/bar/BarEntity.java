package com.sources.model.entity.bar;

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
@Entity(name="uniqueBarEntity")
@Table(name = "bars")
public class BarEntity extends BaseEntity {

    @Column(nullable = false)
    private String barContent;

    @Override
    public String toString() {
        return "FooEntity{" +
                "id='" + getId() + "', " +
                "'barContent='" + barContent + '\'' +
                '}';
    }

}
