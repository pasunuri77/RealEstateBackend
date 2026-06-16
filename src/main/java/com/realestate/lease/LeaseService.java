package com.realestate.lease;

import java.util.List;

public interface LeaseService {

    Lease createRequest(
            LeaseRequest request);

    Lease getRequestById(Long id);

    List<Lease> getAllRequests();

    List<Lease> getRequestsByStatus(
            String status);

    Lease approveRequest(Long id);

    Lease rejectRequest(Long id);

    Lease updateStatus(Long id, String status);

    void deleteRequest(Long id);
}
