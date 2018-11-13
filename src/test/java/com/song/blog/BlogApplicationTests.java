package com.song.blog;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpMethods;
import com.song.blog.dto.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() throws Exception {

        org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BlogApplicationTests.class);
        String SHOPURL = "http://www.admin333.com/ShopProduct-";

        Product product = new Product();
        product.setOrderNO("0");
        product.setProductId("199793");
        product.setTotalCount("1");
        product.setEmail("1111@qq.com");
        product.setContact("1111@qq.com");
        product.setPaymentType("56");
        product.setUserPaymentId("5135946");


//        LOGGER.debug(product.toString());
//        HttpConfig config = HttpConfig.custom().method(HttpMethods.POST).url(SHOPURL + "199793.html").map(product.toMap());
//
//        String resultHtml = HttpClientUtil.post(config);

//        String resultHtml = HttpClientUtils.doPost(SHOPURL + "199793.html",null,product.toMap()).getContent();

        String resultHtml  = HttpClientUtil.post( HttpConfig.custom()
                .method(HttpMethods.POST)
                .url("http://www.admin333.com/ShopProduct-199793.html")
                .map(product.toMap()));

        LOGGER.debug(resultHtml);






//        System.out.println(TaleUtils.class.getClassLoader().getResource("").getPath());
//        System.out.println(TaleUtils.getUploadFilePath());
//        System.out.println(TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//
//        ClassPathResource classPathResource =new ClassPathResource("newblog.db");
//
//
//
//        System.out.println(classPathResource.getPath());
//        System.out.println(classPathResource.getDescription());
//        System.out.println(classPathResource.getURL().toString());
//        System.out.println(classPathResource.getFilename());
//        System.out.println(ResourceUtils.getFile("newblog.db").getAbsolutePath());
//
//
//        ZipUtils.writeToLocal(getClass().getClassLoader()
//                .getResourceAsStream("newblog.db"),
//                TaleUtils.getUploadFilePath()+"backup/back.db");
//
//        String uuid = UUID.UU64();
//        java.util.UUID javauuid = UUID.fromUU64(uuid);
//
//        System.out.println(uuid);
//        System.out.println(javauuid);
//
//        System.out.println(UUID.UU64(javauuid));
//        System.out.println(javauuid.toString().length());
    }

}
