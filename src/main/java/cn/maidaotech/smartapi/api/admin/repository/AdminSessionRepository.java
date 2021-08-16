package cn.maidaotech.smartapi.api.admin.repository;

import cn.maidaotech.smartapi.api.admin.model.AdminSession;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminSessionRepository extends BaseRepository<AdminSession, Long> {
    AdminSession findByToken(String token);

    List<AdminSession> findByAdminId(Integer id);
 }
