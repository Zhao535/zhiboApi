package cn.maidaotech.smartapi.api.template.service;

import cn.maidaotech.smartapi.api.category.model.ProductCategory;
import cn.maidaotech.smartapi.api.category.service.ProductCategoryService;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.product.entity.ProductParam;
import cn.maidaotech.smartapi.api.product.entity.ProductSpec;
import cn.maidaotech.smartapi.api.template.entity.ProductTemplateQo;
import cn.maidaotech.smartapi.api.template.entity.TemplateErrors;
import cn.maidaotech.smartapi.api.template.entity.TemplateStatus;
import cn.maidaotech.smartapi.api.template.model.ProductTemplate;
import cn.maidaotech.smartapi.api.template.repository.ProductTemplateRepository;
import cn.maidaotech.smartapi.common.context.MerchantAdminContexts;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductTemplateServiceImpl implements ProductTemplateService, TemplateErrors {

    @Autowired
    private ProductTemplateRepository productTemplateRepository;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    public void save(ProductTemplate productTemplate) throws Exception {
        if (Objects.isNull(productTemplate)) {
            throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
        }
        if (productTemplate.getTitle() == null) {
            throw new ServiceException(ERR_PRODUCT_TEMPLATE_TITLE_NOT_NULL);
        }
        List<ProductSpec> specs = productTemplate.getSpecs();
        if (CollectionUtils.isEmpty(specs)) {
            throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
        }
        Set<String> set = new HashSet<>(specs.size());
        for (ProductSpec spec : specs) {
            if (StringUtils.isEmpty(spec.getSno())) {
                throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
            }
            if (CollectionUtils.isEmpty(spec.getParams())) {
                throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
            }
            for (ProductParam param : spec.getParams()) {
                if (StringUtils.isEmpty(param.getLabel()) || StringUtils.isEmpty(param.getValue())) {
                    throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
                }
            }
            set.add(spec.getSno());
        }

        if (CollectionUtils.isEmpty(productTemplate.getParams())) {
            throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
        }
        if (productTemplate.getId() == null || productTemplate.getId() == 0) {

            productTemplate.setStatus((byte) 2);
            productTemplate.setCreatedAt(System.currentTimeMillis());
            productTemplateRepository.save(productTemplate);
        } else {
            ProductTemplate exist = getById(productTemplate.getId());
            exist.setTitle(productTemplate.getTitle());
            exist.setSpecs(productTemplate.getSpecs());
            exist.setParams(productTemplate.getParams());
            exist.setContent(productTemplate.getContent());
            exist.setSequence(productTemplate.getSequence());
            exist.setStatus(productTemplate.getStatus());
            productTemplateRepository.save(exist);
        }
    }

    private ProductTemplate getById(Integer id) throws Exception {
        if (id == null || id == 0) {
            throw new ServiceException(ERR_PRODUCT_TEMPLATE_NOT_NULL);
        }
        ProductTemplate template = productTemplateRepository.findById(id).orElse(null);
        if (Objects.isNull(template)) {
            throw new DataNotFoundServiceException("template");
        }
        return template;
    }

    @Override
    public Page<ProductTemplate> productTemplates(ProductTemplateQo qo) {
        return productTemplateRepository.findAll(qo);
    }

    @Override
    public ProductTemplate item(Integer id) {
        return productTemplateRepository.findById(id).orElse(null);
    }

    @Override
    public void status(Integer id) throws Exception {
        ProductTemplate template = getById(id);
        template.setStatus(Objects.equals(template.getStatus(), Constants.STATUS_OK) ? Constants.STATUS_HALT : Constants.STATUS_OK);
        productTemplateRepository.save(template);
    }


    private List<String> chooseSequences() throws Exception {
        Merchant merchant = MerchantAdminContexts.requireMerchant();
        List<ProductCategory> categories = productCategoryService.findAllCategoriesForUsr();


        List<String> chooseSequences = new ArrayList<>();
        for (String sequence : merchant.getSequences()) {
            for (ProductCategory category : categories) {
                if (!CollectionUtils.isEmpty(category.getChildren())) {
                    for (ProductCategory child : category.getChildren()) {
                        if (!CollectionUtils.isEmpty(child.getChildren())) {
                            for (ProductCategory childChild : child.getChildren()) {
                                if (sequence.substring(2, 6).equals("0000") && sequence.equals(category.getSequence())) {
                                    chooseSequences.add(childChild.getSequence());
                                }
                                if (!sequence.substring(2, 4).equals("00") && sequence.substring(4, 6).equals("00") && sequence.equals(child.getSequence())) {
                                    chooseSequences.add(childChild.getSequence());
                                }
                                if (!sequence.substring(4, 6).equals("00") && sequence.equals(childChild.getSequence())) {
                                    chooseSequences.add(childChild.getSequence());
                                }
                            }
                        }
                    }
                }
            }
        }
        return chooseSequences;
    }


    @Override
    public Page<ProductTemplate> merchantTemplate(ProductTemplateQo qo) throws Exception {
        qo.setStatus(TemplateStatus.NORMAL.value());
        qo.setSequence(chooseSequences());
        return productTemplateRepository.findAll(qo);
    }
}
