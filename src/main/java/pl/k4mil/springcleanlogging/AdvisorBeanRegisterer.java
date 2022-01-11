package pl.k4mil.springcleanlogging;

import org.springframework.aop.Advisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.UUID;

public class AdvisorBeanRegisterer implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private AdvisorCreator advisorCreator = new AdvisorCreator();

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BindResult<Logs> result = Binder.get(environment).bind("scl", Logs.class);
        Logs logs = result.get();
        if (logs != null && logs.getLogs() != null) {
            logs.getLogs().forEach(logSpecs -> registerAdvisorBean(beanDefinitionRegistry, logSpecs));
        }
    }

    private void registerAdvisorBean(BeanDefinitionRegistry registry, LogSpecs logSpecs) {
        registry
            .registerBeanDefinition(
                "loggingAdvisor-" + (logSpecs.getId() != null ? logSpecs.getId() : UUID.randomUUID().toString()),
                    BeanDefinitionBuilder
                        .genericBeanDefinition(
                            Advisor.class,
                            () -> advisorCreator.createAdvisor(logSpecs))
                        .getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
