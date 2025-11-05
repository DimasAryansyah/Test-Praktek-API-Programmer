package com.example.thtbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thtbp.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    
}
