package com.realestate.shopunit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shop_units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopOrUnitId;

    private Long projectId;

    private Long buildingId;

    private Long floorId;

    private String unitNumber;

    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    private Double areaSqft;

    @Enumerated(EnumType.STRING)
    private AvailabilityType availabilityType;

    private Double monthlyRent;

    private Double yearlyRent;

    private Double salePrice;

    private Double bookingAmount;

    private Double maintenanceCharges;

    @Enumerated(EnumType.STRING)
    private UnitStatus status;
}

