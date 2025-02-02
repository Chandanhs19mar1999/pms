package com.elva.pms.jdbc;

import com.elva.pms.enums.LandProjectStatus;
import com.elva.pms.pojo.dao.LandProjectDao;
import com.elva.pms.enums.PlotStatus;
import com.elva.pms.pojo.request.LandProjectFilterRequest;
import com.elva.pms.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
public class LandProjectJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper;

    public long insert(LandProjectDao landProjectDao) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO land_project (" +
                "developer_id, " +
                "name, " +
                "description, " +
                "location_id, " +
                "status, " +
                "metadata, " +
                "is_active, " +
                "created_at, " +
                "created_by, " +
                "updated_at, " +
                "updated_by) " +
                "VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            int index = 0;
            ps.setLong(++index, landProjectDao.getDeveloperId());
            ps.setString(++index, landProjectDao.getName());
            ps.setString(++index, landProjectDao.getDescription());
            ps.setLong(++index, landProjectDao.getLocationId());
            ps.setString(++index, landProjectDao.getStatus().toString());
            try {
                ps.setString(++index, landProjectDao.getMetadata() != null ? 
                    mapper.writeValueAsString(landProjectDao.getMetadata()) : null);
            } catch (Exception e) {
                ps.setString(++index, null);
            }
            ps.setBoolean(++index, true);
            ps.setLong(++index, landProjectDao.getCreatedBy());
            ps.setLong(++index, landProjectDao.getCreatedBy());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void update(LandProjectDao landProjectDao) {
        String sql = "UPDATE land_project SET " +
                "developer_id = ?, " +
                "name = ?, " +
                "description = ?, " +
                "location_id = ?, " +
                "status = ?, " +
                "metadata = ?, " +
                "updated_at = CURRENT_TIMESTAMP, " +
                "updated_by = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                landProjectDao.getDeveloperId(),
                landProjectDao.getName(),
                landProjectDao.getDescription(),
                landProjectDao.getLocationId(),
                landProjectDao.getStatus().toString(),
                landProjectDao.getMetadata(),
                landProjectDao.getUpdatedBy(),
                landProjectDao.getId()
        );
    }

    public List<LandProjectDao> getProjectsByFilter(LandProjectFilterRequest filterRequest) {
        String sql = "SELECT lp.*, COUNT(p.id) AS total_plots " +
                "FROM land_project lp LEFT JOIN plot p ON p.land_project_id = lp.id";

        List<String> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        String whereQuery = generateWhereQuery(filterRequest, keys, values);

        String groupQuery = "GROUP BY lp.id";

        String limitQuery = CommonUtils.generateLimitQuery(filterRequest.getLimit(), filterRequest.getOffset(), values);

        String finalQuery = String.join(" ", sql, whereQuery, groupQuery, limitQuery);

        try {
            return jdbcTemplate.query(finalQuery, values.toArray(), new LandProjectJdbcRepository.LandProjectMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private String generateWhereQuery(LandProjectFilterRequest filterRequest, List<String> keys, List<Object> values) {
        keys.add(" lp.is_active = true ");

        if (filterRequest.getProjectId() != null) {
            keys.add(" lp.id = ?");
            values.add(filterRequest.getProjectId());
        }

        if (filterRequest.getDeveloperId() != null) {
            keys.add(" lp.developer_id = ?");
            values.add(filterRequest.getDeveloperId());
        }
        if (filterRequest.getName() != null) {
            keys.add("lp.name LIKE ?");
            values.add('%' + filterRequest.getName() + '%');
        }
        return !keys.isEmpty() ? "WHERE " + String.join(" AND ", keys) : "";
    }


    private static class LandProjectMapper implements RowMapper<LandProjectDao> {
        @Override
        public LandProjectDao mapRow(ResultSet rs, int rowNum) throws SQLException {
            LandProjectDao landProjectDao = new LandProjectDao();
            landProjectDao.setId(rs.getLong("id"));
            landProjectDao.setDeveloperId(rs.getLong("developer_id"));
            landProjectDao.setName(rs.getString("name"));
            landProjectDao.setDescription(rs.getString("description"));
            landProjectDao.setLocationId(rs.getLong("location_id"));
            landProjectDao.setStatus(LandProjectStatus.valueOf(rs.getString("status")));
            landProjectDao.setMetadata(rs.getString("metadata"));
            landProjectDao.setIsActive(rs.getBoolean("is_active"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                landProjectDao.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                landProjectDao.setUpdatedAt(updatedAt.toLocalDateTime());
            }
            landProjectDao.setCreatedBy(rs.getLong("created_by"));
            landProjectDao.setUpdatedBy(rs.getLong("updated_by"));
            landProjectDao.setTotalPlots(rs.getLong("total_plots"));
            
            return landProjectDao;
        }
    }
} 