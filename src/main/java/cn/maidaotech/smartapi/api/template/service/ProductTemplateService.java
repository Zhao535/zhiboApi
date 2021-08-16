package cn.maidaotech.smartapi.api.template.service;

import cn.maidaotech.smartapi.api.template.entity.ProductTemplateQo;
import cn.maidaotech.smartapi.api.template.model.ProductTemplate;
import org.springframework.data.domain.Page;

public interface ProductTemplateService {

    Page<ProductTemplate> productTemplates(ProductTemplateQo qo);

    //
    ProductTemplate item(Integer id);

    //
    void save(ProductTemplate productTemplate) throws Exception;

    //
    void status(Integer id) throws Exception;
//
//    void remove(int id) throws ServiceException;
//
//    void batchRemove(List<Integer> ids);

    Page<ProductTemplate> merchantTemplate(ProductTemplateQo qo ) throws Exception;

}
