package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.ResourcePropertyDetail;

import java.util.List;

public interface IResourcePropertyDetailService {
    List<ResourcePropertyDetail> getAllDetail();
    ResourcePropertyDetail createResourcePropertyDetail(ResourcePropertyDetail detail) throws Exception;
    List<ResourcePropertyDetail> getAllDetailByResourceId(Long id) throws Exception;
}

