package com.realestate.purchase;

import com.realestate.exception.ResourceNotFoundException;
import com.realestate.lease.RequestStatus;
import com.realestate.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepo repository;

    @Override
    public Purchase createRequest(PurchaseRequest request) {
        Purchase purchaseRequest =
                Purchase.builder()
                        .unitId(request.getUnitId())
                        .customerName(
                                request.getCustomerName())
                        .phone(request.getPhone())
                        .email(request.getEmail())
                        .budget(request.getBudget())
                        .message(request.getMessage())
                        .status(RequestStatus.PENDING)
                        .createdAt(
                                LocalDateTime.now())
                        .build();

        return repository.save(purchaseRequest);
    }

    @Override
    public Purchase getRequestById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Purchase request not found with id : "
                                        + id));
    }

    @Override
    public List<Purchase> getAllRequests() {
        return repository.findAll();
    }

    @Override
    public Purchase updateStatus(Long id, String status) {
        Purchase request = getRequestById(id);
        request.setStatus(RequestStatus.valueOf(status.toUpperCase()));
        return repository.save(request);
    }

    @Override
    public void deleteRequest(Long id) {

        Purchase request =
                getRequestById(id);

        repository.delete(request);
    }


}
