package com.elva.pms.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LandProjectFilterRequest {
    Long developerId;
    String name;
    Long projectId;
    Integer limit;
    Integer offset;
}
