package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
}
