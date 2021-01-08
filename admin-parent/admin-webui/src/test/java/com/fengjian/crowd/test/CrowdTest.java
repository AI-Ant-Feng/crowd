package com.fengjian.crowd.test;

import com.fengjian.crowd.entity.Admin;
import com.fengjian.crowd.mapper.AdminMapper;
import com.fengjian.crowd.service.api.AdminService;
import com.fengjian.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AdminService adminService;

    @Test
    public void testAdminService(){
        for (int i = 3; i < 389; i++) {
            Admin admin = new Admin(i, "zhanghui" + i, CrowdUtil.md5("123456"), "张慧" + i, "zh@163.com" + i, null);
            adminService.saveAdmin(admin);
        }
    }

    @Test
    public void testLog(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("I love you!");
    }

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(5,"zhanghui","123456","张慧","zh@163.com", null);
        adminMapper.insert(admin);
    }
    @Test
    public void testConnection() throws Exception{
        Connection connection = dataSource.getConnection();
        connection.close();
    }

}
