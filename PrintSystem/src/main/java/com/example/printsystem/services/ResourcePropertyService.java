package com.example.printsystem.services;

import com.example.printsystem.models.entity.ResourceProperty;
import com.example.printsystem.models.entity.Resources;
import com.example.printsystem.models.repository.ResourcePropertyRepository;
import com.example.printsystem.models.repository.ResourceRepository;
import com.example.printsystem.services.IService.IResourcePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourcePropertyService implements IResourcePropertyService {
    @Autowired
    private ResourcePropertyRepository _resourcePropertyRepository;

    @Autowired
    private ResourceRepository _resourceRepository;

    @Override
    public ResourceProperty createResourceProperty(ResourceProperty resourceProperty) throws Exception {
        Resources resources = _resourceRepository.findById(resourceProperty.getResourceId()).orElseThrow(() -> new Exception("Resource not found"));
        resourceProperty.setResourceProperty(resources);
        return _resourcePropertyRepository.save(resourceProperty);
    }
}
