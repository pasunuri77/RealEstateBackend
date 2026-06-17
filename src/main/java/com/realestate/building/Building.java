package com.realestate.building;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "buildings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;

    private String name;

    private Integer totalFloors;

    private String description;

//    private String address;

//    private Double latitude;
//
//    private Double longitude;
}