package com.elva.pms.pojo.request;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class PlotRequest {
    @NotNull(message = "Land Project ID is required")
    private Long landProjectId;
    
    @NotNull(message = "Plot number is required")
    private String plotNumber;
    
    @Positive(message = "Size must be positive")
    private Double size;
    
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    private String metadata;
} 