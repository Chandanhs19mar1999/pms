package com.elva.pms.jdbc;

import com.elva.pms.pojo.dao.PlotDao;
import com.elva.pms.enums.PlotStatus;
import com.elva.pms.pojo.request.PlotFilterRequest;
import com.elva.pms.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlotJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper;

    public List<Long> bulkInsert(List<PlotDao> plotDaos) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO plot (" +
                "land_project_id, " +
                "plot_number, " +
                "size, " +
                "price, " +
                "status, " +
                "metadata, " +
                "is_active, " +
                "created_at, " +
                "created_by, " +
                "updated_at, " +
                "updated_by) " +
                "VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";

        List<Long> generatedIds = new ArrayList<>();

        for (PlotDao plotDao : plotDaos) {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                int index = 0;
                ps.setLong(++index, plotDao.getLandProjectId());
                ps.setString(++index, plotDao.getPlotNumber());
                ps.setDouble(++index, plotDao.getSize());
                ps.setBigDecimal(++index, plotDao.getPrice());
                ps.setString(++index, plotDao.getPlotStatus().toString());
                try {
                    ps.setString(++index, plotDao.getMetadata() != null ?
                        mapper.writeValueAsString(plotDao.getMetadata()) : null);
                } catch (Exception e) {
                    ps.setString(++index, null);
                }
                ps.setBoolean(++index, true);
                ps.setLong(++index, plotDao.getCreatedBy());
                ps.setLong(++index, plotDao.getCreatedBy());
                return ps;
            }, keyHolder);

            generatedIds.add(keyHolder.getKey().longValue());
        }

        return generatedIds;
    }

    public void update(PlotDao plotDao) {
        String sql = "UPDATE plot SET " +
                "land_project_id = ?, " +
                "plot_number = ?, " +
                "size = ?, " +
                "price = ?, " +
                "status = ?, " +
                "metadata = ?, " +
                "updated_at = CURRENT_TIMESTAMP, " +
                "updated_by = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                plotDao.getLandProjectId(),
                plotDao.getPlotNumber(),
                plotDao.getSize(),
                plotDao.getPrice(),
                plotDao.getPlotStatus().toString(),
                plotDao.getMetadata(),
                plotDao.getUpdatedBy(),
                plotDao.getId()
        );
    }

    public List<PlotDao> getPlotsByFilter(PlotFilterRequest filterRequest) {
        String sql = "SELECT * FROM plot";

        List<String> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        String whereQuery = generateWhereQuery(filterRequest, keys, values);

        String limitQuery = CommonUtils.generateLimitQuery(filterRequest.getLimit(), filterRequest.getOffset(), values);

        String finalQuery = String.join(" ", sql, whereQuery, limitQuery);
        try {
            return jdbcTemplate.query(finalQuery, values.toArray(), new PlotJdbcRepository.PlotMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private String generateWhereQuery(PlotFilterRequest filterRequest, List<String> keys, List<Object> values) {
        keys.add("is_active = true");
        if (filterRequest.getPlotId() != null) {
            keys.add("id = ?");
            values.add(filterRequest.getPlotId());
        }
        if (filterRequest.getPlotNumber() != null) {
            keys.add("plot_number = ?");
            values.add(filterRequest.getPlotNumber());
        }
        if (filterRequest.getLandProjectId() != null) {
            keys.add("land_project_id = ?");
            values.add(filterRequest.getLandProjectId());
        }

        if (filterRequest.getStatus() != null) {
            keys.add("status = ?");
            values.add(filterRequest.getStatus());
        }
        return !keys.isEmpty() ? "WHERE " + String.join(" AND ", keys) : "";
    }



    private static class PlotMapper implements RowMapper<PlotDao> {
        @Override
        public PlotDao mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlotDao plotDao = new PlotDao();
            plotDao.setId(rs.getLong("id"));
            plotDao.setLandProjectId(rs.getLong("land_project_id"));
            plotDao.setPlotNumber(rs.getString("plot_number"));
            plotDao.setSize(rs.getDouble("size"));
            plotDao.setPrice(rs.getBigDecimal("price"));
            plotDao.setPlotStatus(PlotStatus.valueOf(rs.getString("status")));
            plotDao.setMetadata(rs.getString("metadata"));
            plotDao.setIsActive(rs.getBoolean("is_active"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                plotDao.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                plotDao.setUpdatedAt(updatedAt.toLocalDateTime());
            }
            
            plotDao.setCreatedBy(rs.getLong("created_by"));
            plotDao.setUpdatedBy(rs.getLong("updated_by"));
            
            return plotDao;
        }
    }
} 