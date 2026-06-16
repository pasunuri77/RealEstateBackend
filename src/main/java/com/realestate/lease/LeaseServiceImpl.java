package com.realestate.lease;

import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repositories.LeaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepo repository;

    @Override
    public Lease createRequest(
            LeaseRequest request) {

        Lease lease =
                Lease.builder()
                        .unitId(request.getUnitId())
                        .customerName(
                                request.getCustomerName())
                        .phone(request.getPhone())
                        .email(request.getEmail())
                        .businessType(
                                request.getBusinessType())
                        .leaseType(
                                request.getLeaseType())
                        .preferredStartDate(
                                request.getPreferredStartDate())
                        .message(request.getMessage())
                        .status(RequestStatus.PENDING)
                        .createdAt(
                                LocalDateTime.now())
                        .build();

        return repository.save(lease);
    }

    @Override
    public Lease getRequestById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Lease request not found with id : "
                                        + id));
    }

    @Override
    public List<Lease> getAllRequests() {

        return repository.findAll();
    }

    @Override
    public List<Lease> getRequestsByStatus(
            String status) {

        return repository.findByStatus(
                RequestStatus.valueOf(
                        status.toUpperCase()));
    }

    @Override
    public Lease approveRequest(Long id) {

        Lease request =
                getRequestById(id);

        request.setStatus(
                RequestStatus.APPROVED);

        return repository.save(request);
    }

    @Override
    public Lease rejectRequest(Long id) {

        Lease request =
                getRequestById(id);

        request.setStatus(
                RequestStatus.REJECTED);

        return repository.save(request);
    }

    @Override
    public Lease updateStatus(Long id, String status) {

        Lease request =
                getRequestById(id);

        request.setStatus(
                RequestStatus.valueOf(
                        status.toUpperCase()));

        return repository.save(request);
    }

    @Override
    public void deleteRequest(Long id) {

        Lease request =
                getRequestById(id);

        repository.delete(request);
    }

}
