package com.klaus.fd.cache;

import com.alibaba.fastjson2.JSON;
import com.klaus.fd.comm.TestEntityA;
import com.klaus.fd.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class CaffeineTest {

    @Resource
    private CacheUtil cacheUtil;

    @Test
    void testA() throws InterruptedException {
        Cache cache = cacheUtil.getCaffeineCache();
        log.info(cache.toString());
        cache.put("AAA", "AAAAAA");
//        Thread.sleep(5000);
        log.info("Cache value: " + cache.get("AAA", String.class));
    }

    @Test
    public void testB() {
        String key = "testB";
        cacheUtil.add(key, "TestBBBBBBB");
        String str = cacheUtil.getStr(key);
        log.info("Cache: {}", str);
    }

    @Test
    void testC() {
        String key = "testC";

        TestEntityA testEntityA = new TestEntityA();
        testEntityA.setId(123L);
        testEntityA.setName("Name.......");
        cacheUtil.add(key, testEntityA);

        log.info("{}", JSON.toJSONString(cacheUtil.get(key, TestEntityA.class)));
    }
}
