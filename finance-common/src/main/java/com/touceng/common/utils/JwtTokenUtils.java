package com.touceng.common.utils;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.touceng.common.exception.BusinessException;
import com.touceng.common.response.EResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: JwtToken工具类
 * @createTime 2018年7月8日 下午3:11:45
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
public class JwtTokenUtils {

    /**
     * token秘钥，请勿泄露，请勿随便修改
     */
    public static final String SECRET = "AADFASDFASDFASDF";
    /**
     * token 过期时间: 10天
     */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;
    public static final String TOKEN_ISSUER = "http://touceng.com";
    public static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    public static final String TOKEN = "token";// 存放Token的Header Key


    /**
     * @param tcMemberId
     * @throws Exception
     * @methodDesc: 功能描述: JWT生成Token
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月8日 下午3:20:49
     * @version v1.0.0
     */
    public static String createToken(String tcMemberId) throws Exception {
        Date iatDate = new Date();
        // 设置过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // JWT构成: header, payload, signature
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP").withClaim("tcMemberId", null == tcMemberId ? null : tcMemberId)
                .withIssuedAt(iatDate) // sign time
                .withIssuer(TOKEN_ISSUER).withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature
        // token = JwtTokenUtils.getDefaultToken();
        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // token 校验失败, 抛出Token验证非法异常
            log.error("[JwtTokenUtils-verifyToken异常]-{}", e);
            throw new BusinessException(EResultEnum.UNAUTHORIZED);
        }
        return jwt.getClaims();
    }

    /**
     * @param token
     * @methodDesc: 功能描述: 根据Token获取tcMemberId
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月8日 下午3:23:03
     * @version v1.0.0
     */
    public static String getMemberId(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim tcMemberId = claims.get("tcMemberId");
        if (null == tcMemberId || StringUtils.isEmpty(tcMemberId.asString())) {
            // token 校验失, 抛出Token验证非法异常
            throw new BusinessException(EResultEnum.UNAUTHORIZED);
        }
        return tcMemberId.asString();
    }

    /**
     * @methodDesc: 功能描述: 测试默认token
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月8日 下午3:23:03
     * @version v1.0.0
     */
    public static String getDefaultToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ0Y01lbWJlcklkIjoiMTAiLCJpc3MiOiJodHRwOi8vdG91Y2VuZy5jb20iLCJleHAiOjE1MzI5MjE4OTQsImlhdCI6MTUzMjA1Nzg5NH0.NQ_ebhcj-tEiBC2VkyFn8gMMtulyd8iOo7kTKd045Kw";
        return token;
    }
}
