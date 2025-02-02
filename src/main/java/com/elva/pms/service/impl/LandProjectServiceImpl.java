package com.elva.pms.service.impl;

import com.elva.pms.converter.LandProjectConverter;
import com.elva.pms.pojo.dao.LandProjectDao;
import com.elva.pms.pojo.request.LandProjectFilterRequest;
import com.elva.pms.pojo.request.LandProjectRequest;
import com.elva.pms.pojo.response.LandProjectResponse;
import com.elva.pms.jdbc.LandProjectJdbcRepository;
import com.elva.pms.service.LandProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LandProjectServiceImpl implements LandProjectService {

    @Autowired
    private LandProjectJdbcRepository landProjectRepository;
    
    @Transactional
    public LandProjectResponse createLandProject(LandProjectRequest request) {
        LandProjectDao landProjectDao = LandProjectConverter.convertRequestToDao(request);
        long projectId = landProjectRepository.insert(landProjectDao);
        landProjectDao.setId(projectId);
        return LandProjectConverter.convertDaoToResponse(landProjectDao);
    }

    @Override
    public LandProjectResponse updateLandProject(long projectId, LandProjectRequest request) {
        LandProjectDao landProjectDao = LandProjectConverter.convertRequestToDao(request);
        landProjectDao.setId(projectId);
        landProjectRepository.update(landProjectDao);
        return LandProjectConverter.convertDaoToResponse(landProjectDao);
    }

    @Override
    public List<LandProjectResponse> getLandProjectsByFilters(LandProjectFilterRequest filterRequest) {
        List<LandProjectDao> landProjectDaoList = landProjectRepository.getProjectsByFilter(filterRequest);
        if (landProjectDaoList != null) {
            return LandProjectConverter.getResponseList(landProjectDaoList);
        }
        return null;
    }
} 