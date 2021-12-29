package pl.k4mil.springcleanlogging;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Autoconfiguration {

    @Bean
    public BeanDefinitionRegistryPostProcessor advisorBeanRegisterer() {
        return new AdvisorBeanRegisterer();
    }
}
