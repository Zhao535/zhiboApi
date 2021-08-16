package cn.maidaotech.smartapi.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebApiConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdmApiInterceptor admApiInterceptor;
    @Autowired
    private PickerApiInterceptor pickerApiInterceptor;
    @Autowired
    private MerchantAdminInterceptor merchantAdminInterceptor;
    @Autowired
    private UsrApiInterceptor usrApiInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(admApiInterceptor).addPathPatterns("/adm/**");
        registry.addInterceptor(pickerApiInterceptor).addPathPatterns("/common/**");
        registry.addInterceptor(merchantAdminInterceptor).addPathPatterns("/mch/**");
        registry.addInterceptor(merchantAdminInterceptor).addPathPatterns("/adm/**");
        registry.addInterceptor(usrApiInterceptor).addPathPatterns("/usr/**");
    }
}
