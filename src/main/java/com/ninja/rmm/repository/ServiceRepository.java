package com.ninja.rmm.repository;

import com.ninja.rmm.model.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

  List<Service> findByCustomerId(Long customerId);

  Optional<Service> findByIdAndCustomerId(Long id, Long customerId);
}
