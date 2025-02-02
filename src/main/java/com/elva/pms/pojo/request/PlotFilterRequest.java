package com.elva.pms.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotFilterRequest {
    Long plotId;
    String plotNumber;
    Long landProjectId;
    String status;
    Integer limit;
    Integer offset;
}
