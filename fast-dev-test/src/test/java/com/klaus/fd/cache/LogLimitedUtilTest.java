package com.klaus.fd.cache;

import com.klaus.fd.util.LogLimitedUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Klaus
 */
@SpringBootTest
public class LogLimitedUtilTest {
    @Test
    void testInfo1() {
        LogLimitedUtil.info("info", "loglogggggggg", 3);
    }

    @Test
    void testInfo2() {
        LogLimitedUtil.info("info", "loglogggggggg, {}", 3, "info-2");
    }


    @Test
    void testWarn() {
        LogLimitedUtil.warn("warn", "loglogggggggg", 3);
    }

    @Test
    void testError() {
        LogLimitedUtil.error("error-1", "eeeeeee", 2, null);
        LogLimitedUtil.error("error-2", "eeeeeee {}", 2, new RuntimeException("6666666"), 7777);
    }
}
