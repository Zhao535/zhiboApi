package cn.maidaotech.smartapi.common.vcode.repositroy;

import cn.maidaotech.smartapi.common.vcode.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeRepositroy extends JpaRepository<Code, Long> {
    //    Code findTopBySubjectAndUsernameOrderByCreatedAtDesc(String subject, String username);

    /**
     * 修改数据库，包括insert、update、delete，自定义SQL语句需要@Modifying和@Transactional
     * nativeQuery=true时是SQL，nativeQuery=false时是HQL
     */

    @Query(value = "select * from code where subject=:subject and username=:username order by created_at  desc limit 0,1", nativeQuery = true)
    Code findBySubjectAndUsername(String subject, String username);
}
