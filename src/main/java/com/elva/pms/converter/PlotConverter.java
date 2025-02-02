package com.elva.pms.converter;

import com.elva.pms.enums.PlotStatus;
import com.elva.pms.pojo.dao.PlotDao;
import com.elva.pms.pojo.request.PlotRequest;
import com.elva.pms.pojo.response.PlotResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlotConverter {

    public static PlotDao convertRequestToDao(PlotRequest request) {
        PlotDao dao = new PlotDao();
        dao.setLandProjectId(request.getLandProjectId());
        dao.setPlotNumber(String.valueOf(request.getPlotNumber()));
        dao.setSize(request.getSize());
        dao.setPrice(request.getPrice());
        dao.setPlotStatus(PlotStatus.valueOf(request.getStatus()));
        dao.setMetadata(request.getMetadata());
        dao.setIsActive(true);
        dao.setCreatedBy(123L);
        dao.setUpdatedBy(123L);
        return dao;
    }

    public static List<PlotDao> convertRequestToDaoList(List<PlotRequest> plotRequestList) {
        List<PlotDao> plotDaoList = new ArrayList<>();
        for (PlotRequest request : plotRequestList) {
            PlotDao dao = convertRequestToDao(request);
            plotDaoList.add(dao);
        }
        return plotDaoList;
    }


    public static PlotResponse convertDaoToResponse(PlotDao plotDao) {
        return PlotResponse.builder()
                .id(plotDao.getId())
                .landProjectId(plotDao.getLandProjectId())
                .plotNumber(plotDao.getPlotNumber())
                .size(plotDao.getSize())
                .price(plotDao.getPrice())
                .plotStatus(plotDao.getPlotStatus())
                .metadata(plotDao.getMetadata())
                .createdBy(plotDao.getCreatedBy())
                .updatedBy(plotDao.getUpdatedBy())
                .createdAt(plotDao.getCreatedAt())
                .updatedAt(plotDao.getUpdatedAt())
                .isActive(plotDao.getIsActive())
                .build();
    }


    public static List<PlotResponse> getResponseList(List<PlotDao> daoList) {
        return daoList.stream().map(PlotConverter::convertDaoToResponse).collect(Collectors.toList());
    }
}
