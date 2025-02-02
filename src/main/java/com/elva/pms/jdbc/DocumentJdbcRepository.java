package com.elva.pms.jdbc;

import com.elva.pms.pojo.dao.DocumentDao;
import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public long insert(DocumentDao documentDao) {
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
            ps.setLong(++index, documentDao.getEntityId());
            ps.setString(++index, documentDao.getEntityType().toString());
            ps.setString(++index, documentDao.getFileCategory().toString());
            ps.setString(++index, documentDao.getFileName());
            ps.setString(++index, documentDao.getFilePath());
            ps.setBoolean(++index, true);
            ps.setString(++index, documentDao.getCreatedBy());
            ps.setString(++index, documentDao.getCreatedBy());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<DocumentDao> findByEntityIdAndType(Long entityId, EntityType entityType) {
        String sql = "SELECT * FROM document_management " +
                "WHERE entity_id = ? AND entity_type = ? AND is_active = true";
        
        return jdbcTemplate.query(sql, new Object[]{entityId, entityType.toString()}, 
            new DocumentMapper());
    }

    private static class DocumentMapper implements RowMapper<DocumentDao> {
        @Override
        public DocumentDao mapRow(ResultSet rs, int rowNum) throws SQLException {
            DocumentDao documentDao = new DocumentDao();
            documentDao.setId(rs.getLong("id"));
            documentDao.setEntityId(rs.getLong("entity_id"));
            documentDao.setEntityType(EntityType.valueOf(rs.getString("entity_type")));
            documentDao.setFileCategory(FileCategory.valueOf(rs.getString("file_category")));
            documentDao.setFileName(rs.getString("file_name"));
            documentDao.setFilePath(rs.getString("file_path"));
            documentDao.setActive(rs.getBoolean("is_active"));
            
            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                documentDao.setCreatedAt(createdAt.toLocalDateTime());
            }
            
            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                documentDao.setUpdatedAt(updatedAt.toLocalDateTime());
            }
            
            documentDao.setCreatedBy(rs.getString("created_by"));
            documentDao.setUpdatedBy(rs.getString("updated_by"));
            
            return documentDao;
        }
    }
} 