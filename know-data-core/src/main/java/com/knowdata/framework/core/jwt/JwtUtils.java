package com.knowdata.framework.core.jwt;

import com.knowdata.framework.core.util.encipher.Base64Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author johnnyzen
 * @project know-data-parent
 * @create-time 2023/4/30  14:17:23
 * @description
 *  1. 依赖于 io.jsonwebtoken 包
 *      io.jsonwebtoken:jjwt-api:0.11.2
 *      io.jsonwebtoken:jjwt-impl:0.11.2
 *      io.jsonwebtoken:jjwt-gson:0.11.5
 *
 *  2. JWT 概述
 *      json web token(JWT)是一种用于双方之间传递安全信息的简洁的、URL安全的表述性声明规范。
 *      JWT作为一个开放的标准(RFC 7519)，定义了一种简洁的，自包含的方法用于通信双方之间以Json对象的形式安全的传递信息，该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录(SSO)场景。
 *      JWT的声明一般被用在【身份提供者】和【服务提供者】间传递被认证的用户身份信息，以便于从资源服务器获取资源，也可以增加一些额外的其它业务逻辑所必须的声明信息，该token也可直接被用于认证，也可被加密。
 *      JWT其实就是一个字符串，由3个部分组成：头部(header)、载荷(payload)及签名(signature)，是一种基于JSON的令牌安全验证(在某些特定的场合可以替代Session或者Cookie)。
 *      JWT经常被用来保护服务器的资源，客户端一般通过HTTP/header的Authorzation把JWT发送给服务端，服务端使用自己保存的Key进行计算，验证签名JWT是否合法。JWT可以使用HMAC算法或者是RSA的公私秘钥对进行签名。
 *
 *      jwt的3个组成部分，每部分通过点号来分割，即：header.payload.signature
 *          头部(header)是一个JSON对象，一般使用Base64URL编码，承载两部分信息，存放一些声明信息，如：用什么加密，用什么编码【作用:指定JWT使用的签名】
 *              ① 声明类型typ，表示这个令牌(token)的类型(type)，JWT令牌统一写为JWT
 *              ② 声明加密的算法alg，通常直接使用HMAC SHA256，也可以使用RSA，支持很多算法(HS256、HS384、HS512、RS256、RS384、RS512、ES256、ES384、ES512、PS256、PS384)
 *          载荷也叫【消息体】(payload)是一个JSON对象，一般使用Base64URL编码，存放主要的有用信息，如：登陆的用户名，登录时间，登录的过期时间，还可以放一些自定义信息【作用:指定JWT的请求数据】
 *              这些有效信息包含三个部分：标准中注册的声明、公共的声明和私有的声明
 *              一、标准中注册的声明：
 *                  ① iss(issuer): jwt签发者
 *                  ② sub(subject): jwt所面向的用户，放登录的用户名等
 *                  ③ aud(audience): jwt接收者
 *                  ④ exp(expiration time): jwt的过期时间，这个过期时间必须要大于签发时间
 *                  ⑤ nbf(Not Before): 生效时间，定义在什么时间之前
 *                  ⑥ iat(issuedAt): jwt的签发时间
 *                  ⑦ jti(JWT ID): jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
 *              二、公共的声明：公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息，但不建议添加敏感信息，因为该部分在客户端可解密
 *              三、私有的声明：私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息
 *      签证(signature)存放有效信息的地方，这些有效信息包含三个部分，三部分之间用"."号做分隔，对header和payload使用密钥进行签名，防止数据篡改
 *      签证信息的算法：base64UrlEncode(header) + "." + base64UrlEncode(payload) + your-256-bit-secret
 * @reference-doc
 *  [0] { @link https://gitee.com/love-connect-team/love-connect-im-service/blob/dev/im-service-common/src/main/java/cn/loveconnect/common/utils/TokenUtils.java }
 *  [1] Java中Json web token (JWT)的使用 - CSDN - https://blog.csdn.net/AdminGuan/article/details/100147488
 *  [2] signing key‘s algorithm ‘AES‘ does not equal a valid HmacSHA - CSDN - https://blog.csdn.net/qq_31424825/article/details/117352277
 */
public class JwtUtils {
        private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

        // jti：jwt 的唯一身份标识
        //public static final String JWT_ID = UUID.randomUUID().toString();

        /**
         * 过期时间默认值
         */
        public final static int DEFAULT_EXPIRE_TIME = 60 * 60 * 1000;

        /**
         * 加密算法默认值
         */
        public final static String DEFAULT_ALGORITHM = "HmacSHA512";

        /**
         * 签名算法默认值
         */
        public final static SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

        /**
         * 存放在通信双方本地的、加密的私钥 （由通信双方共同约定）
         * @description
         *  为避免报此错误，需满足长度 >= 256
         *      .WeakKeyException: The signing key's size is 128 bits which is not secure enough for the HS256 algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HS256 MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).  Consider using the io.jsonwebtoken.security.Keys class's 'secretKeyFor(SignatureAlgorithm.HS256)' method to create a key guaranteed to be secure enough for HS256.
         */
        public String jwtSecret;

        /**
         * 过期时间
         * @description 单位: 毫秒
         * @sample
         *  60 * 60 * 1000 => 1 hour
         *  7 * 24 * 3600 * 1000 => 7 days     */
        private Integer expireTime = DEFAULT_EXPIRE_TIME;

        /**
         * 加密算法
         * @description
         *  eg: "AES"[X] "HS256"[X] "HmacSHA256"[√] "HmacSHA512"[√] ...
         */
        private String algorithm = DEFAULT_ALGORITHM;

        /**
         * 签名算法
         */
        private SignatureAlgorithm signatureAlgorithm = DEFAULT_SIGNATURE_ALGORITHM;

        /**
         * 构造器
         * @param jwtSecret
         *     eg: "jiamimiwen4643thfihebfiube35398thfh934yty98gh9hew9gweg43g3g3g43g34gi834g7gf83gfbuibeiubfisbifg8e4"
         */
        public JwtUtils(String jwtSecret) {
            this.jwtSecret = jwtSecret;
        }

        /**
         * 构造器
         * @param jwtSecret 对称加密通信的密钥
         * @param algorithm 加密算法
         * @param signatureAlgorithm 签名算法
         */
        public JwtUtils(String jwtSecret, String algorithm, SignatureAlgorithm signatureAlgorithm) {
            this.jwtSecret = jwtSecret;
            this.algorithm = algorithm;
            this.signatureAlgorithm = signatureAlgorithm;
        }

        /**
         * 由字符串生成加密 key
         * @param jwtSecret 本地密钥
         * @param algorithm
         * @return
         */
        public static SecretKey generateKey(String jwtSecret,String algorithm) {
            // 本地的密钥(jwtSecret)解码
            byte[] encodedKey = Base64Utils.encode(jwtSecret.getBytes());
            // 根据给定的字节数组使用指定加密算法构造一个密钥
            SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, algorithm);
            return key;
        }

        /**
         * 创建 JWT (Json web token)
         * @description
         *  [1] String jwt = request.getHeader("Authorization");
         *  [2] request.header
         *      authorization: Basic YWRtaW46cWluZ2xpYW5fMjAyMw==
         *      => authorization: Basic admin:qinglian_2023
         *          atob(admin: qinglian_2023)
         * @sample JWT
         *  [1] authorization : Basic YWRtaW46cWluZ2xpYW5fMjAyMw==
         *  [2] eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjAxMDIwMyIsInVzZXJuYW1lIjoiYWRtaW4iLCJqdGkiOiI5MTA3YjZlOS02MTUyLTQyMTMtYmFkNy1hNGE0MTgzYjAzNzAiLCJpYXQiOjE2ODI4MzUzNzEsImlzcyI6IkphY2siLCJzdWIiOiJ7XCJpZFwiOjEwLFwicGFzc3dvcmRcIjpcIjEyMzEyM1wiLFwidXNlcm5hbWVcIjpcIuW8oOS4iVwifSIsImV4cCI6MTY4MjgzODk3MSwiYXVkIjoi5p2O5ZubIn0.NKr8EkKFVCGuwVq7QH0HfnPMopKUiXQrwrJr-tN9BsE
         *      JWT长度：352
         *      jwt三个组成部分中间payload部分的解密：
         *      jti用户id：9107b6e9-6152-4213-bad7-a4a4183b0370
         *      iat登录时间：2023-04-30 14:16:11
         *      iss签发者：Jack
         *      sub用户信息列表：{"id":10,"password":"123123","username":"张三"}
         *      exp过期时间：2023-04-30 15:16:11
         *      aud接收者：李四
         *      登录的用户名：admin
         *      登录的用户名：admin
         *      登录的密码：010203
         * @param issuer 签发者 (可理解为: 签名+发送者)
         * @param audience 接收者
         * @param subject 通信时需发送的明文内容中的正文内容
         * @param jwtId JTI用户ID (对应 payload 的 jti, )
         *   例如: 20da39f8-b74e-4a9b-9a0f-a39f1f73fe64
         * @return
         * @throws Exception
         */
        public String createJWT(String issuer, String audience, String subject, String jwtId) throws Exception {
            // step1 设置头部信息(设置 request.headers 的 type、加密算法 )
            //方式1
//		Map<String, Object> header = new HashMap<String, Object>();
//		header.put("typ", "JWT");
//		header.put("alg", "HS256");
            // 方式2 指定 header 那部分签名的时候使用的签名算法， jjwt 已将这部分内容封装好了，只有{"alg":"HS256"}
            SignatureAlgorithm signatureAlgorithm = getSignatureAlgorithm();

            // 创建 payload 的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和 jwt 的接收方提前沟通好验证的方式）
            Map<String, Object> claims = new HashMap<>();
            //claims.put("username", "admin");
            //claims.put("password", "010203");
            //claims.put("token", "xxxx");

            // 生成JWT的时间
            long nowTime = System.currentTimeMillis();
            Date issuedAt = new Date(nowTime);
            // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
            SecretKey key = generateKey(this.jwtSecret, this.algorithm);
            // 为 payload 添加各种标准声明和私有声明
            // sample-payload : '{"password":"010203","username":"admin","jti":"9107b6e9-6152-4213-bad7-a4a4183b0370","iat":1682835371,"iss":"Jack","sub":"{\\"id\\":10,\\"password\\":\\"123123\\",\\"username\\":\\"å¼ ä¸\x89\\"}","exp":1682838971,"aud":"æ\x9D\x8Eå\x9B\x9B"}'
            JwtBuilder builder = Jwts.builder() // 表示new一个JwtBuilder，设置jwt的body
//				.setHeader(header) // 设置头部信息
                    .setClaims(claims) // 如果有私有声明，一定要先设置自己创建的这个私有声明，这是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明
                    // 对应 request.headers.authorization[payload].jti | jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                    .setId(jwtId)
                    // 对应 request.headers.authorization[payload].iat | iat(issuedAt)：jwt的签发时间
                    .setIssuedAt(issuedAt)
                    // 对应 request.headers.authorization[payload].iss | iss(issuer)：jwt签发者
                    .setIssuer(issuer)
                    // 对应 request.headers.authorization[payload].sub | sub(subject)：
                    .setSubject(subject)
                    .signWith(signatureAlgorithm, key); // 设置签名，使用的是签名算法和签名使用的秘钥
            // 设置过期时间
            long expTime = getExpireTime();
            if (expTime >= 0) {
                long exp = nowTime + expTime;
                // 对应 request.headers.authorization[payload].exp
                builder.setExpiration(new Date(exp));
            } else {
                throw new RuntimeException("Fail to create JWT, because that the `expireTime` is negative number value!");
            }
            // 设置 jwt 接收者
            if (audience == null || "".equals(audience)) {
                // 对应 request.headers.authorization[payload].aud
                builder.setAudience("Tom");
            } else {
                builder.setAudience(audience);
            }
            return builder.compact();
        }

        /**
         * 解密 JWT
         * @param jwt
         * @return
         * @throws Exception
         */
        public Claims parseJWT(String jwt) throws Exception {
            SecretKey key = generateKey(this.jwtSecret, this.algorithm); // 签名秘钥，和生成的签名的秘钥一模一样
            Claims claims = Jwts.parser() // 得到 DefaultJwtParser
                    .setSigningKey(key) // 设置签名的秘钥
                    .parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
            return claims;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public Integer getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Integer expireTime) {
            this.expireTime = expireTime;
        }

        public SignatureAlgorithm getSignatureAlgorithm() {
            return signatureAlgorithm;
        }

        public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
        }
    }