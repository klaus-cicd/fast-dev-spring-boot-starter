package com.fd.auth.util;

import cn.hutool.core.convert.Convert;
import com.fd.auth.constant.AuthConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author klaus
 * @date 2024/03/23
 */
@Slf4j
public class JwtUtil {

    public static String SECRET_KEY = AuthConstant.SECRET_KEY;

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put(AuthConstant.USER_ID, "1234567");
        map.put(AuthConstant.USERNAME, "klaus");
        String token = createToken(map);
        log.info("Token: {}", token);

        Claims claims = parseToken(token);
        log.info(getUserId(claims));
        log.info(getUsername(claims));
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(getKey(), SignatureAlgorithm.HS512).compact();
    }

    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserStr(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, AuthConstant.USER);
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserStr(Claims claims) {
        return getValue(claims, AuthConstant.USER);
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, AuthConstant.USER_ID);
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserId(Claims claims) {
        return getValue(claims, AuthConstant.USER_ID);
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, AuthConstant.USERNAME);
    }

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    public static String getUsername(Claims claims) {
        return getValue(claims, AuthConstant.USERNAME);
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        return Convert.toStr(claims.get(key), "");
    }
}
