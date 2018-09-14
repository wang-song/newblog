package com.song.blog;

import com.song.blog.utils.TaleUtils;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.song.blog.dao")
@SpringBootApplication
@EnableTransactionManagement
public class CoreApplication {

    public static String path = TaleUtils.getUploadFilePath()+"backup";
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreApplication.class);


    @Value("${wangsong.httpport}")
    private int httpport;
    @Value("${server.port}")
    private int httpsport;

//    @Bean
//    public DataSource dataSource() throws IOException {
//        File file = new File(path);
//        if(!file.exists()){
//            file.mkdirs();
//        }
//        LOGGER.debug("path is：{},panduan data file newblog.db,if has return，if not copy resources file",path);
//
//
//        if(TaleUtils.fileIsExists(path+"/newblog.db")){
//            LOGGER.debug("data file is has，return DataSource");
//        }else {
//            ZipUtils.writeToLocal(getClass().getClassLoader()
//                    .getResourceAsStream("newblog.db"),path + "/newblog.db");
//            LOGGER.debug("init DruidDataSource，copy data file to backup wenjianjiazhong，lianjie backup zhong data file");
//        }
//
//        return DruidDataSourceBuilder.create().build();
//    }



    @Bean
    public Connector connector(){
        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(httpport);
        connector.setSecure(false);
        connector.setRedirectPort(httpsport);


        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint=new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection=new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }



    public static void main(String[] args) {


        SpringApplication.run(CoreApplication.class, args);
    }
}
