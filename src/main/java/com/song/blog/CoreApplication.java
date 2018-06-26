package com.song.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.song.blog.dao")
@SpringBootApplication
@EnableTransactionManagement
public class CoreApplication {
//    @Bean(initMethod = "init", destroyMethod = "close")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return new DruidDataSource();
//    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*Mapper.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }


    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
