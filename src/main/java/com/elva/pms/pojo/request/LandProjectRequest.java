package com.elva.pms.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandProjectRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    
    @NotBlank(message = "Description cannot be empty") 
    private String description;
    
    @NotNull(message = "Location ID cannot be empty")
    private Long locationId;
    
    @NotNull(message = "Developer ID cannot be empty")
    private Long developerId;
    
    @NotBlank(message = "Status cannot be empty")
    private String status;
    
    private String metadata;
}