package dev.conf;

import dev.example.entities.event_listeners.ReplicationEventListenerIntegrator;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@Import(PersistenceConfig.class)
@EnableTransactionManagement
public class HibernateConfig {
    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        final LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("dev.example.entities");
        factoryBean.setAnnotatedPackages("dev.example.dao");
        factoryBean.setHibernateProperties(hibernateProperties());
//        factoryBean.setHibernateIntegrators(ReplicationEventListenerIntegrator.INSTANCE);
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }


    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        final InputStream in = HibernateConfig.class
                .getClassLoader()
                .getResourceAsStream("hibernate.properties");
        try {
            if (in != null) {
                properties.load(in);
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}


