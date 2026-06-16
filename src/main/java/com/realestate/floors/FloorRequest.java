package com.realestate.floors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorRequest {
    @NotNull(message = "Building Id is required")
    private Long buildingId;

    @NotNull(message = "Floor number is required")
    private Integer floorNumber;

    @NotBlank(message = "Floor name is required")
    private String floorName;

    private String description;

    private Integer units;


}
