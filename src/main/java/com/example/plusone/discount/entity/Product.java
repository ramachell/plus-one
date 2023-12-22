package com.example.plusone.discount.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product") // JPA가 사용하기위해 필요한 어노테이션, JPA에게 알려주기 위해 에너테이션을 사용
@Getter // getter로 member변수에 직접 접근하지 않고 메서드를 통해 접근하도록 함
@ToString // 해당 객체의 멤버변수들이 어떤 값을 쓰고 있는지, 알기위해 사용.
@Builder // 빌더 패턴을 사용할 수 있도록 함, 내부객체의 멤버변수들이 변경될 일을 막으려고 함. 이후 개발자가 이 값의 상태를 변경하는 일로, 미스커뮤니케이션이 발생하여 생길문제를 방지하고자 함. immutable instance.
@NoArgsConstructor // 기본 생성자를 만들어줌
@AllArgsConstructor // builder 에서 필요한 생성자.
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 10, nullable = false)
    private int id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "image_url", length = 200)
    private String image_url;

    @Column(name = "price", length = 20, nullable = false)
    private int price;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "categories", length = 20)
    private String categories;

    @Column(name = "discountType", length = 10)
    private String discountType;

}
