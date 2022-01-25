package com.ninja.rmm.repository;

import com.ninja.rmm.model.Device;
import com.ninja.rmm.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByCustomerId(Long customerId);

    Optional<Service> findByIdAndCustomerId(Long id, Long customerId);

    List<Service> findByServiceName(String serviceName);
}