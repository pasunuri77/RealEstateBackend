package com.realestate.building;

import java.util.List;

public interface BuildingService {

    Building createBuilding(BuildingRequest request);

    Building updateBuilding(Long id,
                            BuildingRequest request);

    Building getBuildingById(Long id);

    List<Building> getAllBuildings();

    List<Building> getBuildingsByProject(Long projectId);

    void deleteBuilding(Long id);
}
