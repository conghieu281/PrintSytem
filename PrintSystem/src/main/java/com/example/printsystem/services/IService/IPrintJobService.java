package com.example.printsystem.services.IService;

import com.example.printsystem.models.dto.PrintJobDTO;
import com.example.printsystem.models.entity.Design;
import com.example.printsystem.models.entity.PrintJobs;

import java.util.List;

public interface IPrintJobService {
    PrintJobs createPrintJob(PrintJobs printJobs, Design design);
    public void prepareForPrint(PrintJobDTO printJobDTO) throws Exception;
    PrintJobs updatePrintJob(PrintJobs printJob);
    void deletePrintJob(Long printJobId);
    PrintJobs getPrintJobById(Long printJobId) throws Exception;
    List<PrintJobs> getPrintJobsByDesignId(Long designId);
}
