package com.ninja.rmm.repository;

import com.ninja.rmm.model.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

  List<Device> findByCustomerId(Long customersId);

  Optional<Device> findByIdAndCustomerId(Long id, Long customerId);
}