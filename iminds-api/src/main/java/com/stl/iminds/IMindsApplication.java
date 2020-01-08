package com.stl.iminds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.stl.core.base.cache.CacheProviderFactory;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.logger.DefaultLogger;

@SpringBootApplication(scanBasePackages="com.stl")
@EnableJpaRepositories("com.stl")
@EntityScan("com.stl")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

public class IMindsApplication {

public static void main(String[] args) {
		
		LogManager.setDefaultLogger(new DefaultLogger("iMinds",IMindsApplication.class.getName()));
		LogManager.setLogger("PERFORMANCE", new DefaultLogger("iMinds", "PERFORMANCE"));
		//CacheProviderFactory.configure("com.stl.core.base.cache.LocalCacheProvider");
		CacheProviderFactory.configure("com.stl.core.base.cache.LocalCacheProvider");
		
		SpringApplication.run(IMindsApplication.class, args);
		//new TestCore().testLogger();
	}
}
