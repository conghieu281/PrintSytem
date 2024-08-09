package com.example.printsystem.services;

import com.example.printsystem.models.entity.Resources;
import com.example.printsystem.models.repository.ResourceRepository;
import com.example.printsystem.services.IService.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService implements IResourceService {
    @Autowired
    private ResourceRepository _resourcesRepository;

    @Override
    public Resources createResource(Resources resource) {
        return _resourcesRepository.save(resource);
    }

    @Override
    public List<Resources> getAllResource() {
        return _resourcesRepository.findAll();
    }
}
