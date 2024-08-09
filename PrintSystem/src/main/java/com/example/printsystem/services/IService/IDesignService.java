package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Design;
import com.example.printsystem.models.entity.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDesignService {

    Design addDesign(Long projectId, Long designerId, MultipartFile file) throws Exception;

    Design updateDesign(Design design);
    void deleteDesign(Long designId);
    Design getDesignById(Long designId) throws Exception;
    List<Design> getDesignsByProjectId(Long projectId);
    Design approveDesign(Long projectId, Long designId, Long leaderId) throws Exception;
    Design rejectDesign(Long projectId, Long designId, Long leaderId) throws Exception;
}
