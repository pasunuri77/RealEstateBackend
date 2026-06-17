package com.realestate.building;


import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildingRequest {

    private Long projectId;

    private String name;

    private Integer totalFloors;

    private String description;

//    private String address;

//    private Double latitude;
//
//    private Double longitude;
}
