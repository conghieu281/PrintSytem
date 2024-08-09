package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Resources;

import java.util.List;

public interface IResourceService {
    Resources createResource(Resources resource);
    List<Resources> getAllResource();
}
