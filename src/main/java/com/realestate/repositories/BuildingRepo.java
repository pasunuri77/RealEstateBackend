package com.realestate.repositories;

import com.realestate.building.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepo extends JpaRepository<Building,Long> {

    List<Building> findByProjectId(Long projectId);

}
