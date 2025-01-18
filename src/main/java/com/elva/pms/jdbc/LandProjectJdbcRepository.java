package com.elva.pms.jdbc;

import com.elva.pms.pojo.dao.LandProject;
import com.elva.pms.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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

    public long insert(LandProject landProject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO land_project (" +
                "develop_id, " +
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
            ps.setLong(++index, landProject.getDevelopId());
            ps.setString(++index, landProject.getName());
            ps.setString(++index, landProject.getDescription());
            ps.setLong(++index, landProject.getLocationId());
            ps.setString(++index, landProject.getStatus().toString());
            try {
                ps.setString(++index, landProject.getMetadata() != null ? 
                    mapper.writeValueAsString(landProject.getMetadata()) : null);
            } catch (Exception e) {
                ps.setString(++index, null);
            }
            ps.setBoolean(++index, true);
            ps.setString(++index, landProject.getCreatedBy());
            ps.setString(++index, landProject.getCreatedBy());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void update(LandProject landProject) {
        String sql = "UPDATE land_project SET " +
                "develop_id = ?, " +
                "name = ?, " +
                "description = ?, " +
                "location_id = ?, " +
                "status = ?, " +
                "metadata = ?, " +
                "updated_at = CURRENT_TIMESTAMP, " +
                "updated_by = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                landProject.getDevelopId(),
                landProject.getName(),
                landProject.getDescription(),
                landProject.getLocationId(),
                landProject.getStatus().toString(),
                landProject.getMetadata(),
                landProject.getUpdatedBy(),
                landProject.getId()
        );
    }

    public LandProject findById(Long id) {
        try {
            String sql = "SELECT * FROM land_project WHERE id = ? AND is_active = true";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new LandProjectMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<LandProject> findAll() {
        String sql = "SELECT * FROM land_project WHERE is_active = true";
        return jdbcTemplate.query(sql, new LandProjectMapper());
    }

    public List<LandProject> findByFilter(String name, Long locationId, Long developId) {
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("SELECT * FROM land_project WHERE is_active = true");
        
        if (name != null && !name.trim().isEmpty()) {
            conditions.add("name LIKE ?");
            params.add("%" + name + "%");
        }
        
        if (locationId != null) {
            conditions.add("location_id = ?");
            params.add(locationId);
        }
        
        if (developId != null) {
            conditions.add("develop_id = ?");
            params.add(developId);
        }
        
        if (!conditions.isEmpty()) {
            sql.append(" AND ").append(String.join(" AND ", conditions));
        }
        
        sql.append(" ORDER BY created_at DESC");
        
        return jdbcTemplate.query(sql.toString(), params.toArray(), new LandProjectMapper());
    }

    private static class LandProjectMapper implements RowMapper<LandProject> {
        @Override
        public LandProject mapRow(ResultSet rs, int rowNum) throws SQLException {
            LandProject landProject = new LandProject();
            landProject.setId(rs.getLong("id"));
            landProject.setDevelopId(rs.getLong("develop_id"));
            landProject.setName(rs.getString("name"));
            landProject.setDescription(rs.getString("description"));
            landProject.setLocationId(rs.getLong("location_id"));
            landProject.setStatus(Status.valueOf(rs.getString("status")));
            landProject.setMetadata(rs.getString("metadata"));
            landProject.setActive(rs.getBoolean("is_active"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                landProject.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                landProject.setUpdatedAt(updatedAt.toLocalDateTime());
            }
            
            landProject.setCreatedBy(rs.getString("created_by"));
            landProject.setUpdatedBy(rs.getString("updated_by"));
            
            return landProject;
        }
    }
} 