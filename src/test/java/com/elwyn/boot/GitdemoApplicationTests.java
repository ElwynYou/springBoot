package com.elwyn.boot;

import com.elwyn.dao.UserDao;
import com.elwyn.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitdemoApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setCnUserId("111");
        user.setCnUserName("fdsa");
        userDao.save(user);
        System.out.println("插入用户信息"+user.getCnUserName());
    }

}
