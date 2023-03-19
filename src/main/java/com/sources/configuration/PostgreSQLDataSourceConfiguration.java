package com.sources.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.sources.repository.bar"},
        entityManagerFactoryRef = "barEntityManagerFactory",
        transactionManagerRef = "barTransactionManager"
)
public class PostgreSQLDataSourceConfiguration {

    private final Environment env;

    @Autowired
    public PostgreSQLDataSourceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(name = "dataSourceB")
    @ConfigurationProperties("spring.datasource.psql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "barEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSourceB") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter hjva = new HibernateJpaVendorAdapter();

        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.sources.model.entity.bar");
        emf.setJpaVendorAdapter(hjva);
        emf.setPersistenceUnitName("dataSourceB");
        emf.setJpaPropertyMap(jpaProperties());

        return emf;
    }

    @Bean(name = "barTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("barEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private HashMap<String, Object> jpaProperties() {
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.datasource.psql.hibernate.ddl-auto"));

        properties.put("hibernate.dialect",
                env.getProperty("spring.datasource.psql.hibernate.dialect"));

        properties.put("hibernate.physical_naming_strategy", SnakeCasePhysicalNamingStrategy.class.getName());

        properties.put("hibernate.show_sql", true);

        return properties;
    }
}
