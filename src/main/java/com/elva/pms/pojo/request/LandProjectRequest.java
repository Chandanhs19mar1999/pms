package com.elva.pms.pojo.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LandProjectRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @NotNull(message = "Location ID is required")
    private Long locationId;
    @NotNull(message = "Developer ID is required")
    private Long developId;
    private String metadata;
} 