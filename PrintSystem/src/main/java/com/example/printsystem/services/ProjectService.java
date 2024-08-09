package com.example.printsystem.services;

import com.example.printsystem.models.Enum.EDesignStatus;
import com.example.printsystem.models.Enum.EProjectStatus;
import com.example.printsystem.models.Enum.ERoles;
import com.example.printsystem.models.entity.Design;
import com.example.printsystem.models.entity.Project;
import com.example.printsystem.models.entity.Team;
import com.example.printsystem.models.entity.User;
import com.example.printsystem.models.repository.DesignRepository;
import com.example.printsystem.models.repository.ProjectRepository;
import com.example.printsystem.models.repository.TeamRepository;
import com.example.printsystem.models.repository.UserRepository;
import com.example.printsystem.services.IService.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository _projectRepository;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private TeamRepository _teamRepository;

    @Override
    public List<Project> getAllProjects() {
        return _projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) throws Exception {
        return _projectRepository.findById(id);
    }

    @Transactional
    @Override
    public Project createProject(Project project) throws Exception {
        User employee = _userRepository.findById(project.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + project.getEmployeeId()));
        // Check if the user has the role "EMPLOYEE" and belongs to the "SALES" team
        boolean isAuthorized = false;
        if(employee.getPermissions().stream()
                .anyMatch(permission -> permission.getRolePermission().getRoleName() == ERoles.ROLE_EMPLOYEE) ) {
            System.out.println(employee.getTeamId());
            Team team = _teamRepository.findById(employee.getTeamId()).orElseThrow(() -> new Exception("Team not found"));
            if (team.getName().contains("Sales")){
                isAuthorized = true;
            }
        }

        if (!isAuthorized) {
            throw new Exception("User is not authorized to create a project");
        }

        project.setUserProject(employee);
        project.setStartDate(LocalDateTime.now());
        project.setProjectStatus(EProjectStatus.PROJECT_STATUS_DESIGNING);
        return _projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return null;
    }

    @Override
    public void deleteProject(Long projectId) {
        _projectRepository.deleteById(projectId);
    }

}
