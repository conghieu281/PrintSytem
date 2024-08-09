package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Delivery;
import com.example.printsystem.models.entity.Project;

import java.util.List;

public interface IDeliveryService {
    List<Delivery> getAllDelivery();
    List<Delivery> getAllDeliveryByProjectId(Long projectId);
    public Delivery assignDeliveriesToShippers(Long deliveryId, Long shipperId) throws Exception;
    public void confirmDelivery(Long deliveryId, boolean success) throws Exception;
    public Delivery createDelivery(Long projectId) throws Exception;
}
