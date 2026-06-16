package com.realestate.repositories;

import com.realestate.shopunit.ShopUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopUnitRepo extends JpaRepository<ShopUnit, Long> {

    List<ShopUnit> findByProjectId(Long projectId);

    long countByFloorId(Long floorId);

    boolean existsByFloorIdAndUnitNumber(
            Long floorId,
            String unitNumber);

}
