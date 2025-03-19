package com.example.lab2.repository;

import com.example.lab2.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContainsIgnoreCaseAndDescriptionContainsIgnoreCase(String name, String description);
}
