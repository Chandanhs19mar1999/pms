package com.elva.pms.service;

import com.elva.pms.pojo.request.LandProjectFilterRequest;
import com.elva.pms.pojo.request.LandProjectRequest;
import com.elva.pms.pojo.response.LandProjectResponse;

import java.util.List;

public interface LandProjectService {
    LandProjectResponse createLandProject(LandProjectRequest request);

    LandProjectResponse updateLandProject(long projectId, LandProjectRequest request);

    List<LandProjectResponse> getLandProjectsByFilters(LandProjectFilterRequest filterRequest);



}
