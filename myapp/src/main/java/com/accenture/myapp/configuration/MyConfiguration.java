/**
 * 
 */
package com.accenture.myapp.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author anurag.a.sachan
 *
 */

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.accenture.myapp.repository"
})
@EnableTransactionManagement
class MyConfiguration {
    @Bean
    NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}