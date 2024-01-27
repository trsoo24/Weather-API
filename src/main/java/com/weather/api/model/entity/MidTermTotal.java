package com.weather.api.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "location"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidTermTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String location;
    private String description;
    // ALTER TABLE mid_term_total MODIFY COLUMN description VARCHAR(1024); 로 길이 조정
}
