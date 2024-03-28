package com.fd.web.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.klaus.fd.ThreadInfoContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 线程变量管理拦截器
 *
 * @author Klaus
 */
public class ThreadInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = ServletUtil.getHeader(request, ThreadInfoContextHolder.TRACE_ID, StandardCharsets.UTF_8);
        if (StrUtil.isBlankIfStr(traceId)) {
            traceId = IdUtil.fastUUID();
        }
        ThreadInfoContextHolder.setTraceId(traceId);
        ThreadInfoContextHolder.setToken(ServletUtil.getHeader(request, ThreadInfoContextHolder.TOKEN, StandardCharsets.UTF_8));


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadInfoContextHolder.reset();
    }
}
