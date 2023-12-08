package com.example.plusone.discount.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "discount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Discount {

    @Id
    @Column(length = 4, nullable = false)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String type;


}
