package ru.mirea.MeatMarket.configs;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableJpaRepositories(basePackages = "ru.mirea.MeatMarket.repositories")
@EntityScan(basePackages = "ru.mirea.MeatMarket.entities")
@ComponentScan(basePackages = "ru.mirea.MeatMarket")
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableWebSecurity
public class AppConfig {
}
