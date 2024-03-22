package com.klaus.fd.core;

import com.klaus.fd.comm.TestEntity;
import com.klaus.fd.utils.DateUtil;
import com.klaus.fd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootTest
public class JsonUtilTest {

    @Test
    public void test() {
        List<TestEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TestEntity testEntity = new TestEntity();
            testEntity.setUsername("Klaus: " + i);
            testEntity.setTimestamp(new Timestamp(DateUtil.nowUtcTs()));
            testEntity.setLocalDate(LocalDate.now());
            testEntity.setLocalDateTime(LocalDateTime.now());

            list.add(testEntity);
        }
        String jsonListStr = JsonUtil.toJsonStr(list);
        log.info("[jsonListStr]: {}", jsonListStr);

        List<TestEntity> testEntities = JsonUtil.parseToList(jsonListStr, TestEntity.class);
        log.info("[testEntities]: {}", JsonUtil.toJsonStr(testEntities));

        log.info("-----------------------------");

        TestEntity testEntity = new TestEntity();
        testEntity.setUsername("Klaus-single");
        testEntity.setTimestamp(new Timestamp(DateUtil.nowUtcTs()));
        testEntity.setLocalDate(LocalDate.now());
        testEntity.setLocalDateTime(LocalDateTime.now());

        String jsonStr = JsonUtil.toJsonStr(testEntity);
        log.info("[jsonStr]: {}", jsonStr);
        TestEntity testEntity1 = JsonUtil.parseToObject(jsonStr, TestEntity.class);
        log.info("[testEntity1]: {}", JsonUtil.toJsonStr(testEntity1));

        Map<String, Object> map = JsonUtil.parseToObject(jsonStr, Map.class);
        log.info("[map]: {}", map);
    }


}
