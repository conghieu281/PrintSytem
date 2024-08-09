package com.example.printsystem.services;

import com.example.printsystem.models.Enum.EDesignStatus;
import com.example.printsystem.models.Enum.EProjectStatus;
import com.example.printsystem.models.entity.Design;
import com.example.printsystem.models.entity.Project;
import com.example.printsystem.models.entity.User;
import com.example.printsystem.models.repository.DesignRepository;
import com.example.printsystem.models.repository.ProjectRepository;
import com.example.printsystem.models.repository.UserRepository;
import com.example.printsystem.services.IService.IDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Map;

@Service
public class DesignService implements IDesignService {
    @Autowired
    private DesignRepository _designRepository;

    @Autowired
    private ProjectRepository _projectRepository;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private FileUploadService _fileUploadService;

    @Override
    public Design addDesign(Long projectId, Long designerId, MultipartFile file) throws Exception {
        Project project = _projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
        User designer = _userRepository.findById(designerId).orElseThrow(() -> new Exception("User not found"));
        if (project.getProjectStatus().equals(EProjectStatus.PROJECT_STATUS_PRINTING)) {
            throw new Exception("Project in printing process. Can not create new design");
        }
        Design design = new Design();
        design.setProjectDesign(project);
        design.setProjectId(projectId);
        design.setDesignerId(designerId);
        design.setUserDesign(designer);
        Map uploadResult = _fileUploadService.uploadFile(file);
        String fileUrl = (String) uploadResult.get("url");

        design.setFilePath(fileUrl);
        design.setDesignStatus(EDesignStatus.DESIGN_STATUS_PENDING);
        return _designRepository.save(design);
    }

    @Override
    public Design updateDesign(Design design) {
        return _designRepository.save(design);
    }

    @Override
    public void deleteDesign(Long designId) {
        _designRepository.deleteById(designId);
    }

    @Override
    public Design getDesignById(Long designId) throws Exception {
        return _designRepository.findById(designId).orElseThrow(() -> new Exception("Design not found"));
    }

    @Override
    public List<Design> getDesignsByProjectId(Long projectId) {
        return _designRepository.getAllByProjectId(projectId);
    }

    @Override
    public Design approveDesign(Long projectId, Long designId, Long userId) throws Exception {
        Project project = _projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project has not found"));
        Design design = _designRepository.findById(designId).orElseThrow(() -> new Exception("Design has not found"));

        if (!project.getUserProject().getId().equals(userId)) {
            throw new AuthenticationException("Only the project leader can approve the design");
        }

        design.setDesignStatus(EDesignStatus.DESIGN_STATUS_APPROVED);
        design.setApproveId(project.getEmployeeId());
        _designRepository.save(design);

        project.setProjectStatus(EProjectStatus.PROJECT_STATUS_PRINTING);
        _projectRepository.save(project);

        //notificationService.notifyDesigner(design.getUserDesign().getId(), "Your design has been approved.");

        return design;
    }

    @Override
    public Design rejectDesign(Long projectId, Long designId, Long userId) throws Exception {
        Project project = _projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project has not found"));
        Design design = _designRepository.findById(designId).orElseThrow(() -> new Exception("Design has not found"));

        if (!project.getUserProject().getId().equals(userId)) {
            throw new AuthenticationException("Only the project leader can reject the design");
        }

        design.setDesignStatus(EDesignStatus.DESIGN_STATUS_CANCEL);
        _designRepository.save(design);

        //notificationService.notifyDesigner(design.getUserDesign().getId(), "Your design has been rejected.");

        return design;
    }
}
