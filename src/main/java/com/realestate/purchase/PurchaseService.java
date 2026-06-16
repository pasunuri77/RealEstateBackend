package com.realestate.purchase;

import java.util.List;

public interface PurchaseService {
    Purchase createRequest(
            PurchaseRequest request);

    Purchase getRequestById(Long id);

    List<Purchase> getAllRequests();

    void deleteRequest(Long id);

}
