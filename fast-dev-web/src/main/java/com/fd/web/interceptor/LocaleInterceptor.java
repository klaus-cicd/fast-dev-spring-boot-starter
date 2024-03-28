package com.fd.web.interceptor;

import com.fd.web.util.ServletUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Klaus
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String lang = ServletUtil.getHeader(request, "lang");
        if (!StringUtils.hasText(lang)) {
            lang = "en_US";
        }

        // 格式: 语言_地区
        String[] s = lang.split("_");
        Locale.setDefault(new Locale(s[0], s[1]));
        return true;
    }
}
