package com.elva.pms.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotRequest {
    @NotNull(message = "Land Project ID cannot be empty")
    private Long landProjectId;
    
    @NotBlank(message = "Plot Number cannot be empty")
    private String plotNumber;
    
    @NotNull(message = "Size cannot be empty")
    private Double size;
    
    @NotNull(message = "Price cannot be empty")
    private BigDecimal price;
    
    private String metadata;
    
    @NotBlank(message = "Status cannot be empty")
    private String status;
}