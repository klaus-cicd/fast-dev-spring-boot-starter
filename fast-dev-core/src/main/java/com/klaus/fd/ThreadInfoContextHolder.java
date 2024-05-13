package com.klaus.fd;

import com.klaus.fd.utils.JsonUtil;
import org.springframework.core.NamedInheritableThreadLocal;

/**
 * @author Silas
 */
public class ThreadInfoContextHolder {

    public static final String TRACE_ID = "_trace-id";
    public static final String TOKEN = "_token";
    public static final String USER_INFO = "_user";
    public static final String REQ_URI = "_req-uri";

    private static final ThreadLocal<String> THREAD_CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(TRACE_ID);
    private static final ThreadLocal<String> TOKEN_CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(TOKEN);
    private static final ThreadLocal<String> USER_INFO_CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(USER_INFO);
    private static final ThreadLocal<String> URI = new NamedInheritableThreadLocal<>(REQ_URI);

    public static String getTraceId() {
        return THREAD_CONTEXT_HOLDER.get();
    }

    public static String getToken() {
        return TOKEN_CONTEXT_HOLDER.get();
    }

    public static <T> T getUser(Class<T> userClass) {
        return JsonUtil.parseToObject(USER_INFO_CONTEXT_HOLDER.get(), userClass);
    }

    public static void setToken(String token) {
        TOKEN_CONTEXT_HOLDER.set(token);
    }

    public static void setUser(String user) {
        USER_INFO_CONTEXT_HOLDER.set(user);
    }

    public static void setTraceId(String traceId) {
        THREAD_CONTEXT_HOLDER.set(traceId);
    }

    public static void setUri(String uri) {
        URI.set(uri);
    }

    public static String getUri() {
        return URI.get();
    }

    public static void reset() {
        THREAD_CONTEXT_HOLDER.remove();
        TOKEN_CONTEXT_HOLDER.remove();
        USER_INFO_CONTEXT_HOLDER.remove();
        URI.remove();
    }
}
