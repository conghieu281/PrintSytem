package com.example.printsystem.services;

import com.example.printsystem.models.Enum.EPrintJob;
import com.example.printsystem.models.Enum.EResourceType;
import com.example.printsystem.models.dto.PrintJobDTO;
import com.example.printsystem.models.dto.ResourceForPrintJobDTO;
import com.example.printsystem.models.entity.*;
import com.example.printsystem.models.repository.*;
import com.example.printsystem.services.IService.IPrintJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrintJobService implements IPrintJobService {
    @Autowired
    private PrintJobRepository _printJobRepository;

    @Autowired
    private DesignRepository _designRepository;

    @Autowired
    private ResourcePropertyDetailRepository _resourcePropertyDetailRepository;

    @Autowired
    private ResourcePropertyRepository _resourcePropertyRepository;

    @Autowired
    private ResourceRepository _resourceRepository;


    @Override
    public PrintJobs createPrintJob(PrintJobs printJob, Design design) {
        printJob.setDesignPrintJob(design);
        printJob.setPrintJobStatus(EPrintJob.PRINT_JOB_PENDING);
        return _printJobRepository.save(printJob);
    }

    @Override
    public void prepareForPrint(PrintJobDTO printJobDTO) throws Exception {

        // Create print job
        PrintJobs printJob = _printJobRepository.findById(printJobDTO.getId()).orElseThrow(() -> new Exception("Print job not found"));
        printJob.setPrintJobStatus(EPrintJob.PRINT_JOB_PRINTING);

        // Process resources
        for (ResourceForPrintJobDTO resourceRequest : printJobDTO.getResourceDetails()) {
            ResourcePropertyDetail resourceDetail = _resourcePropertyDetailRepository.findById(resourceRequest.getResourceDetailId()).orElseThrow(() -> new Exception("Resource detail not found"));

            if(resourceRequest.getQuantity() > resourceDetail.getQuantity()) {
                throw new Exception("Invalid quantity");
            }
            // Decrement quantity if necessary
            else  if (resourceDetail.getPropertyDetail().getResourceProperty().getResourceType() == EResourceType.RESOURCE_TYPE_CONSUMABLE) {
                resourceDetail.setQuantity(resourceDetail.getQuantity() - resourceRequest.getQuantity());
                _resourcePropertyDetailRepository.save(resourceDetail);
            }


            // Cập nhật số lượng của ResourceProperty
            ResourceProperty resourceProperty = resourceDetail.getPropertyDetail();
            int totalQuantity = resourceProperty.getPropertyDetails().stream()
                    .mapToInt(ResourcePropertyDetail::getQuantity)
                    .sum();
            resourceProperty.setQuantity(totalQuantity);
            _resourcePropertyRepository.save(resourceProperty);

            // Cập nhật số lượng của Resource
            Resources resource = resourceProperty.getResourceProperty();
            int totalResourceQuantity = resource.getPropertyList().stream()
                    .mapToInt(ResourceProperty::getQuantity)
                    .sum();
            resource.setAvailableQuantity(totalResourceQuantity);
            _resourceRepository.save(resource);
        }

    }

    @Override
    public PrintJobs updatePrintJob(PrintJobs printJob) {
        return _printJobRepository.save(printJob);
    }

    @Override
    public void deletePrintJob(Long printJobId) {
        _printJobRepository.deleteById(printJobId);
    }

    @Override
    public PrintJobs getPrintJobById(Long printJobId) throws Exception {
        return _printJobRepository.findById(printJobId).orElseThrow(() -> new Exception("Print job not found"));
    }

    @Override
    public List<PrintJobs> getPrintJobsByDesignId(Long designId) {
        return _printJobRepository.findAllByDesignId(designId);
    }
}
