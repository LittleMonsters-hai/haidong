package com.haidong.storysharing;

import com.haidong.storysharing.utils.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class StorySharingApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(PageUtils.getCurrent());
    }

}
