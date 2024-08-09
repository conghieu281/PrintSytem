package com.example.printsystem.services;

import com.example.printsystem.models.entity.ResourceProperty;
import com.example.printsystem.models.entity.ResourcePropertyDetail;
import com.example.printsystem.models.entity.Resources;
import com.example.printsystem.models.repository.ResourcePropertyDetailRepository;
import com.example.printsystem.models.repository.ResourcePropertyRepository;
import com.example.printsystem.models.repository.ResourceRepository;
import com.example.printsystem.services.IService.IResourcePropertyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcePropertyDetailService implements IResourcePropertyDetailService {
    @Autowired
    private ResourcePropertyDetailRepository _resourcePropertyDetailRepository;

    @Autowired
    private ResourcePropertyRepository _resourcePropertyRepository;

    @Autowired
    private ResourceRepository _resourceRepository;

    @Override
    public ResourcePropertyDetail createResourcePropertyDetail(ResourcePropertyDetail detail) throws Exception {
        ResourceProperty property = _resourcePropertyRepository.findById(detail.getPropertyId()).orElseThrow(() -> new Exception(("Property not found")));
        detail.setPropertyDetail(property);
        return _resourcePropertyDetailRepository.save(detail);
    }

    @Override
    public List<ResourcePropertyDetail> getAllDetailByResourceId(Long id) throws Exception {
        Resources resources = _resourceRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
        List<ResourcePropertyDetail> resourcePropertyDetails = new ArrayList<>();
        for (ResourceProperty property : resources.getPropertyList()) {
            resourcePropertyDetails.addAll(_resourcePropertyDetailRepository.getAllByPropertyId(property.getId()));
        }
        return resourcePropertyDetails;
    }

    @Override
    public List<ResourcePropertyDetail> getAllDetail() {
        return _resourcePropertyDetailRepository.findAll();
    }
}
