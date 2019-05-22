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

    public static String path = TaleUtils.getUploadFilePath()+"backup";
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreApplication.class);


//    @Value("${wangsong.httpport}")
//    private int httpport;
//    @Value("${server.port}")
//    private int httpsport;

    @Bean
    public DataSource dataSource() throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        LOGGER.debug("path is：{},panduan data file newblog.db,if has return，if not copy resources file",path);


        if(TaleUtils.fileIsExists(path+"/newblog.db")){
            LOGGER.debug("data file is has，return DataSource");
        }else {
            ZipUtils.writeToLocal(getClass().getClassLoader()
                    .getResourceAsStream("newblog.db"),path + "/newblog.db");
            LOGGER.debug("init DruidDataSource，copy data file to backup wenjianjiazhong，lianjie backup zhong data file");
        }

        return DruidDataSourceBuilder.create().build();
    }

//
//
//    @Bean
//    public Connector connector(){
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(httpport);
//        connector.setSecure(false);
//        connector.setRedirectPort(httpsport);
//
//
//        return connector;
//    }
//
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
//        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(connector);
//        return tomcat;
//    }
//


    public static void main(String[] args) {

        SpringApplication.run(CoreApplication.class, args);
    }
}
