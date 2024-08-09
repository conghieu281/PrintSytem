package com.example.printsystem.services;

import com.example.printsystem.models.Enum.EDeliveryStatus;
import com.example.printsystem.models.entity.Customer;
import com.example.printsystem.models.entity.Delivery;
import com.example.printsystem.models.entity.Project;
import com.example.printsystem.models.entity.User;
import com.example.printsystem.models.repository.CustomerRepository;
import com.example.printsystem.models.repository.DeliveryRepository;
import com.example.printsystem.models.repository.ProjectRepository;
import com.example.printsystem.models.repository.UserRepository;
import com.example.printsystem.services.IService.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService implements IDeliveryService {
    @Autowired
    private DeliveryRepository _deliveryRepository;

    @Autowired
    private CustomerRepository _customerRepository;

    @Autowired
    private ProjectRepository _projectRepository;

    @Autowired
    private NotificationService _notificationService;

    @Autowired
    private EmailService _emailService;

    @Autowired
    private UserRepository _userRepository;

    @Override
    public List<Delivery> getAllDelivery() {
        return _deliveryRepository.findAll();
    }

    @Override
    public Delivery assignDeliveriesToShippers(Long deliveryId, Long shipperId) throws Exception {
        Delivery delivery = _deliveryRepository.findById(deliveryId).orElseThrow(() -> new ExceptionInInitializerError(("Delivery not found")));
        delivery.setDeliverId(shipperId);
        _notificationService.createNotify(shipperId,"You have to assigned a product!","");
        return delivery;
    }

    @Override
    public void confirmDelivery(Long deliveryId, boolean success) throws Exception {
        Optional<Delivery> optionalDelivery = _deliveryRepository.findById(deliveryId);
        if (optionalDelivery.isPresent()) {
            Delivery delivery = optionalDelivery.get();
            if (success) {
                delivery.setDeliveryStatus(EDeliveryStatus.DELIVERY_STATUS_DONE);
                delivery.setActualDeliveryTime(LocalDateTime.now());
                _deliveryRepository.save(delivery);

                // Thông báo thanh cong
                _emailService.sendDoneDeliveryEmail(delivery.getCustomerDelivery().getEmail());
                _notificationService.createNotify(delivery.getProjectDelivery().getEmployeeId(),"Done Delivery","");
                //User shipper = _userRepository.findById(delivery.getDeliverId()).orElseThrow(()-> new Exception(("User not found")));
                //_notificationService.createNotify(shipper.getTeam().getManagerId(),"Done delivery"+ deliveryId+ "!","");
            } else {
                delivery.setDeliveryStatus(EDeliveryStatus.DELIVERY_STATUS_CANCEL);
                _deliveryRepository.save(delivery);
            }
        }
    }

    @Override
    public Delivery createDelivery(Long projectId) throws Exception {
        Delivery delivery = new Delivery();
        Project project = _projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
        Customer customer = _customerRepository.findById(project.getCustomerId()).orElseThrow(() -> new Exception("Customer not found"));
        delivery.setDeliveryAddress(customer.getAddress());
        delivery.setCustomerDelivery(customer);
        delivery.setProjectDelivery(project);
        delivery.setCustomerId(customer.getId());
        delivery.setProjectId(projectId);
        delivery.setDeliveryStatus(EDeliveryStatus.DELIVERY_STATUS_PENDING);
        delivery.setEstimateDeliveryTime(LocalDateTime.now().plusDays(2));
        return _deliveryRepository.save(delivery);
    }

    //    private Map<Shipper, List<Delivery>> optimizeDeliveryAssignments(List<Delivery> deliveries, List<Shipper> shippers) throws Exception {
//        Map<Shipper, List<Delivery>> assignments = new HashMap<>();
//        DeliveryOptimizer optimizer = new DeliveryOptimizer();
//        List<List<Delivery>> clusters = optimizer.performClustering(deliveries, shippers.size());
//
//        for (int i = 0; i < shippers.size(); i++) {
//            Shipper shipper = shippers.get(i);
//            List<Delivery> deliveriesForShipper = clusters.get(i);
//            assignments.put(shipper, deliveriesForShipper);
//        }
//
//        return assignments;
//    }

//    private void assignDeliveriesToProjects(Map<Shipper, List<Delivery>> shipperAssignments, List<Project> projects) {
//        for (Map.Entry<Shipper, List<Delivery>> entry : shipperAssignments.entrySet()) {
//            List<Delivery> deliveries = entry.getValue();
//            Shipper shipper = entry.getKey();
//
//            // Ví dụ đơn giản: phân công cho dự án đầu tiên
//            Project project = projects.get(0); // Lấy dự án đầu tiên (có thể cần logic phân công dự án chi tiết hơn)
//            for (Delivery delivery : deliveries) {
//                delivery.setProjectDelivery(project);
//                _deliveryRepository.save(delivery);
//            }
//        }
//    }


    @Override
    public List<Delivery> getAllDeliveryByProjectId(Long projectId) {
        return _deliveryRepository.findAllByProjectId(projectId);
    }
}
