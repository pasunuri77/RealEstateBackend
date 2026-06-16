package com.realestate.shopunit;

import java.util.List;

public interface ShopUnitService {

    ShopUnit createUnit(ShopUnitRequest request);

    ShopUnit updateUnit(Long id,
                        ShopUnitRequest request);

    ShopUnit getUnitById(Long id);

    List<ShopUnit> getAllUnits();

    List<ShopUnit> getUnitsByProject(Long projectId);

    void deleteUnit(Long id);


}
