package com.fengjian.crowd.test;

import com.fengjian.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {
    @Test
    public void testMd5(){
        System.out.println(CrowdUtil.md5("123456"));
    }

}
