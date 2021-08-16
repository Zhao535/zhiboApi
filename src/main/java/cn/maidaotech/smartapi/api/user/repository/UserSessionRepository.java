package cn.maidaotech.smartapi.api.user.repository;

import cn.maidaotech.smartapi.api.user.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    UserSession findByToken(String token);
}
