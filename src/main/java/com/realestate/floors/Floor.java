package com.realestate.floors;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "floors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long buildingId;

    private Integer floorNumber;

    private String floorName;

    private String description;

    private Integer units;
}