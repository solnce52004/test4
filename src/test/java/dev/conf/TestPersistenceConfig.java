package dev.conf;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySources( value = {
        @PropertySource("classpath:persistence.properties"),
        @PropertySource("classpath:spy.properties"),
        @PropertySource("classpath:liquibase.properties")
})
@EnableTransactionManagement
public class TestPersistenceConfig {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource(){
        final DriverManagerDataSource driverManager = new DriverManagerDataSource();
        driverManager.setDriverClassName(
                Objects.requireNonNull(env.getProperty("db.driver_class"))
        );
        driverManager.setUrl(env.getProperty("db.url"));
        driverManager.setUsername(env.getProperty("db.username"));
        driverManager.setPassword(env.getProperty("db.password"));

        return driverManager;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(env.getProperty("output_classpath"));
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
}
