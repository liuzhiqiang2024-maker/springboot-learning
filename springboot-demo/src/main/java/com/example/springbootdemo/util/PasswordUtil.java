package com.example.springbootdemo.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class PasswordUtil {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    /**
     * 加密密码
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验密码
     * @param rawPassword 用户输入的明文
     * @param encodedPassword 数据库里的加密密码
     */
    public static boolean matches(String rawPassword,String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
