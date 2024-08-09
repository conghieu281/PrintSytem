package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Project;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    public List<Project> getAllProjects();

    public Optional<Project> getProjectById(Long id) throws Exception;

    public Project createProject(Project project) throws Exception;
    Project updateProject(Project project);
    void deleteProject(Long projectId);

}
