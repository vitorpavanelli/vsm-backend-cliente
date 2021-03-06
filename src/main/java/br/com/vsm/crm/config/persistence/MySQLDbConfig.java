package br.com.vsm.crm.config.persistence;

import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.cliente.repository.Endereco;
import br.com.vsm.crm.api.pontuacao.repository.Pontuacao;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacao;
import br.com.vsm.crm.api.venda.repository.Venda;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManager",
        transactionManagerRef = "primaryTransactionManager",
        basePackageClasses = {
                Cliente.class,
                Endereco.class,
                Pontuacao.class,
                RegraPontuacao.class,
                Venda.class
        }
)
public class MySQLDbConfig {

    @Value("${app.dist}")
    private String env;

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[] {
                "br.com.vsm.crm.api.cliente.repository",
                "br.com.vsm.crm.api.pontuacao.repository",
                "br.com.vsm.crm.api.venda.repository"
        });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "prod".equals(env) ? "none" : "dev".equals(env) ? "none" : "none");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.id.new_generator_mappings", "false");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        properties.put("hibernate.jdbc.time_zone", "UTC");
        em.setJpaPropertyMap(properties);

        return em;
    }

    private HikariDataSource getDataSource() {
        return  "prod".equals(env) ? prodDataSource() : "dev".equals(env) ? devDataSource() : testDataSource();
    }

    @Primary
    @Bean
    @ConfigurationProperties("prod.datasource")
    @ConditionalOnProperty(
            name = "app.dist",
            havingValue = "prod"
    )
    public HikariDataSource prodDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    @ConfigurationProperties("dev.datasource")
    @ConditionalOnProperty(
            name = "app.dist",
            havingValue = "dev"
    )
    public HikariDataSource devDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    @ConfigurationProperties("test.datasource")
    @ConditionalOnProperty(
            name = "app.dist",
            havingValue = "test"
    )
    public HikariDataSource testDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(primaryEntityManager().getObject());

        return transactionManager;
    }
}
