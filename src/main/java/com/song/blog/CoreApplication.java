package com.song.blog;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.song.blog.utils.TaleUtils;
import com.song.blog.utils.ZipUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

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

    public static String path = TaleUtils.getUploadFilePath()+"backup";
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreApplication.class);

    @Bean
    public DataSource dataSource() throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        LOGGER.debug("路径为：{},然后判断这个路径下有没有数据库文件 newblog.db 如果有就直接返回，如果没有从 resources 目录中把数据库文件复制过来",path);


        if(TaleUtils.fileIsExists(path+"/newblog.db")){
            LOGGER.debug("数据库文件已经存在，直接返回 DataSource");
        }else {
            ZipUtils.writeToLocal(getClass().getClassLoader()
                    .getResourceAsStream("newblog.db"),path + "/newblog.db");
            LOGGER.debug("初始化DruidDataSource，把数据库文件复制到 backup 文件夹中，链接 backup 文件夹中的数据库文件");
        }

        return DruidDataSourceBuilder.create().build();
    }


    public static void main(String[] args) {


        SpringApplication.run(CoreApplication.class, args);
    }
}
