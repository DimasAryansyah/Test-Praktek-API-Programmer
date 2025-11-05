package com.example.thtbp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.thtbp.entity.ServiceEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    @Query(value = "SELECT * FROM services WHERE service_code = :serviceCode LIMIT 1", nativeQuery = true)
    ServiceEntity findByServiceCode(@Param("serviceCode") String serviceCode);
}
