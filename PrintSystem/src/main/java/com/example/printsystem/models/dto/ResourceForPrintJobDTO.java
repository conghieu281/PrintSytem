package com.example.printsystem.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceForPrintJobDTO {
    private Long resourceDetailId;
    private Integer quantity;
}
