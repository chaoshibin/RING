package com.ring.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 编码与解码操作工具类
 *
 * @author chaoshibin
 */
public final class CodecUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CodecUtil.class);
    public static final String UTF_8 = "UTF-8";

    private CodecUtil() {
    }

    /**
     * 将 URL 编码
     */
    public static String encodeURL(String str) {
        String target;
        try {
            target = URLEncoder.encode(str, UTF_8);
        } catch (Exception e) {
            LOG.error("encode URL failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     */
    public static String decodeURL(String str) {
        String target;
        try {
            target = URLDecoder.decode(str, UTF_8);
        } catch (Exception e) {
            LOG.error("decode URL failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字符串 Base64 编码
     */
    public static String encodeBASE64(String str) {
        String target;
        try {
            target = Base64.encodeBase64URLSafeString(str.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            LOG.error("encode BASE64 failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字符串 Base64 解码
     */
    public static String decodeBASE64(String str) {
        String target;
        try {
            target = new String(Base64.decodeBase64(str), UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOG.error("decode BASE64 failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字符串 MD5 加密
     */
    public static String encryptMD5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 将字符串 SHA1 加密
     * @param str
     * @return 32位
     */
    public static String encryptSHA1(String str) {
        return DigestUtils.sha1Hex(str);
    }


    /**
     * 将字符串 SHA256 加密
     * @param str
     * @return 64位
     */
    public static String encryptSHA256(String str) {
        return DigestUtils.sha256Hex(str);
    }

    /**
     * 创建随机数
     */
    public static String createRandom(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    /**
     * 获取 UUID（32位）
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
