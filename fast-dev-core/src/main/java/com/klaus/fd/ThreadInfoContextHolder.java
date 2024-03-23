package com.klaus.fd;

import org.springframework.core.NamedInheritableThreadLocal;

/**
 * @author Silas
 */
public class ThreadInfoContextHolder {

    public static final String TRACE_ID = "_trace-id";
    public static final String TOKEN = "_trace-id";
    public static final String USER_INFO = "_trace-id";
    private static final ThreadLocal<String> THREAD_CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(TRACE_ID);
    private static final ThreadLocal<String> TOKEN_CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(TOKEN);
    private static final ThreadLocal<String> USER_INFO_CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(USER_INFO);

    public static String getTraceId() {
        return THREAD_CONTEXT_HOLDER.get();
    }

    public static String getToken() {
        return TOKEN_CONTEXT_HOLDER.get();
    }

    public static void setToken(String token) {
        TOKEN_CONTEXT_HOLDER.set(token);
    }

    public static void setTraceId(String traceId) {
        THREAD_CONTEXT_HOLDER.set(traceId);
    }

    public static void reset() {
        THREAD_CONTEXT_HOLDER.remove();
        TOKEN_CONTEXT_HOLDER.remove();
        USER_INFO_CONTEXT_HOLDER.remove();
    }
}
