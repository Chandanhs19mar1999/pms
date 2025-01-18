package com.elva.pms.jdbc;

import com.elva.pms.pojo.dao.Plot;
import com.elva.pms.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlotJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper;

    public long insert(Plot plot) {
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

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            int index = 0;
            ps.setLong(++index, plot.getLandProjectId());
            ps.setString(++index, plot.getPlotNumber());
            ps.setDouble(++index, plot.getSize());
            ps.setBigDecimal(++index, plot.getPrice());
            ps.setString(++index, plot.getStatus().toString());
            try {
                ps.setString(++index, plot.getMetadata() != null ? 
                    mapper.writeValueAsString(plot.getMetadata()) : null);
            } catch (Exception e) {
                ps.setString(++index, null);
            }
            ps.setBoolean(++index, true);
            ps.setString(++index, plot.getCreatedBy());
            ps.setString(++index, plot.getCreatedBy());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void update(Plot plot) {
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
                plot.getLandProjectId(),
                plot.getPlotNumber(),
                plot.getSize(),
                plot.getPrice(),
                plot.getStatus().toString(),
                plot.getMetadata(),
                plot.getUpdatedBy(),
                plot.getId()
        );
    }

    public List<Plot> findAll() {
        String sql = "SELECT * FROM plot WHERE is_active = true";
        return jdbcTemplate.query(sql, new PlotMapper());
    }

    public List<Plot> findByLandProjectId(Long landProjectId) {
        String sql = "SELECT * FROM plot WHERE land_project_id = ? AND is_active = true";
        return jdbcTemplate.query(sql, new Object[]{landProjectId}, new PlotMapper());
    }

    private static class PlotMapper implements RowMapper<Plot> {
        @Override
        public Plot mapRow(ResultSet rs, int rowNum) throws SQLException {
            Plot plot = new Plot();
            plot.setId(rs.getLong("id"));
            plot.setLandProjectId(rs.getLong("land_project_id"));
            plot.setPlotNumber(rs.getString("plot_number"));
            plot.setSize(rs.getDouble("size"));
            plot.setPrice(rs.getBigDecimal("price"));
            plot.setStatus(Status.valueOf(rs.getString("status")));
            plot.setMetadata(rs.getString("metadata"));
            plot.setActive(rs.getBoolean("is_active"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                plot.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                plot.setUpdatedAt(updatedAt.toLocalDateTime());
            }
            
            plot.setCreatedBy(rs.getString("created_by"));
            plot.setUpdatedBy(rs.getString("updated_by"));
            
            return plot;
        }
    }
} 