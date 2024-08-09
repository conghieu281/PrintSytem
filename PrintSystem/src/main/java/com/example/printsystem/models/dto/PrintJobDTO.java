package com.example.printsystem.models.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrintJobDTO {
    private Long id;
    private Long designId;
    private List<ResourceForPrintJobDTO> resourceDetails;

    public PrintJobDTO(Long id) {
        this.id = id;
    }
}
