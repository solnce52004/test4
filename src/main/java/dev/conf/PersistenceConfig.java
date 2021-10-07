package dev.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
public class PersistenceConfig {
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
}
