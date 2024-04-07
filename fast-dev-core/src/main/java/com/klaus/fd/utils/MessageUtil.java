package com.klaus.fd.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * 国际化工具类
 *
 * @author klaus
 * @date 2024/03/28
 */
@Slf4j
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSourceBean;

    private static MessageSource messageSource;

    @PostConstruct
    public void init() {
        setMessageSource(messageSourceBean);
    }

    /**
     * 获取 国际化后内容信息
     *
     * @param code 国际化key
     * @return 国际化后内容信息
     */
    public static String getMessage(String code) {
        return getMessage(code, code, Locale.getDefault());
    }

    /**
     * 获取指定语言中的国际化信息，如果没有则走中文
     *
     * @param code 国际化 key
     * @param lang 语言参数
     * @return 国际化后内容信息
     */
    public static String getMessage(String code, String lang) {
        Locale locale;
        if (!StringUtils.hasText(lang)) {
            locale = Locale.US;
        } else {
            try {
                String[] split = lang.split("_");
                locale = new Locale(split[0], split[1]);
            } catch (Exception e) {
                locale = Locale.CHINA;
            }
        }
        return getMessage(code, code, locale);
    }


    public static String getMessage(String code, String defaultMessage, Locale locale, String... args) {
        String content;
        try {
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            log.error("国际化参数获取失败 ===> {}", e.getMessage(), e);
            content = defaultMessage;
        }
        return content;
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }
}

