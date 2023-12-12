package com.example.plusone.discount.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

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

    @Column (length = 20)
    private String start_date;

    @Column (length = 20)
    private String end_date;


}
