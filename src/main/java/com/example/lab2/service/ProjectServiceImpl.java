package com.example.lab2.service;

import com.example.lab2.dto.ProjectDTO;
import com.example.lab2.entity.Project;
import com.example.lab2.mapper.ProjectMapper;
import com.example.lab2.repository.ProjectRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<ProjectDTO> findAll(String search) {
        List<Project> projects;
        if (search != null) {
            projects = projectRepository.findByNameContainsIgnoreCaseAndDescriptionContainsIgnoreCase(search, search);
        } else {
            projects = projectRepository.findAll();
        }

        return projectMapper.toProjectDTO(projects);
    }

    @Override
    public Optional<ProjectDTO> findById(Long id) {
        return projectRepository.findById(id).map(projectMapper::toProjectDTO);
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO) {
        return projectMapper.toProjectDTO(projectRepository.save(projectMapper.toEntity(projectDTO)));
    }

    @Override
    public Optional<ProjectDTO> update(Long id, ProjectDTO projectDTO) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project existingProject = project.get();

            existingProject.setName(projectDTO.name());
            existingProject.setDescription(projectDTO.description());
            existingProject.setStartDate(projectDTO.startDate());
            existingProject.setEndDate(projectDTO.endDate());

            return Optional.of(projectMapper.toProjectDTO(projectRepository.save(existingProject)));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
    }

    @Override
    public Map<Long, Long> countUncompletedTasks() {
        return Map.of();
    }
}
