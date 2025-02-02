package com.elva.pms.converter;

import com.elva.pms.enums.LandProjectStatus;
import com.elva.pms.pojo.dao.LandProjectDao;
import com.elva.pms.pojo.request.LandProjectRequest;
import com.elva.pms.pojo.response.LandProjectResponse;

import java.util.List;
import java.util.stream.Collectors;

public class LandProjectConverter {

    public static LandProjectDao convertRequestToDao(LandProjectRequest request) {
        LandProjectDao dao = new LandProjectDao();
        dao.setName(request.getName());
        dao.setDescription(request.getDescription());
        dao.setStatus(LandProjectStatus.valueOf(request.getStatus()));
        dao.setLocationId(request.getLocationId());
        dao.setDeveloperId(request.getDeveloperId());
        dao.setMetadata(request.getMetadata());
        dao.setIsActive(true);
        dao.setCreatedBy(123L);
        dao.setUpdatedBy(123L);
        return dao;
    }

    public static LandProjectResponse convertDaoToResponse(LandProjectDao landProjectDao) {
        return LandProjectResponse.builder()
                .id(landProjectDao.getId())
                .developerId(landProjectDao.getDeveloperId())
                .name(landProjectDao.getName())
                .status(landProjectDao.getStatus())
                .description(landProjectDao.getDescription())
                .totalPlots(landProjectDao.getTotalPlots())
                .locationId(landProjectDao.getLocationId())
                .metadata(landProjectDao.getMetadata())
                .createdBy(landProjectDao.getCreatedBy())
                .updatedBy(landProjectDao.getUpdatedBy())
                .createdAt(landProjectDao.getCreatedAt())
                .updatedAt(landProjectDao.getUpdatedAt())
                .isActive(landProjectDao.getIsActive())
                .build();
    }


    public static List<LandProjectResponse> getResponseList(List<LandProjectDao> daoList) {
        return daoList.stream().map(LandProjectConverter::convertDaoToResponse).collect(Collectors.toList());
    }

}
