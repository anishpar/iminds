package com.stl.iminds.commons.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.stl.core.commons.db.DBManager;

@Configuration
public class PersistenceConfig {
	
	@Autowired
	Environment environment;
	
	@Autowired
	DBManager dbManager;
	
	private static final String MASTER_DATASOURCE = "MASTERDS";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "dbconfig.jpa.properties.hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "dbconfig.jpa.properties.hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_STATS = "dbconfig.jpa.properties.hibernate.generate_statistics";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.stl";

	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProterties = new Properties();
        
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_STATS, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_STATS));

		entityManagerFactoryBean.setJpaProperties(jpaProterties);
        return entityManagerFactoryBean;
    }
 
    @Bean
    public DataSource dataSource() {
    	
    	return dbManager.getDataSource(MASTER_DATASOURCE);
    }
    
    @Bean
    public AnnotationMBeanExporter annotationMBeanExporter() {
        AnnotationMBeanExporter annotationMBeanExporter = new AnnotationMBeanExporter();
        annotationMBeanExporter.addExcludedBean("dataSource");
        return annotationMBeanExporter;
    }
 
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
