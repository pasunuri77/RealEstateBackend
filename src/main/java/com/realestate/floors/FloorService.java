package com.realestate.floors;

import java.util.List;

public interface FloorService {

    Floor createFloor(FloorRequest request);

    Floor updateFloor(Long id,
                      FloorRequest request);

    Floor getFloorById(Long id);

    List<Floor> getAllFloors();

    List<Floor> getFloorsByBuilding(Long buildingId);

    void deleteFloor(Long id);
}
