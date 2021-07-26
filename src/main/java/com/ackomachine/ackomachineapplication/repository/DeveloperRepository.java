package com.ackomachine.ackomachineapplication.repository;

import com.ackomachine.ackomachineapplication.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
}
