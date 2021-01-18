package org.laiyw.act.seven.config;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.laiyw.act.seven.listeners.ActivitiListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * @ProjectName SpringBootAuthentication
 * @Author Laiyw
 * @CreateTime 2021/1/14 14:01
 * @Description TODO
 */
@Configuration
public class ActivitiConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(platformTransactionManager);
        configuration.setEventListeners(Collections.singletonList(new ActivitiListener()));
        return configuration;
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(SpringProcessEngineConfiguration engineConfiguration) {
        return engineConfiguration.getDynamicBpmnService();
    }
}
