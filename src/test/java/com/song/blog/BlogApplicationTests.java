package com.song.blog;

import com.song.blog.utils.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() throws IOException {
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

        String uuid = UUID.UU64();
        java.util.UUID javauuid = UUID.fromUU64(uuid);

        System.out.println(uuid);
        System.out.println(javauuid);

        System.out.println(UUID.UU64(javauuid));
        System.out.println(javauuid.toString().length());
    }

}
