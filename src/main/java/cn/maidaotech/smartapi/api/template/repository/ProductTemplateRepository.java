package cn.maidaotech.smartapi.api.template.repository;

import cn.maidaotech.smartapi.api.template.model.ProductTemplate;
import cn.maidaotech.smartapi.common.reposiotry.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductTemplateRepository extends BaseRepository<ProductTemplate, Integer> {

    @Transactional
    @Modifying
    @Query("delete from ProductTemplate where id  in(:ids)")
    void deleteByIds(List<Integer> ids);




}
