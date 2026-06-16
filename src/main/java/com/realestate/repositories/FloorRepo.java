package com.realestate.repositories;

import com.realestate.floors.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorRepo extends JpaRepository<Floor, Long> {

    List<Floor> findByBuildingId(Long buildingId);

    long countByBuildingId(Long buildingId);

    boolean existsByBuildingIdAndFloorNumber(
            Long buildingId,
            Integer floorNumber);
}
