package com.realestate.repositories;

import com.realestate.lease.Lease;
import com.realestate.lease.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseRepo extends JpaRepository<Lease, Long> {

    List<Lease> findByStatus(
            RequestStatus status);
}
