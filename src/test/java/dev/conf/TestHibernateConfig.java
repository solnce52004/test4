package dev.conf;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@Import(TestPersistenceConfig.class)
@EnableTransactionManagement
public class TestHibernateConfig {
    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        final LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("dev.example.entities");
        factoryBean.setAnnotatedPackages("dev.example.dao");
        factoryBean.setHibernateProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }


    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        final InputStream in = TestHibernateConfig.class
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

    @Bean
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}


