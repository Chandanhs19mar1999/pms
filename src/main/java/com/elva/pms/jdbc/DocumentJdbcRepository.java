package com.elva.pms.jdbc;

import com.elva.pms.pojo.dao.Document;
import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
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
public class DocumentJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public long insert(Document document) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO document_management (" +
                "entity_id, " +
                "entity_type, " +
                "file_category, " +
                "file_name, " +
                "file_path, " +
                "is_active, " +
                "created_at, " +
                "created_by, " +
                "updated_at, " +
                "updated_by) " +
                "VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            int index = 0;
            ps.setLong(++index, document.getEntityId());
            ps.setString(++index, document.getEntityType().toString());
            ps.setString(++index, document.getFileCategory().toString());
            ps.setString(++index, document.getFileName());
            ps.setString(++index, document.getFilePath());
            ps.setBoolean(++index, true);
            ps.setString(++index, document.getCreatedBy());
            ps.setString(++index, document.getCreatedBy());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Document> findByEntityIdAndType(Long entityId, EntityType entityType) {
        String sql = "SELECT * FROM document_management " +
                "WHERE entity_id = ? AND entity_type = ? AND is_active = true";
        
        return jdbcTemplate.query(sql, new Object[]{entityId, entityType.toString()}, 
            new DocumentMapper());
    }

    private static class DocumentMapper implements RowMapper<Document> {
        @Override
        public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
            Document document = new Document();
            document.setId(rs.getLong("id"));
            document.setEntityId(rs.getLong("entity_id"));
            document.setEntityType(EntityType.valueOf(rs.getString("entity_type")));
            document.setFileCategory(FileCategory.valueOf(rs.getString("file_category")));
            document.setFileName(rs.getString("file_name"));
            document.setFilePath(rs.getString("file_path"));
            document.setActive(rs.getBoolean("is_active"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                document.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                document.setUpdatedAt(updatedAt.toLocalDateTime());
            }
            
            document.setCreatedBy(rs.getString("created_by"));
            document.setUpdatedBy(rs.getString("updated_by"));
            
            return document;
        }
    }
} 