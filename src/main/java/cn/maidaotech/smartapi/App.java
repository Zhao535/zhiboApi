package cn.maidaotech.smartapi;

import cn.maidaotech.smartapi.common.reposiotry.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
