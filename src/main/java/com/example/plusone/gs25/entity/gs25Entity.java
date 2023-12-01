package com.example.plusone.gs25.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "gs25Entity")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class gs25Entity {

    @Id
    @Column(length = 50)
    private int num;

    @Column(length = 10)
    private int price;

    @Column(length = 50)
    private String name;

    @Column(length = 100)
    private String img;

    @Column(length = 30)
    private String type;

}
