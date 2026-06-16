package com.realestate.shopunit;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopUnitRequest {

    private Long projectId;

    private Long buildingId;

    private Long floorId;

    private String unitNumber;

    private UnitType unitType;

    private Double areaSqft;

    private AvailabilityType availabilityType;

    private Double monthlyRent;

    private Double yearlyRent;

    private Double salePrice;

    private Double bookingAmount;

    private Double maintenanceCharges;

    private UnitStatus status;
}